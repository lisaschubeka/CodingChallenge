// data/repository/HL7MessageRepositoryImpl.kt
package com.example.codingchallenge.data.repository

import com.example.codingchallenge.app.AppDatabase
import com.example.codingchallenge.data.model.ObxReadStatusEntity
import com.example.codingchallenge.domain.repository.OBXReadStatusRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OBXReadStatusRepositoryImpl @Inject constructor(
    database: AppDatabase
) : OBXReadStatusRepository {

    private val obxReadStatusDao = database.obxReadStatusDao()

    override suspend fun markObxAsRead(obxId: Long, isRead: Boolean) {
        val status = ObxReadStatusEntity(obxId = obxId, isRead = isRead)
        obxReadStatusDao.insertObxReadStatus(status)
    }

    override fun observeObxReadStatus(obxId: Long): Flow<Boolean> {
        return obxReadStatusDao.getObxReadStatus(obxId)
            .map { it?.isRead ?: false }
            .distinctUntilChanged()
    }

    override suspend fun getObxReadStatus(obxId: Long): Boolean {
        return obxReadStatusDao.getObxReadStatus(obxId).map { it?.isRead ?: false }.first()
    }

    override suspend fun addObxIdsAsUnread(obxIds: List<Long>) {
        val unreadStatuses = obxIds.map { ObxReadStatusEntity(obxId = it, isRead = false) }
        obxReadStatusDao.insertAllObxAsUnread(unreadStatuses)
    }

    override fun observeAmountObxNotRead(): Flow<Int> {
        return obxReadStatusDao.observeAmountObxNotRead()
    }

}