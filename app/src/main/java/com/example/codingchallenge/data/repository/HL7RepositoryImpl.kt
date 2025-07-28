// data/repository/HL7MessageRepositoryImpl.kt
package com.example.codingchallenge.data.repository

import android.util.Log
import com.example.codingchallenge.app.AppDatabase
import com.example.codingchallenge.data.model.NTESegmentEntity
import com.example.codingchallenge.data.model.mapToDomain
import com.example.codingchallenge.data.model.mapToEntity
import com.example.codingchallenge.domain.model.HL7Data
import com.example.codingchallenge.domain.model.hl7Segment.MSHSegment
import com.example.codingchallenge.domain.model.hl7Segment.NTESegment
import com.example.codingchallenge.domain.model.hl7Segment.OBXSegment
import com.example.codingchallenge.domain.model.hl7Segment.OBXSegmentEntity
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

    override suspend fun saveMSHSegment(mshSegment: MSHSegment): Long {
        return mshSegmentDao.insertMSHSegmentEntity(mshSegment.mapToEntity())
    }

    override suspend fun savePIDSegment(pidSegment: PIDSegment): Long {
        return pidSegmentDao.insertPIDSegmentEntity(segment = pidSegment.mapToEntity())
    }

    override suspend fun saveOBXSegment(obxSegment: OBXSegment): Long {
        return obxSegmentDao.insertObxSegment(segment = obxSegment.mapToEntity())
    }

    override suspend fun saveNTESegment(nteSegment: NTESegment): Long {
        return nteSegmentDao.insertNteSegment(segment = nteSegment.mapToEntity())
    }

    override suspend fun saveHL7FileData(hl7Data: HL7Data) {
        database.runInTransaction {

            val mshEntity = hl7Data.msh?.mapToEntity()
            if (mshEntity != null) {
                val mshId = mshSegmentDao.insertMSHSegmentEntity(mshEntity)
                val pidSegmentEntity = hl7Data.pid?.mapToEntity()
                if (pidSegmentEntity != null) {
                    pidSegmentDao.insertPIDSegmentEntity(pidSegmentEntity)
                    val obxEntitiesToInsert = hl7Data.obxSegmentList.map { obxSegment ->
                        obxSegment.mapToEntity()
                    }
                    obxSegmentDao.insertAllObxSegments(obxEntitiesToInsert)

                }
            }

            val nteEntitiesToInsert: List<NTESegmentEntity> =
                hl7Data.nteMap.flatMap { (obxIdFromMap, nteSegmentsForObx) ->
                    nteSegmentsForObx.map { nteSegment ->
                        nteSegment.mapToEntity()
                    }
                }
            nteSegmentDao.insertAllNteSegments(nteEntitiesToInsert)
        }
    }

    override fun observeHL7FileData(): Flow<List<HL7Data>> {
        val mshFlow = mshSegmentDao.observeAllMshSegments()
        val pidFlow = pidSegmentDao.observeAllPidSegments()
        val obxFlow = obxSegmentDao.observeAllObxSegments()
        val nteFlow = nteSegmentDao.observeAllNteSegments()

        return combine(
            mshFlow,
            pidFlow,
            obxFlow,
            nteFlow
        ) { mshEntities, pidEntities, obxEntities, nteEntities ->

//            val mshList = mshEntities.map { it.mapToDomain() }
            val pidList = pidEntities.map { it.mapToDomain() }
            val obxList = obxEntities.map { it.mapToDomain() }
            val nteList = nteEntities.map { it.mapToDomain() }

            val pidMap: Map<Long, PIDSegment> = pidList.associateBy { it.mshId }
            val obxMap: Map<Long, List<OBXSegmentEntity>> = obxEntities.groupBy { it.mshId }
            val nteMap: Map<Long, List<NTESegment>> = nteList.groupBy { it.obxId }

            val allHL7Data = mutableListOf<HL7Data>()

            for (mshSegment in mshEntities) {
                val currentMshId = mshSegment.mshId

                val pidSegment = pidMap[currentMshId]
                val obxSegmentsForMsh = obxMap[currentMshId] ?: emptyList()

                if (pidSegment != null) {
                    allHL7Data.add(
                        HL7Data(
                            msh = mshSegment.mapToDomain(),
                            pid = pidSegment,
                            obxSegmentList = obxSegmentsForMsh.map { it.mapToDomain() },
                            nteMap = nteMap
                        )
                    )
                } else {
                    Log.w(
                        "HL7Repository",
                        "Skipping HL7Data for MSH ID $currentMshId: PID segment not found."
                    )
                }
            }
            allHL7Data
        }
    }

    // TODO DOES THIS also need to be changed???
    override suspend fun clearDatabase() {
        // should cascade and delete all entries in database
        mshSegmentDao.deleteAll()
    }

}