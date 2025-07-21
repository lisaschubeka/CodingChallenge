package com.example.codingchallenge.domain.usecase

import com.example.codingchallenge.domain.model.HL7Data
import com.example.codingchallenge.domain.model.ObxReadStatus
import com.example.codingchallenge.domain.model.TestResult
import com.example.codingchallenge.domain.model.User
import com.example.codingchallenge.domain.model.hl7Segment.MSHSegment
import com.example.codingchallenge.domain.model.hl7Segment.NTESegment
import com.example.codingchallenge.domain.model.hl7Segment.OBXSegment
import com.example.codingchallenge.domain.model.hl7Segment.PIDSegment
import kotlinx.coroutines.flow.Flow

interface CombineTestResultsUseCase {
    fun convertObxSegmentToTestResult(
        obxSegment: OBXSegment,
        nteList: List<NTESegment>?,
        isRead: Boolean
    ): TestResult

    fun combineToFlowTestResults(
        flowHL7Data: Flow<HL7Data>,
        flowReadStatus: Flow<List<ObxReadStatus>>,
    ): Flow<Pair<User, List<TestResult>>>

    fun mapToUser(pidSegment: PIDSegment?, mshSegment: MSHSegment?): User

}