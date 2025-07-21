package com.example.codingchallenge.domain.repository

import com.example.codingchallenge.domain.model.HL7Data
import com.example.codingchallenge.domain.model.ObxReadStatus
import kotlinx.coroutines.flow.Flow

interface HL7Repository {
    suspend fun markObxAsRead(obxId: Long, isRead: Boolean)
    suspend fun addObxIdsAsUnread(obxIds: List<Long>)

    suspend fun saveHL7FileData(hl7Data: HL7Data)
    suspend fun clearDatabase()

    fun observeOBXReadStatusFromDatabase(): Flow<List<ObxReadStatus>>
    fun observeHL7FileData(): Flow<HL7Data>

}