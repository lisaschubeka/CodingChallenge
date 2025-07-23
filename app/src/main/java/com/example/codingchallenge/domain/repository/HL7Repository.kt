package com.example.codingchallenge.domain.repository

import com.example.codingchallenge.domain.model.HL7Data
import kotlinx.coroutines.flow.Flow

interface HL7Repository {

    suspend fun saveHL7FileData(hl7Data: HL7Data)
    suspend fun clearDatabase()
    fun observeHL7FileData(): Flow<HL7Data>

}