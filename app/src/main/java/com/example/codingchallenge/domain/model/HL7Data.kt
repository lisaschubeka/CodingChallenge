package com.example.codingchallenge.domain.model

import com.example.codingchallenge.domain.model.hl7Segment.MSHSegment
import com.example.codingchallenge.domain.model.hl7Segment.NTESegment
import com.example.codingchallenge.domain.model.hl7Segment.OBXSegment
import com.example.codingchallenge.domain.model.hl7Segment.PIDSegment

// Only contains relevant classes for this project
data class HL7Data(
    val msh: MSHSegment?,
    val pid: PIDSegment?,
    val obxSegmentList: List<OBXSegment> = emptyList(),
    val nteMap: Map<Long, List<NTESegment>> = emptyMap(),
)
