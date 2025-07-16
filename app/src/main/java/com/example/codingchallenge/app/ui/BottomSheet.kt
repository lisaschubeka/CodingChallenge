package com.example.codingchallenge.app.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BottomSheet(
    name: String, note: String?, rangeBar: @Composable () -> Unit
) {

    Column(
        Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {

        Text(name, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardColors(
                containerColor = Color(0xFFE3E2E2),
                contentColor = Color.Black,
                disabledContentColor = Color.White,
                disabledContainerColor = Color.White
            ),
        ) {

            Column(Modifier.padding(16.dp)) {
                rangeBar()

            }

        }
        Spacer(Modifier.height(16.dp))

        if (note != null) {
            Row(Modifier.align(alignment = Alignment.Start)) {
                Icon(
                    imageVector = Icons.Default.MailOutline,
                    contentDescription = "Warning Icon",
                    tint = Color(0xFF2683CD)
                )
                Spacer(Modifier.padding(2.dp))
                Text("Notiz", fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(2.dp))
            Text(
                note,
                fontSize = 16.sp,
                color = Color.DarkGray,
            )
            Spacer(Modifier.height(20.dp))
        }


        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = CardColors(
                containerColor = Color(0xFFE3E2E2),
                contentColor = Color.Black,
                disabledContentColor = Color.White,
                disabledContainerColor = Color.White
            )
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(20.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ExitToApp,
                    contentDescription = "Informationen",
                    tint = Color(0xFF2196F3),
                )
                Spacer(Modifier.width(20.dp))
                Text("Weitere Informationen", color = Color(0xFF2196F3))
            }
        }
    }


}