package com.example.codingchallenge.domain.usecase

import com.example.codingchallenge.domain.model.TestResult
import com.example.codingchallenge.domain.model.hl7Segment.NTESegment
import com.example.codingchallenge.domain.model.hl7Segment.OBXSegment

interface ParseToTestResultsUseCase {
    fun parseToTestResults(
        obxSegments: List<OBXSegment>,
        nteMap: Map<Int, List<NTESegment>>
    ): List<TestResult>
}