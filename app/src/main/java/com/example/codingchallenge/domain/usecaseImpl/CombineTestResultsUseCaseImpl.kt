package com.example.codingchallenge.domain.usecaseImpl

import android.util.Log
import com.example.codingchallenge.domain.model.HL7Data
import com.example.codingchallenge.domain.model.ObxReadStatus
import com.example.codingchallenge.domain.model.TestResult
import com.example.codingchallenge.domain.model.User
import com.example.codingchallenge.domain.model.hl7Segment.MSHSegment
import com.example.codingchallenge.domain.model.hl7Segment.NTESegment
import com.example.codingchallenge.domain.model.hl7Segment.OBXSegment
import com.example.codingchallenge.domain.model.hl7Segment.PIDSegment
import com.example.codingchallenge.domain.usecase.CombineTestResultsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
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
        flowHL7Data: Flow<HL7Data>,
        flowReadStatus: Flow<List<ObxReadStatus>>
    ): Flow<Pair<User, List<TestResult>>> {
        return flowHL7Data.combine(flowReadStatus) { hL7Data, listReadStatus ->
            Log.d("FILE READING", " - flowcombined function INSIDE")
            val listTestResults = mutableListOf<TestResult>()
            val mapReadStatus = listReadStatus.associateBy { it.obxId }

            for (obxSegment in hL7Data.obxSegmentList) {
                val matchingReadStatus = mapReadStatus[obxSegment.setId]
                if (matchingReadStatus != null) {
                    listTestResults.add(
                        convertObxSegmentToTestResult(
                            obxSegment,
                            hL7Data.nteMap[obxSegment.setId],
                            matchingReadStatus.isRead
                        )
                    )
                }
            }
            Pair(mapToUser(hL7Data.pid, hL7Data.msh), listTestResults)
        }.onEach { Log.d("FILE READING", " - flowcombined function $it") }
    }

    override fun mapToUser(pidSegment: PIDSegment?, mshSegment: MSHSegment?): User {
        val name = pidSegment?.patientName?.split("^")?.get(1) ?: "Unknown"
        val dob = pidSegment?.dateTimeOfBirth ?: "Unknown"
        val diaryNumber = mshSegment?.receivingFacility ?: "Unknown"
        return User(name, diaryNumber, dob)

    }
}