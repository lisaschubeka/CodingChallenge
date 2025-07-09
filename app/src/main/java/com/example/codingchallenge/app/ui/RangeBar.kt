package com.example.codingchallenge.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex

@Composable
fun RangeBar(
    value: Float, normalMin: Float?, normalMax: Float?
) {

    val green = Color(0xFF4CAF50)
    val yellow = Color(0xFFFFC107)
    val red = Color(0xFFF44336)
    var indicatorColor = Color(0xFF8F8F8F)

    BoxWithConstraints(Modifier.fillMaxWidth()) {
        val totalWidth = maxWidth
        val indicatorOffset: Float = when {
            normalMin != null && normalMax != null -> {
                if (value < normalMax && value > normalMin) {
                    val scale = normalMax - normalMin
                    val diff = value - normalMin
                    val offsetInGreenRange = diff / scale
                    indicatorColor = green
                    offsetInGreenRange / 3f + 0.3f
                } else {
                    // TODO fill out other fields
                    // indicator in lower red bar
                    if (value < normalMin - (normalMax - normalMin) / 2) {
                        0.5f

                        // indicator in lower yellow bar
                    } else if (value < normalMin) {
                        0.5f

                        // indicator is in the upper red bar
                    } else if (value > normalMax + (normalMax - normalMin) / 2) {
                        0.5f

                        // indicator is in the upper yellow bar
                    } else if (value > normalMax) {
                        val diff = value - normalMax
                        val scale = normalMax + (normalMax - normalMin) / 2 - normalMax
                        indicatorColor = yellow

                        diff / scale + 2 / 3f
                    } else 0.5f
                }
            }

            normalMax != null -> {
                if (value < normalMax) {
                    indicatorColor = green
                    value / (normalMax * 2)

                }
                // upper red bar
                else if (value > normalMax + (normalMax) / 2) {
                    // TODO implement logic for red
                    // TODO this only handles extreme values that do not fit on scale
                    indicatorColor = red
                    1f
                }
                // upper yellow bar
                else {
                    val scale = normalMax / 2
                    val diff = (value - normalMax)
                    val offsetInYellowRange = diff / scale
                    indicatorColor = yellow
                    0.5f + offsetInYellowRange / 4
                }
            }

            else -> {
                0.5f
            }
        }

        Box(
            Modifier
                .offset(x = (indicatorOffset * totalWidth.value).dp - 20.dp, y = 4.5.dp)
                .zIndex(1f)

        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    value.toString(),
                    color = Color.White,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .background(
                            indicatorColor, shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                )
                Box(
                    Modifier
                        .size(18.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .border(width = 3.dp, color = indicatorColor, shape = CircleShape)
                )
            }
        }

        Row(
            Modifier
                .fillMaxWidth()
                .height(8.dp)
                .align(Alignment.BottomCenter)
        ) {
            if (normalMin == null) {
                Box(
                    Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(green)
                )
                Box(
                    Modifier
                        .weight(0.5f)
                        .fillMaxHeight()
                        .background(yellow)
                )
                Box(
                    Modifier
                        .weight(0.5f)
                        .fillMaxHeight()
                        .background(red)
                )
            } else {
                Box(
                    Modifier
                        .weight(0.5f)
                        .fillMaxHeight()
                        .background(red)
                )
                Box(
                    Modifier
                        .weight(0.5f)
                        .fillMaxHeight()
                        .background(yellow)
                )
                Box(
                    Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(green)
                )

                Box(
                    Modifier
                        .weight(0.5f)
                        .fillMaxHeight()
                        .background(yellow)
                )
                Box(
                    Modifier
                        .weight(0.5f)
                        .fillMaxHeight()
                        .background(red)
                )


            }
        }
    }

    BoxWithConstraints(Modifier.fillMaxWidth()) {
        val oneThird = (maxWidth * 1f / 3f).value.dp
        val twoThirds = (maxWidth * 2f / 3f).value.dp
        val oneHalf = (maxWidth * 1f / 2f).value.dp
        if (normalMin != null) {
            Box(Modifier.offset(x = oneThird - 12.dp, y = 5.dp)) {
                Text("$normalMin", fontSize = 12.sp)
            }
            Box(Modifier.offset(x = twoThirds - 12.dp, y = 5.dp)) {
                Text("$normalMax", fontSize = 12.sp)
            }
        } else {
            Text("0", fontSize = 12.sp)
            Box(Modifier.offset(x = oneHalf - 12.dp, y = 5.dp)) {
                Text("$normalMax", fontSize = 12.sp)
            }
        }

    }

}

