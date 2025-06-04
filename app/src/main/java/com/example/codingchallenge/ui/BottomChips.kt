package com.example.codingchallenge.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BottomChips(note: String?, isTooHigh: Boolean, isTooLow: Boolean) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {

    if (note != null) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            color =Color(0xFFE2EEF7),
            border = BorderStroke(0.dp, Color.Transparent),
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.MailOutline,
                    contentDescription = "Note Icon",
                    tint = Color.Blue,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    "Notiz",
                    color = Color.Blue,
                    fontSize = 13.sp
                )
            }
        }
        Spacer(Modifier.width(8.dp))
    }

    if (isTooHigh || isTooLow) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = Color(0xFFFFCDD2),
            border = BorderStroke(0.dp, Color.Transparent),
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Warning,
                    contentDescription = "Warning Icon",
                    tint = Color.Red,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    if (isTooHigh) "Erhöhter Wert" else "Niedriger Wert",
                    color = Color.Red,
                    fontSize = 13.sp
                )
            }
        }
    } }
}