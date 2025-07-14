package com.example.codingchallenge.domain.repository

import com.example.codingchallenge.domain.model.HL7Data
import kotlinx.coroutines.flow.Flow

interface HL7Repository {

    suspend fun markObxAsRead(obxId: Long, isRead: Boolean)
    fun observeObxReadStatus(obxId: Long): Flow<Boolean>
    suspend fun getObxReadStatus(obxId: Long): Boolean
    suspend fun addObxIdsAsUnread(obxIds: List<Long>)
    fun observeAmountObxNotRead(): Flow<Int>
    suspend fun saveHL7FileData(hl7Data: HL7Data)
    suspend fun retrieveHL7FileData(): HL7Data
    suspend fun clearDatabase()
}