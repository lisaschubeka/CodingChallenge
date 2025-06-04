package com.example.codingchallenge

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codingchallenge.model.TestResult
import com.example.codingchallenge.model.User
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class HL7ViewModel: ViewModel() {

    private val _user = MutableStateFlow(User("", "", ""))
    val user: StateFlow<User> = _user.asStateFlow()

    private val _testResults = MutableStateFlow<List<TestResult>>(emptyList())
    val testResults: StateFlow<List<TestResult>> = _testResults

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
                    val diaryNumber = it.getOrNull(6)?.firstOrNull()?: "Unknown"
                    _user.value = User( name, diaryNumber, dob)
                }
            }

            val obrResults = parsed["OBX"]?.mapNotNull { obx ->
                val testName = obx.getOrNull(3)?.getOrNull(1) ?: return@mapNotNull null
                val value = obx.getOrNull(5)?.firstOrNull() ?: return@mapNotNull null
                val unit = obx.getOrNull(6)?.firstOrNull() ?: ""
                val range = obx.getOrNull(7)?.firstOrNull()?: ""
                val i = obx[1][0]
                val note = parsed["NTE"]
                    ?.filter { note -> note[0][0] == obx[1][0] }
                    ?.takeIf { it.isNotEmpty() }
                    ?.joinToString("") { a -> a[4][0].trim() }
                TestResult(testName, value, unit, range, note)
            } ?: emptyList()
            for (r in obrResults) {
                Log.i("VIEWMODEL", r.testName + "" + r.value + "" + r.unit+ ""  + r.range)
            }
            _testResults.value = obrResults
        }
    }

     fun parseRange(range: String?): Triple<Float?, Float?, Boolean> {
        val trimmed = range?.trim() ?: ""

        // TODO handle > in the future
        return when {
            trimmed.startsWith("<") -> {
                val upper = trimmed.removePrefix("<").trim().toFloatOrNull()
                Triple(null, upper, true)
            }

            trimmed.contains("-") -> {
                val parts = trimmed.split("-").map { it.trim() }
                val low = parts.getOrNull(0)?.toFloatOrNull()
                val high = parts.getOrNull(1)?.toFloatOrNull()
                Triple(low, high, false)
            }

            else -> Triple(null, null, false)
        }
    }
}