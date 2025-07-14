package com.example.codingchallenge.app.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.withTransaction
import com.example.codingchallenge.app.AppDatabase
import com.example.codingchallenge.domain.model.HL7Data
import com.example.codingchallenge.domain.model.TestResult
import com.example.codingchallenge.domain.model.User
import com.example.codingchallenge.domain.usecase.OBXReadStatusUseCase
import com.example.codingchallenge.domain.usecase.ProcessHL7DataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HL7ViewModel @Inject constructor(
    private val processHL7DataUseCase: ProcessHL7DataUseCase,
    private val obxReadStatusUseCase: OBXReadStatusUseCase,
    private val database: AppDatabase
) : ViewModel() {

    data class HL7UiState(
        val isLoading: Boolean = true,
        val user: User = User("", "", ""),
        val testResults: List<TestResult> = emptyList()
    )

    private val _retrievedHL7Data = MutableStateFlow<HL7Data?>(null)
    val retrievedHL7Data: StateFlow<HL7Data?> = _retrievedHL7Data.asStateFlow()

    val uiState: StateFlow<HL7UiState> = retrievedHL7Data
        .map { data ->
            if (data == null) {
                HL7UiState(isLoading = true)
            } else {
                val currentUser = processHL7DataUseCase.mapToUser(data.pid, data.msh)
                val currentTestResults =
                    processHL7DataUseCase.mapToTestResult(data.obxSegmentList, data.nteMap)
                HL7UiState(
                    isLoading = false,
                    user = currentUser,
                    testResults = currentTestResults
                )
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HL7UiState(isLoading = true)
        )

    val unreadObxCount: Flow<Int> = obxReadStatusUseCase.getAmountObxNotRead()

    init {
        loadFromDatabase()
    }

    fun loadFromFileAndSaveToDatabase() {
        viewModelScope.launch {
            val hl7parsed = processHL7DataUseCase.parseToHL7Data()
            processHL7DataUseCase.clearDatabaseData()
            if (hl7parsed != null) {
                val dataFromDb = database.withTransaction {
                    processHL7DataUseCase.save(hl7parsed)
                    processHL7DataUseCase.retrieve()
                }

                _retrievedHL7Data.value = dataFromDb

                obxReadStatusUseCase.addObxIdsAsUnread(dataFromDb.obxSegmentList.map { it.setId })

            }
        }
    }

    private fun loadFromDatabase() {
        viewModelScope.launch {
            try {
                _retrievedHL7Data.value = processHL7DataUseCase.retrieve()
            } catch (e: Exception) {
                _retrievedHL7Data.value = null
            }

        }
    }

    fun markTestResultAsRead(id: Long) {
        viewModelScope.launch {
            obxReadStatusUseCase.markObxAsRead(id, true)
        }
    }

    fun isTestResultRead(id: Long): Flow<Boolean> {
        return obxReadStatusUseCase.observeObxReadStatus(id)
    }

    fun parseRange(range: String?): Pair<Float?, Float?> {
        val trimmed = range?.trim() ?: ""

        return when {
            trimmed.startsWith("<") -> {
                val upper = trimmed.removePrefix("<").trim().toFloatOrNull()
                Pair(null, upper)
            }

            trimmed.contains("-") -> {
                val parts = trimmed.split("-").map { it.trim() }
                val low = parts.getOrNull(0)?.toFloatOrNull()
                val high = parts.getOrNull(1)?.toFloatOrNull()
                Pair(low, high)
            }

            else -> Pair(null, null)
        }
    }

    fun formatBirthday(dateString: String): String {
        val inputFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("d.M.yyyy", Locale.GERMAN)

        try {
            val date = inputFormat.parse(dateString)
            return date?.let { outputFormat.format(it) } ?: ""
        } catch (e: Exception) {
            println("Error formatting birthday: ${e.message}")
            return dateString
        }
    }
}