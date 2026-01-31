package com.example.codingchallenge.domain.repository

import com.example.codingchallenge.domain.model.hl7Segment.ObxReadStatus
import kotlinx.coroutines.flow.Flow

interface OBXReadStatusRepository {
    suspend fun markObxAsRead(obxId: Long, isRead: Boolean)
    suspend fun addObxIdsAsUnread(obxIds: List<Long>)
    suspend fun addObxReadStatusAsUnread(obxId: Long)
    fun observeOBXReadStatusFromDatabase(): Flow<List<ObxReadStatus>>
    suspend fun clearDatabase()
}