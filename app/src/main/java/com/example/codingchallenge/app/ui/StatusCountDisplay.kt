package com.example.codingchallenge.app.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.codingchallenge.domain.model.TestResult

@Composable
fun StatusCountDisplay(
    testResults: List<TestResult>,
    label: String,
    subLabel: String? = null,
) {

    Row(
        modifier = Modifier.padding(horizontal = 36.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = null,
            modifier = Modifier.size(36.dp),
            tint = Color.Red
        )


        Spacer(modifier = Modifier.width(16.dp))

        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Row(verticalAlignment = Alignment.Bottom) {
                // TODO should be ${testResults.size}, there is a bug here yet to be fixed.
                // TODO The bug is that there are (actual testResults + 1) in viewModel.notReadResults
                Text(
                    text = "${testResults.filter { testResult -> !testResult.isRead }.size - 1}",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = label,
                    color = Color.DarkGray,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
            if (subLabel != null) {
                Text(
                    text = subLabel, fontWeight = FontWeight.Medium
                )
            }
        }

    }
}
