package com.example.codingchallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.codingchallenge.app.ui.NavigationStack
import com.example.codingchallenge.app.ui.theme.CodingChallengeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object Overview

    @Serializable
    data class Detail(val mshId: Long)
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            CodingChallengeTheme {
                NavigationStack()
            }
        }
    }
}