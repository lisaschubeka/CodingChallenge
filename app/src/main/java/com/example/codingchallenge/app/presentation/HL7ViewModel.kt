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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
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

    val uiState: StateFlow<HL7UiState> = flow {
        emit(HL7UiState(isLoading = true))

        // TODO database saving and retrieving
        val hl7parsed = processHL7DataUseCase.parseToHL7Data()
        val retrievedData: HL7Data? = if (hl7parsed != null) {
            database.withTransaction {

                processHL7DataUseCase.save(hl7parsed)
                processHL7DataUseCase.retrieve()
            }
        } else {
            null
        }

        if (retrievedData != null) {
            obxReadStatusUseCase.addObxIdsAsUnread(retrievedData.obxSegmentList.map {
                it.setId
            })
        }

        val currentUser = if (retrievedData != null) {
            processHL7DataUseCase.mapToUser(retrievedData.pid, retrievedData.msh)
        } else {
            User("", "", "")
        }

        val currentTestResults = retrievedData?.let {
            processHL7DataUseCase.mapToTestResult(it.obxSegmentList, it.nteMap)
        } ?: emptyList()

        emit(
            HL7UiState(
                isLoading = false,
                user = currentUser,
                testResults = currentTestResults
            )
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = HL7UiState()
    )

    val unreadObxCount: Flow<Int> = obxReadStatusUseCase.getAmountObxNotRead()

    fun loadFromFileAndSaveToDatabase() {
        viewModelScope.launch {
            uiState.collect()
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