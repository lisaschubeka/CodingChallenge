package com.example.codingchallenge.app.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.codingchallenge.app.presentation.HL7ViewModel

@Composable
fun HL7Screen(viewModel: HL7ViewModel) {

    val user by viewModel.user.collectAsState()
    val testResults by viewModel.testResults.collectAsState()
    val notReadResults by viewModel.notReadResults.collectAsState()

    Column {
        UserHeader(user, viewModel)

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
