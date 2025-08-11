package com.example.codingchallenge.app.ui

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.codingchallenge.Screen
import com.example.codingchallenge.app.presentation.LoadHL7FileEvent
import com.example.codingchallenge.app.presentation.OverviewViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HL7FileOverview(navController: NavController, viewModel: OverviewViewModel) {

    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.events.collectLatest { event ->
            when (event) {
                is LoadHL7FileEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(event.message)
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }) { innerPadding ->
        Column(
            modifier = Modifier
                .background(Color(0xFF3E70F7))
                .padding(innerPadding)
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {

                PickDocumentButton(viewModel::loadFromFileAndSaveAndLoadFromDatabase)

            }
            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                if (uiState.isLoading) {
                    Text("Loading HL7 files...", modifier = Modifier.padding(16.dp))
                } else if (uiState.overviewFileDataList.isEmpty()) {
                    Text(
                        "No HL7 files loaded yet. Select a document.",
                        modifier = Modifier.padding(16.dp)
                    )
                } else {
                    LazyColumn {
                        items(uiState.overviewFileDataList) { overviewFileData ->
                            OverviewFileCard(
                                overviewFileData = overviewFileData,
                                onCardClick = {
                                    navController.navigate(Screen.Detail(mshId = overviewFileData.mshId))
                                }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
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
            if (uri != null && uri.lastPathSegment?.endsWith(".hl7", ignoreCase = true) == true) {
                loadFromFileAndSaveToDatabase(context, uri)
            } else {
                Log.d("PickDocumentButton", "Document selection cancelled or no document selected.")
            }
        }

    Column {
        Button(
            onClick = {
                launcher.launch(arrayOf("*/*"))
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color(0xFF3E70F7)
            )
        ) {
            Text(text = "Select Document")
        }
    }

}