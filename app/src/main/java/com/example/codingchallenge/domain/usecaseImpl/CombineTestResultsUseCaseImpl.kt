package com.example.codingchallenge.domain.usecaseImpl

import com.example.codingchallenge.domain.model.ObxReadStatus
import com.example.codingchallenge.domain.model.TestResult
import com.example.codingchallenge.domain.model.hl7Segment.NTESegment
import com.example.codingchallenge.domain.model.hl7Segment.OBXSegment
import com.example.codingchallenge.domain.usecase.CombineTestResultsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class CombineTestResultsUseCaseImpl @Inject constructor() : CombineTestResultsUseCase {
    override fun convertObxSegmentToTestResult(
        obxSegment: OBXSegment,
        nteList: List<NTESegment>?,
        isRead: Boolean
    ): TestResult {
        val id = obxSegment.setId
        val testName = obxSegment.observationIdentifier?.split("^")?.get(1) ?: ""
        val value = obxSegment.observationValue ?: ""
        val unit = obxSegment.units ?: ""
        val range = obxSegment.referencesRange ?: ""

        val note = nteList?.map({ note -> note.comment })?.joinToString(separator = " ")
        return TestResult(id, testName, value, unit, range, note, isRead)
    }

    override fun combineToFlowTestResults(
        flowObxSegments: Flow<List<OBXSegment>>,
        flowReadStatus: Flow<List<ObxReadStatus>>,
        nteMap: Map<Long, List<NTESegment>>
    ): Flow<List<TestResult>> {
        return flowObxSegments.combine(flowReadStatus) { listObxSegment, listReadStatus ->
            val listTestResults = mutableListOf<TestResult>()
            val mapReadStatus = listReadStatus.associateBy { it.obxId }

            for (obxSegment in listObxSegment) {
                val matchingReadStatus = mapReadStatus[obxSegment.setId]
                if (matchingReadStatus != null) {
                    listTestResults.add(
                        convertObxSegmentToTestResult(
                            obxSegment, nteMap[obxSegment.setId], matchingReadStatus.isRead
                        )
                    )
                }
            }
            listTestResults
        }

    }
}