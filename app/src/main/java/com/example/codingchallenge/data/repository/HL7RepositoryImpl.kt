// data/repository/HL7MessageRepositoryImpl.kt
package com.example.codingchallenge.data.repository

import com.example.codingchallenge.app.AppDatabase
import com.example.codingchallenge.domain.model.HL7Data
import com.example.codingchallenge.domain.model.hl7Segment.MSHSegment
import com.example.codingchallenge.domain.model.hl7Segment.NTESegment
import com.example.codingchallenge.domain.model.hl7Segment.NTESegmentEntity
import com.example.codingchallenge.domain.model.hl7Segment.PIDSegment
import com.example.codingchallenge.domain.model.hl7Segment.mapToDomain
import com.example.codingchallenge.domain.model.hl7Segment.mapToEntity
import com.example.codingchallenge.domain.repository.HL7Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class HL7RepositoryImpl @Inject constructor(
    private val database: AppDatabase
) : HL7Repository {


    private val mshSegmentDao = database.mshSegmentDao()
    private val pidSegmentDao = database.pidSegmentDao()
    private val obxSegmentDao = database.obxSegmentDao()
    private val nteSegmentDao = database.nteSegmentDao()

    override suspend fun saveHL7FileData(hl7Data: HL7Data) {
        database.runInTransaction {

            mshSegmentDao.deleteAll()

            val mshEntity = hl7Data.msh?.mapToEntity()
            if (mshEntity != null) {
                val mshId = mshSegmentDao.insertMSHSegmentEntity(mshEntity)
                val pidSegmentEntity = hl7Data.pid?.mapToEntity(mshId)
                if (pidSegmentEntity != null) {
                    pidSegmentDao.insertPIDSegmentEntity(pidSegmentEntity)
                    val obxEntitiesToInsert = hl7Data.obxSegmentList.map { obxSegment ->
                        obxSegment.mapToEntity(mshId)
                    }
                    obxSegmentDao.insertAllObxSegments(obxEntitiesToInsert)

                }
            }

            val nteEntitiesToInsert: List<NTESegmentEntity> =
                hl7Data.nteMap.flatMap { (obxIdFromMap, nteSegmentsForObx) ->
                    nteSegmentsForObx.map { nteSegment ->
                        nteSegment.mapToEntity(obxIdFromMap)
                    }
                }
            nteSegmentDao.insertAllNteSegments(nteEntitiesToInsert)
        }
    }

    override fun observeHL7FileData(): Flow<HL7Data> {
        val mshFlow = mshSegmentDao.observeMshSegment()
        val pidFlow = pidSegmentDao.observePidSegment()
        val obxFlow = obxSegmentDao.observeAllObxSegments()
        val nteFlow = nteSegmentDao.observeAllNteSegments()

        return combine(
            mshFlow,
            pidFlow,
            obxFlow,
            nteFlow
        ) { mshEntity, pidEntity, obxEntities, nteEntities ->

            val msh: MSHSegment? = mshEntity?.mapToDomain()
            val pid: PIDSegment? = pidEntity?.mapToDomain()
            val obxList = obxEntities.map { it.mapToDomain() }
            val nteList = nteEntities.map { it.mapToDomain() }

            val nteMap: Map<Long, List<NTESegment>> = nteList.groupBy { it.setId }

            HL7Data(
                msh = msh,
                pid = pid,
                obxSegmentList = obxList,
                nteMap = nteMap
            )
        }
    }

    override suspend fun clearDatabase() {
        // should cascade and delete all entries in database
        mshSegmentDao.deleteAll()
    }

}