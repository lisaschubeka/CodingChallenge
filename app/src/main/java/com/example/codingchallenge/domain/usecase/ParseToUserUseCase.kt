package com.example.codingchallenge.domain.usecase

import com.example.codingchallenge.domain.model.User
import com.example.codingchallenge.domain.model.hl7Segment.MSHSegment
import com.example.codingchallenge.domain.model.hl7Segment.PIDSegment

interface ParseToUserUseCase {
    fun parseToUser(pidSegment: PIDSegment?, mshSegment: MSHSegment?): User
}