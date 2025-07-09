package com.example.codingchallenge.domain.usecase

import com.example.codingchallenge.domain.model.hl7Segment.MSHSegment
import com.example.codingchallenge.domain.model.hl7Segment.NTESegment
import com.example.codingchallenge.domain.model.hl7Segment.OBXSegment
import com.example.codingchallenge.domain.model.hl7Segment.PIDSegment

// TODO since these functions are so similar is it ok to put them in the same place
interface CreateSegmentUseCase {
    fun createMSHSegment(stringSegment: List<String>): MSHSegment
    fun createPIDSegment(stringSegment: List<String>): PIDSegment
    fun createOBXSegment(stringSegment: List<String>): OBXSegment
    fun createNTESegment(stringSegment: List<String>): NTESegment
}