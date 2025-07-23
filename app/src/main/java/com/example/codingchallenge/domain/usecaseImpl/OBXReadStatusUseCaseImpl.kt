package com.example.codingchallenge.domain.usecaseImpl

import com.example.codingchallenge.domain.repository.OBXReadStatusRepository
import com.example.codingchallenge.domain.usecase.OBXReadStatusUseCase
import javax.inject.Inject

class OBXReadStatusUseCaseImpl @Inject constructor(
    private val repository: OBXReadStatusRepository
) : OBXReadStatusUseCase {
    override suspend fun markObxAsRead(obxId: Long, isRead: Boolean) {
        repository.markObxAsRead(obxId, isRead)
    }

    override suspend fun addObxIdsAsUnread(obxIds: List<Long>) {
        repository.addObxIdsAsUnread(obxIds)
    }
}