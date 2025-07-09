package com.example.codingchallenge.app.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codingchallenge.domain.model.TestResult
import com.example.codingchallenge.domain.model.User
import com.example.codingchallenge.domain.usecase.ParseToHL7MsgUseCase
import com.example.codingchallenge.domain.usecase.ParseToTestResultsUseCase
import com.example.codingchallenge.domain.usecase.ParseToUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HL7ViewModel @Inject constructor(
    private val parseToHL7MsgUseCase: ParseToHL7MsgUseCase,
    private val parseToUserUseCase: ParseToUserUseCase,
    private val parseToTestResultsUseCase: ParseToTestResultsUseCase
) : ViewModel() {

    private val _user = MutableStateFlow(User("", "", ""))
    val user: StateFlow<User> = _user.asStateFlow()

    private val _testResults = MutableStateFlow<List<TestResult>>(emptyList())
    val testResults: StateFlow<List<TestResult>> = _testResults

    private val _readTestResults = MutableStateFlow<List<TestResult>>(emptyList())
    val notReadResults: StateFlow<List<TestResult>> = _readTestResults

    init {
        parseHL7Message()
    }

    private fun parseHL7Message() {
        viewModelScope.launch {
            val hl7parsed = parseToHL7MsgUseCase.parseToHL7Msg()

            if (hl7parsed != null) {
                _user.value = parseToUserUseCase.parseToUser(hl7parsed.pid, hl7parsed.msh)

            }

            val obrResults = hl7parsed?.let {
                parseToTestResultsUseCase.parseToTestResults(it.obxSegmentList, it.nteMap)
            } ?: emptyList()

            _testResults.value = obrResults
            _readTestResults.value = obrResults
        }
    }

    fun removeTestResult(id: String) {
        _readTestResults.update { currentList ->
            currentList.filter { it.id != id }
        }
    }

    // TODO should the following two functions be here? do UI formatting stuff
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