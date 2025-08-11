package com.example.codingchallenge.domain.usecase

import com.example.codingchallenge.domain.model.TestResult
import com.example.codingchallenge.domain.model.User
import com.example.codingchallenge.domain.model.hl7Segment.MSHSegment
import com.example.codingchallenge.domain.model.hl7Segment.NTESegment
import com.example.codingchallenge.domain.model.hl7Segment.OBXSegment
import com.example.codingchallenge.domain.model.hl7Segment.PIDSegment
import kotlinx.coroutines.flow.Flow

interface ObserveFileUseCase {
    fun convertObxSegmentToTestResult(
        obxSegment: OBXSegment,
        nteList: List<NTESegment>?,
        isRead: Boolean
    ): TestResult

    fun observeChangesForHL7File(
        mshId: Long
    ): Flow<Pair<User, List<TestResult>>>

    suspend fun markObxAsRead(obxId: Long, isRead: Boolean)

    // TODO refactor to utility function used by both use cases
    fun mapToUser(pidSegment: PIDSegment?, mshSegment: MSHSegment?): User

}