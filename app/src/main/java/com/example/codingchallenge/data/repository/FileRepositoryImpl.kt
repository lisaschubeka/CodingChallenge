// data/repository/HL7MessageRepositoryImpl.kt
package com.example.codingchallenge.data.repository

import com.example.codingchallenge.app.AppDatabase
import com.example.codingchallenge.data.model.HL7DataEntity
import com.example.codingchallenge.data.model.mapToEntity
import com.example.codingchallenge.domain.model.hl7Segment.MSHSegment
import com.example.codingchallenge.domain.model.hl7Segment.NTESegment
import com.example.codingchallenge.domain.model.hl7Segment.OBXSegment
import com.example.codingchallenge.domain.model.hl7Segment.PIDSegment
import com.example.codingchallenge.domain.model.hl7Segment.mapToEntity
import com.example.codingchallenge.domain.repository.FileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FileRepositoryImpl @Inject constructor(
    database: AppDatabase
) : FileRepository {


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

    override fun observeHL7FileData(mshId: Long): Flow<HL7DataEntity> {
        return mshSegmentDao.getHL7DataEntity(mshId)
    }

    override fun observeOverviewFileData(): Flow<List<HL7DataEntity>> {
        return mshSegmentDao.getHL7DataEntityList()
    }
}