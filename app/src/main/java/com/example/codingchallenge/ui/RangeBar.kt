package com.example.codingchallenge.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RangeBar(
    value: Float, range: String?, normalMin: Float?, normalMax: Float?, isLessThanOnly: Boolean
) {

    val lowColor = Color(0xFF4CAF50)
    val normalColor = Color(0xFFFFC107)
    val highColor = Color(0xFFF44336)

    val indicatorPosition = remember(value, normalMin, normalMax) {
        when {
            isLessThanOnly -> if (value < normalMax!!) 0.25f else 0.75f
            normalMin != null && normalMax != null -> {
                val rangeSpan = normalMax - normalMin
                if (rangeSpan > 0) {
                    ((value - normalMin) / rangeSpan).coerceIn(0f, 1f)
                } else 0.5f
            }

            else -> 0.5f
        }
    }

    BoxWithConstraints(Modifier.fillMaxWidth()) {
        val totalWidth = maxWidth
        val indicatorOffset = (indicatorPosition * totalWidth.value).dp

        // Value label above
        Box(
            Modifier
                .offset(x = indicatorOffset - 20.dp) // Center alignment adjustment
                .padding(bottom = 4.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    value.toString(),
                    color = Color.White,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .background(
                            Color(0xFFEF5350), shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                )
                Box(
                    Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFEF5350))
                )
            }
        }

        // Range bar
        Row(
            Modifier
                .fillMaxWidth()
                .height(8.dp)
                .align(Alignment.BottomCenter)
        ) {
            if (isLessThanOnly) {
                Box(
                    Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(lowColor)
                )
                Box(
                    Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(highColor)
                )
            } else {
                Box(
                    Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(lowColor)
                )
                Box(
                    Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(normalColor)
                )
                Box(
                    Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(highColor)
                )
            }
        }
    }

    // Range labels below bar
    Spacer(Modifier.height(4.dp))
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        if (!isLessThanOnly) {
            Text("$normalMin", fontSize = 12.sp)
            Text("$normalMax", fontSize = 12.sp)
        } else {
            Text("0", fontSize = 12.sp)
            Text("< $normalMax", fontSize = 12.sp)
        }
    }
}

