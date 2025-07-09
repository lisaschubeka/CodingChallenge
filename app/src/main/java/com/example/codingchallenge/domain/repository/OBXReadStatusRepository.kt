package com.example.codingchallenge.domain.repository

import kotlinx.coroutines.flow.Flow

interface OBXReadStatusRepository {

    suspend fun markObxAsRead(obxId: Long, isRead: Boolean)
    fun observeObxReadStatus(obxId: Long): Flow<Boolean>
    suspend fun getObxReadStatus(obxId: Long): Boolean
    suspend fun addObxIdsAsUnread(obxIds: List<Long>)
    fun observeAmountObxNotRead(): Flow<Int>
}