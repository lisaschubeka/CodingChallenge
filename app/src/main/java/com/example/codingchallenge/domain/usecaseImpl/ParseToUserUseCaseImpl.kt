package com.example.codingchallenge.domain.usecaseImpl

import com.example.codingchallenge.domain.model.User
import com.example.codingchallenge.domain.model.hl7Segment.MSHSegment
import com.example.codingchallenge.domain.model.hl7Segment.PIDSegment
import com.example.codingchallenge.domain.usecase.ParseToUserUseCase
import javax.inject.Inject

class ParseToUserUseCaseImpl @Inject constructor() : ParseToUserUseCase {
    override fun parseToUser(pidSegment: PIDSegment?, mshSegment: MSHSegment?): User {
        val name = pidSegment?.patientName?.split("^")?.get(1) ?: "Unknown"
        val dob = pidSegment?.dateTimeOfBirth ?: "Unknown"
        val diaryNumber = mshSegment?.receivingFacility ?: "Unknown"
        return User(name, diaryNumber, dob)

    }
}