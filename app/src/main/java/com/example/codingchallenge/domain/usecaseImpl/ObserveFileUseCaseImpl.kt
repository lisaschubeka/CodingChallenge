package com.example.codingchallenge.domain.usecaseImpl

import com.example.codingchallenge.data.model.HL7DataEntity
import com.example.codingchallenge.data.model.mapToDomain
import com.example.codingchallenge.domain.model.TestResult
import com.example.codingchallenge.domain.model.User
import com.example.codingchallenge.domain.model.hl7Segment.MSHSegment
import com.example.codingchallenge.domain.model.hl7Segment.NTESegment
import com.example.codingchallenge.domain.model.hl7Segment.OBXSegment
import com.example.codingchallenge.domain.model.hl7Segment.ObxReadStatus
import com.example.codingchallenge.domain.model.hl7Segment.PIDSegment
import com.example.codingchallenge.domain.model.hl7Segment.mapToDomain
import com.example.codingchallenge.domain.repository.FileRepository
import com.example.codingchallenge.domain.repository.OBXReadStatusRepository
import com.example.codingchallenge.domain.usecase.ObserveFileUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class ObserveFileUseCaseImpl @Inject constructor(
    private val obxReadStatusRepository: OBXReadStatusRepository,
    private val fileRepository: FileRepository
) : ObserveFileUseCase {
    override fun convertObxSegmentToTestResult(
        obxSegment: OBXSegment, nteList: List<NTESegment>?, isRead: Boolean
    ): TestResult {

        val id = obxSegment.obxId ?: 0L
        val testName = obxSegment.observationIdentifier?.split("^")?.get(1) ?: ""
        val value = obxSegment.observationValue ?: ""
        val unit = obxSegment.units ?: ""
        val range = obxSegment.referencesRange ?: ""

        val note = nteList?.map({ note -> note.comment })?.joinToString(separator = " ")
        return TestResult(id, testName, value, unit, range, note, isRead)
    }

    // TODO mshId needs to be stored somewhere in the viewmodel after saving the document
    override fun observeChangesForHL7File(mshId: Long): Flow<Pair<User, List<TestResult>>> {
        val flowObxReadStatus = obxReadStatusRepository.observeOBXReadStatusFromDatabase()
        val flowHl7Data = fileRepository.observeHL7FileData(mshId = mshId)
        return combineForDetailView(
            flowHl7Data,
            flowObxReadStatus,
        )
    }

    override fun combineForDetailView(
        flowHL7Data: Flow<HL7DataEntity>, flowReadStatus: Flow<List<ObxReadStatus>>
    ): Flow<Pair<User, List<TestResult>>> {

        return flowHL7Data.combine(flowReadStatus) { hl7Data, listReadStatus ->

            // TODO refactor code since OverviewUseCase also uses code that maps to user -> utility function?
            val user: User = mapToUser(
                hl7Data.pidSegmentEntity.mapToDomain(), hl7Data.mshSegmentEntity.mapToDomain()
            )

            val listTestResults = mutableListOf<TestResult>()
            val mapReadStatus = listReadStatus.associateBy { it.obxId }
            for (obxAndNTEDataEntity in hl7Data.obxSegmentEntityList) {
                val matchingReadStatus = mapReadStatus[obxAndNTEDataEntity.obxSegmentEntity.obxId]
                if (matchingReadStatus != null) {
                    listTestResults.add(
                        convertObxSegmentToTestResult(
                            obxAndNTEDataEntity.obxSegmentEntity.mapToDomain(),
                            obxAndNTEDataEntity.nteSegmentEntityList.map { nteSegmentEntity -> nteSegmentEntity.mapToDomain() },
                            matchingReadStatus.isRead
                        )
                    )
                }
            }
            Pair(user, listTestResults)

        }
    }

    override fun mapToUser(pidSegment: PIDSegment?, mshSegment: MSHSegment?): User {
        val name = pidSegment?.patientName?.split("^")?.get(1) ?: "Unknown"
        val dob = pidSegment?.dateTimeOfBirth ?: "Unknown"
        val diaryNumber = mshSegment?.dateTimeOfMessage ?: "Unknown"
        return User(name, diaryNumber, dob)

    }

    override suspend fun markObxAsRead(obxId: Long, isRead: Boolean) {
        obxReadStatusRepository.markObxAsRead(obxId, isRead)
    }
}