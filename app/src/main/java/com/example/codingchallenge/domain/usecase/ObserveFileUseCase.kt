package com.example.codingchallenge.domain.usecase

import com.example.codingchallenge.data.model.HL7DataEntity
import com.example.codingchallenge.domain.model.TestResult
import com.example.codingchallenge.domain.model.User
import com.example.codingchallenge.domain.model.hl7Segment.MSHSegment
import com.example.codingchallenge.domain.model.hl7Segment.NTESegment
import com.example.codingchallenge.domain.model.hl7Segment.OBXSegment
import com.example.codingchallenge.domain.model.hl7Segment.ObxReadStatus
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

    // TODO violation of domain data separation since HL7DataEntity is from data layer
    // TODO challenge obxId does not exist in Domain layer, HOW TO FIX
    fun combineForDetailView(
        flowHL7Data: Flow<HL7DataEntity>,
        flowReadStatus: Flow<List<ObxReadStatus>>
    ): Flow<Pair<User, List<TestResult>>>


    // TODO refactor to utility function used by both use cases
    fun mapToUser(pidSegment: PIDSegment?, mshSegment: MSHSegment?): User

}