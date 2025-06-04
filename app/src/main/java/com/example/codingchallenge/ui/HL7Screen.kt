package com.example.codingchallenge.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.codingchallenge.HL7ViewModel
import com.example.codingchallenge.HL7_FILE

@Composable
fun HL7Screen(viewModel: HL7ViewModel) {

    val user by viewModel.user.collectAsState()
    val testResults by viewModel.testResults.collectAsState()
    val notReadResults by viewModel.notReadResults.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.parseHL7Message(HL7_FILE)
    }
    Column {
        UserHeader(user)
        // TODO should be > 0, there is a bug here yet to be fixed.
        // TODO The bug is that there are (actual testResults + 1) in viewModel.notReadResults
        if (notReadResults.size > 1) {
            StatusCountDisplay(viewModel, "auffällige Befunde", "Überprüfung notwendig")
        }
        Column(Modifier.padding(16.dp)) {

            if (testResults.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    items(testResults) { result ->

                        result.value.toFloatOrNull()?.let { numericValue ->

                            TestResultCard(
                                viewModel = viewModel, testResult = result
                            )
                        }
                    }
                }
            }

        }
    }
}
