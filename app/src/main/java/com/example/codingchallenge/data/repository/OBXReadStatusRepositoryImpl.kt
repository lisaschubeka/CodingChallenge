package com.example.codingchallenge.data.repository

import com.example.codingchallenge.app.AppDatabase
import com.example.codingchallenge.data.model.ObxReadStatusEntity
import com.example.codingchallenge.data.model.mapToDomain
import com.example.codingchallenge.domain.model.ObxReadStatus
import com.example.codingchallenge.domain.repository.OBXReadStatusRepository
import kotlinx.coroutines.flow.Flow
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

    override suspend fun addObxIdsAsUnread(obxIds: List<Long>) {
        val unreadStatuses = obxIds.map { ObxReadStatusEntity(obxId = it, isRead = false) }
        obxReadStatusDao.insertAllObxAsUnread(unreadStatuses)
    }

    override fun observeOBXReadStatusFromDatabase(): Flow<List<ObxReadStatus>> {
        return obxReadStatusDao.observeAllObxNotRead().map { obxReadStatusList ->
            obxReadStatusList.map { obxReadStatusEntity -> obxReadStatusEntity.mapToDomain() }
        }
    }

    override suspend fun clearDatabase() {
        obxReadStatusDao.deleteAll()
    }
}