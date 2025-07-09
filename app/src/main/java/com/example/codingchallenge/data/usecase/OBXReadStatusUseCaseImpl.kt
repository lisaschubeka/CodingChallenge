package com.example.codingchallenge.data.usecase

import com.example.codingchallenge.domain.repository.OBXReadStatusRepository
import com.example.codingchallenge.domain.usecase.OBXReadStatusUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OBXReadStatusUseCaseImpl @Inject constructor(
    private val repository: OBXReadStatusRepository
) : OBXReadStatusUseCase {
    override suspend fun markObxAsRead(obxId: Long, isRead: Boolean) {
        repository.markObxAsRead(obxId, isRead)
    }

    override fun observeObxReadStatus(obxId: Long): Flow<Boolean> {
        return repository.observeObxReadStatus(obxId)
    }

    override suspend fun addObxIdsAsUnread(obxIds: List<Long>) {
        repository.addObxIdsAsUnread(obxIds)
    }

    override fun getAmountObxNotRead(): Flow<Int> {
        return repository.observeAmountObxNotRead()
    }
}