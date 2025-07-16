// data/repository/HL7MessageRepositoryImpl.kt
package com.example.codingchallenge.data.repository

import com.example.codingchallenge.app.AppDatabase
import com.example.codingchallenge.data.model.ObxReadStatusEntity
import com.example.codingchallenge.data.model.mapToDomain
import com.example.codingchallenge.domain.model.HL7Data
import com.example.codingchallenge.domain.model.ObxReadStatus
import com.example.codingchallenge.domain.model.hl7Segment.NTESegment
import com.example.codingchallenge.domain.model.hl7Segment.NTESegmentEntity
import com.example.codingchallenge.domain.model.hl7Segment.OBXSegment
import com.example.codingchallenge.domain.model.hl7Segment.mapToDomain
import com.example.codingchallenge.domain.model.hl7Segment.mapToEntity
import com.example.codingchallenge.domain.repository.HL7Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HL7RepositoryImpl @Inject constructor(
    private val database: AppDatabase
) : HL7Repository {

    private val obxReadStatusDao = database.obxReadStatusDao()

    private val mshSegmentDao = database.mshSegmentDao()
    private val pidSegmentDao = database.pidSegmentDao()
    private val obxSegmentDao = database.obxSegmentDao()
    private val nteSegmentDao = database.nteSegmentDao()

    override suspend fun markObxAsRead(obxId: Long, isRead: Boolean) {
        val status = ObxReadStatusEntity(obxId = obxId, isRead = isRead)
        obxReadStatusDao.insertObxReadStatus(status)
    }

    // TODO collect a flow of list of booleans instead
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

    override suspend fun saveHL7FileData(hl7Data: HL7Data) {
        database.runInTransaction {

            mshSegmentDao.deleteAll()

            val mshEntity = hl7Data.msh.mapToEntity()
            val mshId = mshSegmentDao.insertMSHSegmentEntity(mshEntity)

            val pidSegmentEntity = hl7Data.pid.mapToEntity(mshId)
            pidSegmentDao.insertPIDSegmentEntity(pidSegmentEntity)

            val obxEntitiesToInsert = hl7Data.obxSegmentList.map { obxSegment ->
                obxSegment.mapToEntity(mshId)
            }
            obxSegmentDao.insertAllObxSegments(obxEntitiesToInsert)

            val nteEntitiesToInsert: List<NTESegmentEntity> =
                hl7Data.nteMap.flatMap { (obxIdFromMap, nteSegmentsForObx) ->
                    nteSegmentsForObx.map { nteSegment ->
                        nteSegment.mapToEntity(obxIdFromMap)
                    }
                }
            nteSegmentDao.insertAllNteSegments(nteEntitiesToInsert)
        }
    }

    override suspend fun retrieveHL7FileData(): HL7Data {
        val msh = mshSegmentDao.getMshSegment()
            ?: throw IllegalStateException("")
        val pid = pidSegmentDao.getPidSegment()
            ?: throw IllegalStateException("")
        val obxList = obxSegmentDao.getAllObxSegments()
        val nteList = nteSegmentDao.getAllNteSegments()
            .map { nteSegmentEntity -> nteSegmentEntity.mapToDomain() }

        val nteMap: Map<Long, List<NTESegment>> = nteList.groupBy { it.setId }

        return HL7Data(
            msh.mapToDomain(),
            pid.mapToDomain(),
            obxList.map { obxSegmentEntity -> obxSegmentEntity.mapToDomain() },
            nteMap
        )
    }

    override suspend fun clearDatabase() {
        // should cascade and delete all entries in database
        mshSegmentDao.deleteAll()

        obxReadStatusDao.deleteAll()
    }

    override suspend fun observeOBXReadStatusFromDatabase(): Flow<List<ObxReadStatus>> {
        return obxReadStatusDao.observeAllObxNotRead()
            .map { obxReadStatusList ->
                obxReadStatusList
                    .map { obxReadStatusEntity -> obxReadStatusEntity.mapToDomain() }
            }
    }

    override suspend fun observeObxSegmentsFromDatabase(): Flow<List<OBXSegment>> {
        return obxSegmentDao.observeAllObxSegments()
            .map { obxSegmentList ->
                obxSegmentList
                    .map { obxSegmentEntity -> obxSegmentEntity.mapToDomain() }
            }
    }

}