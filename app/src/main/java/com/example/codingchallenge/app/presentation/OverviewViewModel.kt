package com.example.codingchallenge.app.presentation

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codingchallenge.domain.model.OverviewFileData
import com.example.codingchallenge.domain.usecase.ObserveFileOverviewListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class OverviewViewModel @Inject constructor(
    private val processHL7DataUseCase: ObserveFileOverviewListUseCase,
) : ViewModel() {

    data class HL7FileListUiState(
        val isLoading: Boolean = true,
        val overviewFileDataList: List<OverviewFileData> = emptyList()
    )

    private val _uiState: MutableStateFlow<HL7FileListUiState> =
        MutableStateFlow(HL7FileListUiState())
    val uiState: StateFlow<HL7FileListUiState> = _uiState.asStateFlow()

    private val _events = Channel<LoadHL7FileEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    init {
        loadOverviewFromDatabase()
    }

    private fun loadOverviewFromDatabase() {
        viewModelScope.launch {
            try {
                val flowHL7FileUpdates = processHL7DataUseCase.observeChangesForOverview()
                flowHL7FileUpdates.collectLatest { overviewUpdates ->
                    Log.d("UI UPDATE", "New overview data received: ${overviewUpdates.size}")
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            overviewFileDataList = overviewUpdates
                        )
                    }
                }
            } catch (e: Exception) {
                _events.send(LoadHL7FileEvent.ShowSnackbar("Failed to update with an exception."))
                Log.w("FILE READING: exception: ", e.message.toString())
            }
        }
    }

    private fun readFromHL7File(context: Context, uri: Uri): String {
        val contentResolver = context.contentResolver
        contentResolver.openInputStream(uri)?.use { inputStream ->
            val bytes = inputStream.readBytes()
            Log.w("FILE READING", "Resource file size: ${bytes.size} bytes")
            val file = File(context.filesDir, "tmp.hl7")
            FileOutputStream(file).use { outputStream ->
                outputStream.write(bytes)
            }
            return file.readText()
        }
        return ""
    }

    // In HL7ViewModel
    fun loadFromFileAndSaveAndLoadFromDatabase(context: Context, uri: Uri) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                val hl7Raw = readFromHL7File(
                    context, uri
                )
                processHL7DataUseCase.parseAndSaveHL7FileToDatabase(hl7Raw)

                _events.send(LoadHL7FileEvent.ShowSnackbar("HL7 file parsed and saved successfully!"))

            } catch (e: Exception) {
                Log.w("FILE READING", "EXCEPTION: ${e.message}")
                _events.send(LoadHL7FileEvent.ShowSnackbar("Failed to parse HL7 file."))
            }
            _uiState.update { it.copy(isLoading = false) }

        }
    }
}