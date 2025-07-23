package com.example.codingchallenge.app.ui

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.codingchallenge.domain.model.User


@Composable
fun UserHeader(
    user: User?,
    loadFromFileAndSaveToDatabase: (Context, Uri) -> Unit,
    formatBirthday: (dateString: String) -> String,
) {
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
                PickDocumentButton(loadFromFileAndSaveToDatabase)
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
                        text = formatBirthday(it.birthday),
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

@Composable
fun PickDocumentButton(loadFromFileAndSaveToDatabase: (Context, Uri) -> Unit) {
    val context = LocalContext.current
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
            // if files are accessed through recents, the uri gets replaced to something cached (not the original file path)
            // only works if you go to the device in the file system and then go to downloads,
            // then file path will remain the original one
            // TODO make sure it works for all (cached or not cached) file paths
            if (uri != null && uri.lastPathSegment?.endsWith(".hl7", ignoreCase = true) == true) {
                loadFromFileAndSaveToDatabase(context, uri)
            } else {
                Log.d("PickDocumentButton", "Document selection cancelled or no document selected.")
            }
        }

    Column {
        Button(onClick = {
            launcher.launch(arrayOf("*/*"))
        }) {
            Text(text = "Select Document")
        }

    }
}

