package com.example.codingchallenge.domain.usecase

import com.example.codingchallenge.domain.model.ObxReadStatus
import com.example.codingchallenge.domain.model.TestResult
import com.example.codingchallenge.domain.model.hl7Segment.NTESegment
import com.example.codingchallenge.domain.model.hl7Segment.OBXSegment
import kotlinx.coroutines.flow.Flow

interface CombineTestResultsUseCase {
    fun convertObxSegmentToTestResult(
        obxSegment: OBXSegment,
        nteList: List<NTESegment>?,
        isRead: Boolean
    ): TestResult

    fun combineToFlowTestResults(
        flowObxSegments: Flow<List<OBXSegment>>,
        flowReadStatus: Flow<List<ObxReadStatus>>,
        nteMap: Map<Long, List<NTESegment>>
    ): Flow<List<TestResult>>
}