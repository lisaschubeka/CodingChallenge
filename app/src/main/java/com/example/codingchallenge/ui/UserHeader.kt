package com.example.codingchallenge.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.codingchallenge.model.User


@Composable
fun UserHeader(user: User?) {
    user?.let {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF3E70F7))
                .padding(16.dp)
        ) {
            Spacer(Modifier.height(20.dp))

            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = it.name,
                fontSize = 24.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 4.dp)) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFFFC107)) // Yellow dot
                )
                Text(
                    text = "  Test Subject", // extra space for spacing after the dot
                    color = Color(0xFFFFC107),
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "GEBURTSDATUM",
                        fontSize = 12.sp,
                        color = Color.LightGray
                    )
                    Text(
                        text = it.birthday,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
                Column {
                    Text(
                        text = "TAGEBUCHNUMMER",
                        fontSize = 12.sp,
                        color = Color.LightGray
                    )
                    Text(
                        text = it.diaryNumber,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
