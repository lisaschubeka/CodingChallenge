package com.example.codingchallenge.domain.usecase

import kotlinx.coroutines.flow.Flow

interface OBXReadStatusUseCase {
    suspend fun markObxAsRead(obxId: Long, isRead: Boolean)
    fun observeObxReadStatus(obxId: Long): Flow<Boolean>
    suspend fun addObxIdsAsUnread(obxIds: List<Long>)
    fun getAmountObxNotRead(): Flow<Int>
}