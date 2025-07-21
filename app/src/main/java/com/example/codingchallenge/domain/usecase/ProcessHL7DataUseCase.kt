package com.example.codingchallenge.domain.usecase

import com.example.codingchallenge.domain.model.HL7Data
import com.example.codingchallenge.domain.model.TestResult
import com.example.codingchallenge.domain.model.User
import kotlinx.coroutines.flow.Flow

// Top level use case used by HL7ViewModel
interface ProcessHL7DataUseCase {
    fun parseToHL7DataObject(hl7Raw: String): HL7Data?

    fun observeChangesInDatabase(
    ): Flow<Pair<User, List<TestResult>>>

    suspend fun saveHL7DataToDatabase(hl7data: HL7Data)
    suspend fun clearDatabaseData()
}