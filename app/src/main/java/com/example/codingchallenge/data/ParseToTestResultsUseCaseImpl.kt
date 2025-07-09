package com.example.codingchallenge.data

import com.example.codingchallenge.domain.model.TestResult
import com.example.codingchallenge.domain.model.hl7Segment.NTESegment
import com.example.codingchallenge.domain.model.hl7Segment.OBXSegment
import com.example.codingchallenge.domain.usecase.ParseToTestResultsUseCase
import javax.inject.Inject

class ParseToTestResultsUseCaseImpl @Inject constructor() : ParseToTestResultsUseCase {
    override fun parseToTestResults(
        obxSegments: List<OBXSegment>,
        nteMap: Map<Int, List<NTESegment>>
    ): List<TestResult> {
        return obxSegments.mapNotNull { obxSegment ->

            if (!hasValidTestValue(obxSegment) || !hasValidTestRange(obxSegment)) {
                return@mapNotNull null
            }

            val id = obxSegment.setID ?: ""
            val testName = obxSegment.observationIdentifier?.split("^")?.get(1) ?: ""
            val value = obxSegment.observationValue ?: ""
            val unit = obxSegment.units ?: ""
            val range = obxSegment.referencesRange ?: ""

            val note = nteMap[obxSegment.setID?.toIntOrNull()]
                ?.mapNotNull { it.comment?.trim() }
                ?.joinToString(separator = " ") ?: ""

            TestResult(id, testName, value, unit, range, note)
        }
    }

    private fun hasValidTestRange(obxValue: OBXSegment): Boolean {
        return !obxValue.referencesRange.isNullOrBlank()
    }

    private fun hasValidTestValue(obxValue: OBXSegment): Boolean {
        return obxValue.observationValue != "!!Storno"
    }
}