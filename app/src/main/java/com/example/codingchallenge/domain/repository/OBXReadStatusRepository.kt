package com.example.codingchallenge.domain.repository

import com.example.codingchallenge.domain.model.ObxReadStatus
import kotlinx.coroutines.flow.Flow

interface OBXReadStatusRepository {
    suspend fun markObxAsRead(obxId: Long, isRead: Boolean)
    suspend fun addObxIdsAsUnread(obxIds: List<Long>)
    fun observeOBXReadStatusFromDatabase(): Flow<List<ObxReadStatus>>
    suspend fun clearDatabase()
}