package com.example.codingchallenge.domain.usecaseImpl

import com.example.codingchallenge.domain.model.TestResult
import com.example.codingchallenge.domain.model.hl7Segment.NTESegment
import com.example.codingchallenge.domain.model.hl7Segment.OBXSegment
import com.example.codingchallenge.domain.usecase.ParseToTestResultsUseCase
import javax.inject.Inject

class ParseToTestResultsUseCaseImpl @Inject constructor() : ParseToTestResultsUseCase {
    override fun parseToTestResults(
        obxSegments: List<OBXSegment>,
        nteMap: Map<Long, List<NTESegment>>
    ): List<TestResult> {
        return obxSegments.map { obxSegment ->

            val id = obxSegment.setId // TODO check if -1 is ever fulfilled
            val testName = obxSegment.observationIdentifier?.split("^")?.get(1) ?: ""
            val value = obxSegment.observationValue ?: ""
            val unit = obxSegment.units ?: ""
            val range = obxSegment.referencesRange ?: ""

            val note = nteMap[obxSegment.setId]
                ?.mapNotNull { it.comment?.trim() }
                ?.joinToString(separator = " ") ?: ""

            TestResult(id, testName, value, unit, range, note)
        }
    }
}