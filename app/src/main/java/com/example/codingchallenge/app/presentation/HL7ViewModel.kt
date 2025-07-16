package com.example.codingchallenge.app.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codingchallenge.domain.model.HL7Data
import com.example.codingchallenge.domain.model.TestResult
import com.example.codingchallenge.domain.model.User
import com.example.codingchallenge.domain.usecase.OBXReadStatusUseCase
import com.example.codingchallenge.domain.usecase.ProcessHL7DataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class HL7ViewModel @Inject constructor(
    private val processHL7DataUseCase: ProcessHL7DataUseCase,
    private val obxReadStatusUseCase: OBXReadStatusUseCase
) : ViewModel() {

    data class HL7UiState(
        val isLoading: Boolean = true,
        val user: User = User("", "", ""),
        val testResults: List<TestResult> = emptyList(),
    )

    private val _uiState: MutableStateFlow<HL7UiState> = MutableStateFlow(HL7UiState())
    val uiState: StateFlow<HL7UiState> = _uiState.asStateFlow()

    init {
        loadFromDatabase()
    }

    fun loadFromDatabase() {
        viewModelScope.launch {
            try {
                val dataFromDb = processHL7DataUseCase.retrieveHL7DataFromDatabase()
                updateCurrentUserWithHL7data(dataFromDb)
                val flowTestResults = processHL7DataUseCase.observeTestResults()
                flowTestResults.collectLatest { listTestResults ->
                    updateTestResultsFromFlow(listTestResults)
                }
            } catch (e: Exception) {

            }

        }
    }

    fun loadFromFileAndSaveToDatabase() {
        viewModelScope.launch {
            processHL7DataUseCase.clearDatabaseData()
            val hl7parsed = processHL7DataUseCase.parseToHL7DataObject()
            if (hl7parsed != null) {
                processHL7DataUseCase.saveHL7DataToDatabase(hl7parsed)
                // This retrieval step is technically unnecessary, but it ensures we are only
                // displaying data that is actually in the database and not just from the parsed
                // results in variable hl7parsed
                val dataFromDb = processHL7DataUseCase.retrieveHL7DataFromDatabase()
                obxReadStatusUseCase.addObxIdsAsUnread(dataFromDb.obxSegmentList.map { it.setId })
            }
            loadFromDatabase()
        }
    }

    private fun updateCurrentUserWithHL7data(
        data: HL7Data
    ) {
        _uiState.update {
            run {
                val currentUser = processHL7DataUseCase.mapToUser(data.pid, data.msh)
                HL7UiState(
                    isLoading = false,
                    user = currentUser,
                    testResults = uiState.value.testResults
                )
            }
        }
    }

    private fun updateTestResultsFromFlow(listTestResult: List<TestResult>) {
        _uiState.update {
            run {
                HL7UiState(
                    isLoading = false,
                    testResults = listTestResult,
                    user = uiState.value.user
                )
            }
        }
    }

    fun markTestResultAsRead(id: Long) {
        viewModelScope.launch {
            obxReadStatusUseCase.markObxAsRead(id, true)
        }
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