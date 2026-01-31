package com.example.codingchallenge.domain.repository

import com.example.codingchallenge.data.model.HL7DataEntity
import com.example.codingchallenge.domain.model.hl7Segment.MSHSegment
import com.example.codingchallenge.domain.model.hl7Segment.NTESegment
import com.example.codingchallenge.domain.model.hl7Segment.OBXSegment
import com.example.codingchallenge.domain.model.hl7Segment.PIDSegment
import kotlinx.coroutines.flow.Flow

interface FileRepository {
    suspend fun saveMSHSegment(mshSegment: MSHSegment): Long
    suspend fun savePIDSegment(pidSegment: PIDSegment): Long
    suspend fun saveOBXSegment(obxSegment: OBXSegment): Long
    suspend fun saveNTESegment(nteSegment: NTESegment): Long

    fun observeOverviewFileData(): Flow<List<HL7DataEntity>>
    fun observeHL7FileData(mshId: Long): Flow<HL7DataEntity>
}