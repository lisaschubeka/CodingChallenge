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

    val uiState by viewModel.uiState.collectAsState()

    Column {
        UserHeader(
            uiState.user,
            viewModel::loadFromFileAndSaveAndLoadFromDatabase,
            viewModel::formatBirthday
        )

        if (uiState.testResults.isNotEmpty()) {
            StatusCountDisplay(uiState.testResults, "auffällige Befunde", "Überprüfung notwendig")
        }
        Column(Modifier.padding(16.dp)) {

            if (uiState.testResults.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    items(uiState.testResults) { result ->
                        result.value.toFloatOrNull()?.let {
                            TestResultCard(
                                viewModel::parseRange,
                                viewModel::markTestResultAsRead,
                                testResult = result
                            )
                        }
                    }
                }
            }

        }
    }
}
