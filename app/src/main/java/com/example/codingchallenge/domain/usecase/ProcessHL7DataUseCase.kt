package com.example.codingchallenge.domain.usecase

import com.example.codingchallenge.domain.model.HL7Data
import com.example.codingchallenge.domain.model.ObxReadStatus
import com.example.codingchallenge.domain.model.TestResult
import com.example.codingchallenge.domain.model.User
import com.example.codingchallenge.domain.model.hl7Segment.MSHSegment
import com.example.codingchallenge.domain.model.hl7Segment.OBXSegment
import com.example.codingchallenge.domain.model.hl7Segment.PIDSegment
import kotlinx.coroutines.flow.Flow

// Top level use case used by HL7ViewModel
interface ProcessHL7DataUseCase {
    fun parseToHL7DataObject(): HL7Data?
    suspend fun observeTestResults(
    ): Flow<List<TestResult>>

    fun mapToUser(pidSegment: PIDSegment?, mshSegment: MSHSegment?): User

    suspend fun saveHL7DataToDatabase(hl7data: HL7Data)
    suspend fun retrieveHL7DataFromDatabase(): HL7Data
    suspend fun observeObxSegmentsFromDatabase(): Flow<List<OBXSegment>>
    suspend fun observeOBXReadStatusFromDatabase(): Flow<List<ObxReadStatus>>
    suspend fun clearDatabaseData()
}