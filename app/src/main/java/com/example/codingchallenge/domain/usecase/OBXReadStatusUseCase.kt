package com.example.codingchallenge.domain.usecase

interface OBXReadStatusUseCase {
    suspend fun markObxAsRead(obxId: Long, isRead: Boolean)
    suspend fun addObxIdsAsUnread(obxIds: List<Long>)
}