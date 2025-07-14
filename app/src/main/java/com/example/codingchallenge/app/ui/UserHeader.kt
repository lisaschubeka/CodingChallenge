package com.example.codingchallenge.app.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.codingchallenge.app.presentation.HL7ViewModel
import com.example.codingchallenge.domain.model.User


@Composable
fun UserHeader(user: User?, viewModel: HL7ViewModel) {

    user?.let {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF3E70F7))
                .padding(16.dp)
        ) {
            Spacer(Modifier.height(20.dp))

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(modifier = Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                Column {
                    Text(
                        text = it.name,
                        fontSize = 24.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFFFC107))
                        )
                        Text(
                            text = "  Test Subject",
                            color = Color(0xFFFFC107),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Button(
                    onClick = { viewModel.loadFromFileAndSaveToDatabase() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    ),
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text("Load Data")
                }
            }



            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "GEBURTSDATUM", fontSize = 12.sp, color = Color.LightGray
                    )
                    Text(
                        text = viewModel.formatBirthday(it.birthday),
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
                Column {
                    Text(
                        text = "TAGEBUCHNUMMER", fontSize = 12.sp, color = Color.LightGray
                    )
                    Text(
                        text = it.diaryNumber, color = Color.White, fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
