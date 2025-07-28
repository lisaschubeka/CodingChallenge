package com.example.codingchallenge.domain.repository

import com.example.codingchallenge.domain.model.HL7Data
import com.example.codingchallenge.domain.model.hl7Segment.MSHSegment
import com.example.codingchallenge.domain.model.hl7Segment.NTESegment
import com.example.codingchallenge.domain.model.hl7Segment.OBXSegment
import com.example.codingchallenge.domain.model.hl7Segment.PIDSegment
import kotlinx.coroutines.flow.Flow

interface HL7Repository {
    // TODO because
//    suspend fun saveHL7FileData(hl7Data: HL7Dataata)
    suspend fun saveMSHSegment(mshSegment: MSHSegment): Long
    suspend fun savePIDSegment(pidSegment: PIDSegment): Long
    suspend fun saveOBXSegment(obxSegment: OBXSegment): Long
    suspend fun saveNTESegment(nteSegment: NTESegment): Long

    suspend fun clearDatabase()
    fun observeHL7FileData(): Flow<List<HL7Data>>

}