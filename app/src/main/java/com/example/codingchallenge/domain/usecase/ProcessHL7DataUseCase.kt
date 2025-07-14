package com.example.codingchallenge.domain.usecase

import com.example.codingchallenge.domain.model.HL7Data
import com.example.codingchallenge.domain.model.TestResult
import com.example.codingchallenge.domain.model.User
import com.example.codingchallenge.domain.model.hl7Segment.MSHSegment
import com.example.codingchallenge.domain.model.hl7Segment.NTESegment
import com.example.codingchallenge.domain.model.hl7Segment.OBXSegment
import com.example.codingchallenge.domain.model.hl7Segment.PIDSegment

// Top level Usecase used by HL7ViewModel
interface ProcessHL7DataUseCase {
    fun parseToHL7Data(): HL7Data?
    fun mapToTestResult(
        obxSegments: List<OBXSegment>,
        nteMap: Map<Long, List<NTESegment>>
    ): List<TestResult>

    fun mapToUser(pidSegment: PIDSegment?, mshSegment: MSHSegment?): User

    suspend fun save(hl7data: HL7Data)
    suspend fun retrieve(): HL7Data
}