package com.example.codingchallenge.app.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.codingchallenge.domain.model.TestResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestResultCard(
    parseRange: (String?) -> Pair<Float?, Float?>,
    markTestResultAsRead: (Long) -> Unit,
    testResult: TestResult,
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
    )

    val (normalMin, normalMax) = parseRange(testResult.range)

    val isTooLow = normalMin != null && (((testResult.value.toFloatOrNull()) ?: 0f) < normalMin)

    val isTooHigh = (normalMin == null && ((testResult.value.toFloatOrNull())
        ?: 0f) >= normalMax!!) || (normalMax != null && ((testResult.value.toFloatOrNull())
        ?: 0f) > normalMax)


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardColors(Color.White, Color.Black, Color.LightGray, Color.DarkGray),
        onClick = {
            showBottomSheet = true
            markTestResultAsRead(testResult.id)
        }) {
        Column(
            Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    run {
                        val regex = "\\s{3,}".toRegex()
                        testResult.testName.replace(regex, " ")
                    }, fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Icon(
                    imageVector = if (testResult.isRead) Icons.Outlined.CheckCircle else Icons.Filled.Info,
                    contentDescription = if (testResult.isRead) "Gelesen" else "Nicht gelesen",
                    tint = if (testResult.isRead) Color.Gray else Color.Red,
                )
            }
            Text("in ${testResult.unit}", style = MaterialTheme.typography.labelSmall)

            Spacer(Modifier.height(1.dp))

            RangeBar(
                ((testResult.value.toFloatOrNull()) ?: 0f), normalMin, normalMax
            )

            Spacer(Modifier.height(3.dp))

            BottomChips(testResult.note, isTooHigh, isTooLow)

            if (showBottomSheet) {
                ModalBottomSheet(
                    modifier = Modifier.fillMaxHeight(),
                    sheetState = sheetState,
                    onDismissRequest = { showBottomSheet = false },
                    containerColor = Color.White
                ) {
                    BottomSheet(
                        testResult.testName, testResult.note, {
                            RangeBar(
                                ((testResult.value.toFloatOrNull()) ?: 0f),
                                normalMin,
                                normalMax
                            )
                        })
                }
            }
        }
    }
}