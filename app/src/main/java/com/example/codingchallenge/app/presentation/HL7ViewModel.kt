package com.example.codingchallenge.app.presentation

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codingchallenge.domain.model.TestResult
import com.example.codingchallenge.domain.model.User
import com.example.codingchallenge.domain.usecase.OBXReadStatusUseCase
import com.example.codingchallenge.domain.usecase.ProcessHL7DataUseCase
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
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

sealed interface LoadHL7FileEvent {
    data class ShowSnackbar(val message: String) : LoadHL7FileEvent
}

@HiltViewModel
class HL7ViewModel @Inject constructor(
    private val processHL7DataUseCase: ProcessHL7DataUseCase,
    private val obxReadStatusUseCase: OBXReadStatusUseCase
) : ViewModel() {

    data class HL7UiState(
        val isLoading: Boolean = true,
        val user: User = User("", "", ""),
        val testResults: List<TestResult> = emptyList(),
    )

    private val _uiState: MutableStateFlow<HL7UiState> = MutableStateFlow(HL7UiState())
    val uiState: StateFlow<HL7UiState> = _uiState.asStateFlow()

    private val _events = Channel<LoadHL7FileEvent>()
    val events = _events.receiveAsFlow()

    init {
        loadFromDatabase()
    }

    private fun loadFromDatabase() {
        viewModelScope.launch {
            try {
                val flowHL7FileUpdates = processHL7DataUseCase.observeChangesForHL7File()
                flowHL7FileUpdates.collectLatest { HL7FileUpdates ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            user = HL7FileUpdates.first,
                            testResults = HL7FileUpdates.second
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
            val file = File(context.filesDir, "Beispiel%20HL7.hl7")
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

                processHL7DataUseCase.clearDatabaseData()
                val hl7parsed = processHL7DataUseCase.parseToHL7DataObject(hl7Raw)

                if (hl7parsed != null) {
                    processHL7DataUseCase.saveHL7DataToDatabase(hl7parsed)
                    obxReadStatusUseCase.addObxIdsAsUnread(hl7parsed.obxSegmentList.map { it.setId })
                } else {
                    _events.send(LoadHL7FileEvent.ShowSnackbar("Failed to parse HL7 file."))
                }
                _events.send(LoadHL7FileEvent.ShowSnackbar("HL7 file parsed and saved successfully!"))
                _uiState.update { it.copy(isLoading = false) }

            } catch (e: Exception) {
                Log.w("FILE READING", "EXCEPTION: ${e.message}")
            }
        }
    }

    fun markTestResultAsRead(id: Long) {
        viewModelScope.launch {
            obxReadStatusUseCase.markObxAsRead(id, true)
        }
    }

    fun parseRange(range: String?): Pair<Float?, Float?> {
        val trimmed = range?.trim() ?: ""

        return when {
            trimmed.startsWith("<") -> {
                val upper = trimmed.removePrefix("<").trim().toFloatOrNull()
                Pair(null, upper)
            }

            trimmed.contains("-") -> {
                val parts = trimmed.split("-").map { it.trim() }
                val low = parts.getOrNull(0)?.toFloatOrNull()
                val high = parts.getOrNull(1)?.toFloatOrNull()
                Pair(low, high)
            }

            else -> Pair(null, null)
        }
    }

    fun formatBirthday(dateString: String): String {
        val inputFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("d.M.yyyy", Locale.GERMAN)

        try {
            val date = inputFormat.parse(dateString)
            return date?.let { outputFormat.format(it) } ?: ""
        } catch (e: Exception) {
            println("Error formatting birthday: ${e.message}")
            return dateString
        }
    }
}