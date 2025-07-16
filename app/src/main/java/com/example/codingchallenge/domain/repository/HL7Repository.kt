package com.example.codingchallenge.domain.repository

import com.example.codingchallenge.domain.model.HL7Data
import com.example.codingchallenge.domain.model.ObxReadStatus
import com.example.codingchallenge.domain.model.hl7Segment.OBXSegment
import kotlinx.coroutines.flow.Flow

interface HL7Repository {
    // TODO clean up the observeObxReadStatus and observeAmountObxNotRead functions
    suspend fun markObxAsRead(obxId: Long, isRead: Boolean)
    fun observeObxReadStatus(obxId: Long): Flow<Boolean>
    suspend fun getObxReadStatus(obxId: Long): Boolean
    suspend fun addObxIdsAsUnread(obxIds: List<Long>)
    fun observeAmountObxNotRead(): Flow<Int>


    suspend fun saveHL7FileData(hl7Data: HL7Data)

    // TODO optional, since we have flows we dont need entire HL7 file,
    //  only MSH and PID segments, this can be refactored to those two functions
    suspend fun retrieveHL7FileData(): HL7Data

    suspend fun clearDatabase()
    suspend fun observeOBXReadStatusFromDatabase(): Flow<List<ObxReadStatus>>
    suspend fun observeObxSegmentsFromDatabase(): Flow<List<OBXSegment>>
}