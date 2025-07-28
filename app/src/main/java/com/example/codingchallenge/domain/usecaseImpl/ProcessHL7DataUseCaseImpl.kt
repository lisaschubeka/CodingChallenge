package com.example.codingchallenge.domain.usecaseImpl

import android.util.Log
import com.example.codingchallenge.domain.model.TestResult
import com.example.codingchallenge.domain.model.User
import com.example.codingchallenge.domain.repository.HL7Repository
import com.example.codingchallenge.domain.repository.OBXReadStatusRepository
import com.example.codingchallenge.domain.usecase.CombineForHL7UIUseCase
import com.example.codingchallenge.domain.usecase.CreateSegmentUseCase
import com.example.codingchallenge.domain.usecase.ProcessHL7DataUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProcessHL7DataUseCaseImpl @Inject constructor(
    private val segmentCreator: CreateSegmentUseCase,
    private val combineTestResultsUseCase: CombineForHL7UIUseCase,
    private val hL7Repository: HL7Repository,
    private val obxReadStatusRepository: OBXReadStatusRepository
) : ProcessHL7DataUseCase {

    private val TAG = "Hl7Parser"
    private var fieldDelimiter: Char = '|'

    override suspend fun parseAndSaveHL7FileToDatabase(hl7Raw: String) {
        val mshSegmentString = hl7Raw.split('\r', '\n').filter { it.isNotBlank() }[0]
        if (mshSegmentString.isEmpty()) {
            return
        }
        val mshFields = mshSegmentString.split(fieldDelimiter).toMutableList()
        val mshSegment = segmentCreator.createMSHSegment(stringSegment = mshFields)

        val mshId = hL7Repository.saveMSHSegment(mshSegment)

        val pidSegmentString = hl7Raw.split('\r', '\n').filter { it.isNotBlank() }[1]
        if (pidSegmentString.isEmpty()) {
            return
        }
        val pidFields = pidSegmentString.split(fieldDelimiter).toMutableList()
        val pidSegment = segmentCreator.createPIDSegment(stringSegment = pidFields, mshId = mshId)

        hL7Repository.savePIDSegment(pidSegment)

        val segments = hl7Raw.split('\r', '\n').filter { it.isNotBlank() }
        if (segments.isEmpty()) {
            return
        }

        var obxId = -1L
        for (segmentString in segments) {
            if (segmentString.isBlank()) continue

            val fields = segmentString.split(fieldDelimiter).toMutableList()

            if (fields.isEmpty()) continue

            val segmentName = fields[0]
            fields.removeAt(0)

            if (segmentName == "OBX") {
                val obxSegment =
                    segmentCreator.createOBXSegment(stringSegment = fields, mshId = mshId)
                if (!obxSegment.referencesRange.isNullOrBlank() && obxSegment.observationValue != "!!Storno") {
                    obxSegment.referencesRange.let { Log.w(TAG, it) }
                    obxId = hL7Repository.saveOBXSegment(obxSegment = obxSegment)
                }
            }
            // if obxNr is -1, then it is unclear where the NTE segment belongs to and
            // it will be ignored
            else if (segmentName == "NTE" && obxId != -1L) {
                val nteSegment =
                    segmentCreator.createNTESegment(stringSegment = fields, obxId = obxId)
                hL7Repository.saveNTESegment(nteSegment)
                obxReadStatusRepository.addObxReadStatusAsUnread(obxId)
            }

        }

    }

    // TODO change this so combine
    // TODO HOW TO AGGREGATE OBX READ STATUSES??
    override fun observeChangesForHL7File(): Flow<List<Pair<User, List<TestResult>>>> {
        val flowObxReadStatus = obxReadStatusRepository.observeOBXReadStatusFromDatabase()
        val flowHl7Data = hL7Repository.observeHL7FileData()
        return combineTestResultsUseCase.combineForHL7UIUpdates(
            flowHl7Data,
            flowObxReadStatus,
        )
    }

    override suspend fun markObxAsRead(obxId: Long, isRead: Boolean) {
        obxReadStatusRepository.markObxAsRead(obxId, isRead)
    }


}