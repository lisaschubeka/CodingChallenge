package com.example.codingchallenge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codingchallenge.model.TestResult
import com.example.codingchallenge.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HL7ViewModel @Inject constructor() : ViewModel() {

    private val _user = MutableStateFlow(User("", "", ""))
    val user: StateFlow<User> = _user.asStateFlow()

    private val _testResults = MutableStateFlow<List<TestResult>>(emptyList())
    val testResults: StateFlow<List<TestResult>> = _testResults

    private val _readTestResults = MutableStateFlow<List<TestResult>>(emptyList())
    val notReadResults: StateFlow<List<TestResult>> = _readTestResults

    private val parser = HL7Parser()

    fun parseHL7Message(message: String) {
        viewModelScope.launch {
            val parsed = parser.parseHL7Data(message)

            val pid = parsed["PID"]?.firstOrNull()
            pid?.let {
                val name = it.getOrNull(5)?.joinToString(" ") ?: "Unknown"
                val dob = it.getOrNull(7)?.firstOrNull() ?: "Unknown"

                val msh = parsed["MSH"]?.firstOrNull()
                msh?.let {
                    val diaryNumber = it.getOrNull(6)?.firstOrNull() ?: "Unknown"
                    _user.value = User(name, diaryNumber, dob)
                }
            }

            val obrResults = parsed["OBX"]?.mapNotNull { obx ->
                val id = obx.getOrNull(1)?.firstOrNull() ?: ""
                val testName = obx.getOrNull(3)?.getOrNull(1) ?: return@mapNotNull null
                val value = obx.getOrNull(5)?.firstOrNull() ?: return@mapNotNull null
                val unit = obx.getOrNull(6)?.firstOrNull() ?: ""
                val range = obx.getOrNull(7)?.firstOrNull() ?: ""
                val note = parsed["NTE"]?.filter { note -> note[0][0] == obx[1][0] }
                    ?.takeIf { it.isNotEmpty() }?.joinToString("\n") { a -> a[4][0].trim() }
                TestResult(id, testName, value, unit, range, note)
            } ?: emptyList()

            val resultsWithValidRange = obrResults.filter { t -> t.range != "" }.onEach { t ->
                t.testName = t.testName.replace(Regex("\\s+"), " ").trim()
            }
            _testResults.value = resultsWithValidRange
            _readTestResults.value = resultsWithValidRange
        }
    }

    fun removeTestResult(id: String) {
        _readTestResults.update { currentList ->
            currentList.filter { it.id != id }
        }
    }
}