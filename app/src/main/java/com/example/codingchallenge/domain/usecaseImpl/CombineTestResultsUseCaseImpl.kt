package com.example.codingchallenge.domain.usecaseImpl

import com.example.codingchallenge.domain.model.HL7Data
import com.example.codingchallenge.domain.model.ObxReadStatus
import com.example.codingchallenge.domain.model.TestResult
import com.example.codingchallenge.domain.model.User
import com.example.codingchallenge.domain.model.hl7Segment.MSHSegment
import com.example.codingchallenge.domain.model.hl7Segment.NTESegment
import com.example.codingchallenge.domain.model.hl7Segment.OBXSegment
import com.example.codingchallenge.domain.model.hl7Segment.PIDSegment
import com.example.codingchallenge.domain.usecase.CombineForHL7UIUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class CombineForHL7UIUseCaseImpl @Inject constructor() : CombineForHL7UIUseCase {
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

    // TODO both inputs would be a list of lists
    override fun combineForHL7UIUpdates(
        flowHL7Data: Flow<List<HL7Data>>,
        flowReadStatus: Flow<List<ObxReadStatus>>
    ): Flow<List<Pair<User, List<TestResult>>>> {
        // based on the obxId in each HL7data, map to read status

        return flowHL7Data.combine(flowReadStatus) { hl7DataList, listReadStatusList ->
            val listOfHL7FileData: MutableList<Pair<User, List<TestResult>>> = mutableListOf()

            for (hl7Data in hl7DataList) {
                val user: User = mapToUser(hl7Data.pid, hl7Data.msh)

                val listTestResults = mutableListOf<TestResult>()
                val mapReadStatus = listReadStatusList.associateBy { it.obxId }
                for (obxSegment in hl7Data.obxSegmentList) {
                    val matchingReadStatus = mapReadStatus[obxSegment.setId]
                    if (matchingReadStatus != null) {
                        listTestResults.add(
                            convertObxSegmentToTestResult(
                                obxSegment,
                                hl7Data.nteMap[obxSegment.setId],
                                matchingReadStatus.isRead
                            )
                        )
                    }
                }
                listOfHL7FileData.add(Pair(user, listTestResults))
            }
            listOfHL7FileData
        }

//        return flowHL7Data.combine(flowReadStatus) { hL7Data, listReadStatus ->
//            val listTestResults = mutableListOf<TestResult>()
//            val mapReadStatus = listReadStatus.associateBy { it.obxId }
//
//            for (obxSegment in hL7Data.obxSegmentList) {
//                val matchingReadStatus = mapReadStatus[obxSegment.setId]
//                if (matchingReadStatus != null) {
//                    listTestResults.add(
//                        convertObxSegmentToTestResult(
//                            obxSegment,
//                            hL7Data.nteMap[obxSegment.setId],
//                            matchingReadStatus.isRead
//                        )
//                    )
//                }
//            }
//            Pair(mapToUser(hL7Data.pid, hL7Data.msh), listTestResults)
//        }
    }

    override fun mapToUser(pidSegment: PIDSegment?, mshSegment: MSHSegment?): User {
        val name = pidSegment?.patientName?.split("^")?.get(1) ?: "Unknown"
        val dob = pidSegment?.dateTimeOfBirth ?: "Unknown"
        val diaryNumber = mshSegment?.receivingFacility ?: "Unknown"
        return User(name, diaryNumber, dob)

    }
}