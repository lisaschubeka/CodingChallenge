package com.example.codingchallenge.domain.usecaseImpl

import android.util.Log
import com.example.codingchallenge.domain.model.HL7Data
import com.example.codingchallenge.domain.model.TestResult
import com.example.codingchallenge.domain.model.User
import com.example.codingchallenge.domain.model.hl7Segment.MSHSegment
import com.example.codingchallenge.domain.model.hl7Segment.NTESegment
import com.example.codingchallenge.domain.model.hl7Segment.OBXSegment
import com.example.codingchallenge.domain.model.hl7Segment.PIDSegment
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

    override fun parseToHL7DataObject(hl7Raw: String): HL7Data? {
        var mshSegment: MSHSegment? = null
        var pidSegment: PIDSegment? = null
        val obxList = mutableListOf<OBXSegment>()
        // Map of the OBX id to the corresponding NTE note
        val obxToNteMap = mutableMapOf<Long, MutableList<NTESegment>>()

        val segments = hl7Raw.split('\r', '\n').filter { it.isNotBlank() }
        if (segments.isEmpty()) {
            Log.w(TAG, "No segments found in the HL7 message.")
            return null
        }

        var obxNr = 0L
        for (segmentString in segments) {
            if (segmentString.isBlank()) continue

            val fields = segmentString.split(fieldDelimiter).toMutableList()

            if (fields.isEmpty()) continue

            val segmentName = fields[0]
            fields.removeAt(0)
            if (segmentName == "OBX") {
                try {
                    obxNr = fields[0].toLong()
                } catch (e: NumberFormatException) {
                    Log.w(TAG, e)
                    obxNr = -1
                }
                val obxSegment = segmentCreator.createOBXSegment(fields)
                if (!obxSegment.referencesRange.isNullOrBlank() && obxSegment.observationValue != "!!Storno") {
                    obxSegment.referencesRange.let { Log.w(TAG, it) }
                    obxList.add(obxSegment)
                } else {
                    obxNr = -1
                }
            }
            // if obxNr is -1, then it is unclear where the NTE segment belongs to and
            // it will be ignored
            else if (segmentName == "NTE" && obxNr != -1L) {

                val nteSegment = segmentCreator.createNTESegment(fields)

                obxToNteMap.getOrPut(obxNr) { mutableListOf() }.add(nteSegment)

            } else if (segmentName == "MSH") {
                val segment = segmentCreator.createMSHSegment(fields)
                mshSegment = segment
            } else if (segmentName == "PID") {
                val segment = segmentCreator.createPIDSegment(fields)
                pidSegment = segment
            }

        }
        if (mshSegment == null || pidSegment == null) {
            return null
        }
        val hl7Data = HL7Data(mshSegment, pidSegment, obxList, obxToNteMap)
        return hl7Data
    }

    override suspend fun saveHL7DataToDatabase(hl7data: HL7Data) {
        hL7Repository.saveHL7FileData(hl7data)
    }

    override suspend fun clearDatabaseData() {
        hL7Repository.clearDatabase()
        obxReadStatusRepository.clearDatabase()
    }

    override suspend fun loadFromFileAndSaveAndLoadFromDatabase(hl7Raw: String) {
        try {
            clearDatabaseData()
            val hl7parsed = parseToHL7DataObject(hl7Raw)

            if (hl7parsed != null) {
                saveHL7DataToDatabase(hl7parsed)
                obxReadStatusRepository.addObxIdsAsUnread(hl7parsed.obxSegmentList.map { it.setId })
            }

        } catch (e: Exception) {
            Log.w("FILE READING", "EXCEPTION: ${e.message}")
        }
    }

    override fun observeChangesForHL7File(): Flow<Pair<User, List<TestResult>>> {
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