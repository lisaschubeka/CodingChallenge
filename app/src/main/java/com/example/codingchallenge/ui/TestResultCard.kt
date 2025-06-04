package com.example.codingchallenge.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestResultCard(
    testName: String, value: Float, unit: String, range: String?, note: String?
) {

    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
    )

    val (normalMin, normalMax, isLessThanOnly) = parseRange(range)

    val isTooLow = normalMin != null && value < normalMin
    val isTooHigh =
        (normalMax != null && value > normalMax) || (isLessThanOnly && value >= normalMax!!)
    // TODO move some of this into the ViewModel, too much code


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardColors(Color.White, Color.Black, Color.LightGray, Color.DarkGray)
    ) {
        Column(
            Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                testName, fontSize = 18.sp, fontWeight = FontWeight.SemiBold
            )
            Text("in $unit", style = MaterialTheme.typography.labelSmall)

            Spacer(Modifier.height(1.dp))

            RangeBar(value, range, normalMin, normalMax, isLessThanOnly)

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                // TODO remove boarder dont have it white
                if (note != null) {
                    AssistChip(
                        onClick = { showBottomSheet = true },
                        label = {
                            Text(
                                "Notiz", color = Color(0xFF2683CD), fontSize = 13.sp
                            )
                        },
                        shape = RoundedCornerShape(8.dp),
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = Color(0xFFE2EEF7)
                        ),
                        border = BorderStroke(0.dp, Color.White),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.MailOutline,
                                contentDescription = "Warning Icon",
                                tint = Color(0xFF2683CD)
                            )
                        },
                    )
                    Spacer(Modifier.width(8.dp))

                }
                // TODO remove boarder dont have it white
                // Warning label if needed
                if (isTooHigh || isTooLow) {
                    AssistChip(
                        onClick = { },
                        label = {
                            Text(
                                if (isTooHigh) "Erhöhter Wert" else "Niedriger Wert",
                                color = Color(0xFFD32F2F),
                                fontSize = 13.sp
                            )
                        },
                        shape = RoundedCornerShape(8.dp),
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = Color(0xFFFFCDD2),
                        ),
                        border = BorderStroke(0.dp, Color.White),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Warning,
                                contentDescription = "Warning Icon",
                                tint = Color(0xFFD32F2F)
                            )
                        },
                    )
                }
            }
            if (showBottomSheet) {
                ModalBottomSheet(
                    modifier = Modifier.fillMaxHeight(),
                    sheetState = sheetState,
                    onDismissRequest = { showBottomSheet = false },
                    containerColor = Color.White
                ) {
                    BottomSheet(testName, note, {RangeBar(value, range, normalMin, normalMax, isLessThanOnly)})
                }
            }
        }
    }
}

private fun parseRange(range: String?): Triple<Float?, Float?, Boolean> {
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