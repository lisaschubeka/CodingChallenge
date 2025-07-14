package com.example.codingchallenge.data.usecase

import android.util.Log
import com.example.codingchallenge.data.HL7_FILE
import com.example.codingchallenge.domain.model.HL7Data
import com.example.codingchallenge.domain.model.TestResult
import com.example.codingchallenge.domain.model.User
import com.example.codingchallenge.domain.model.hl7Segment.MSHSegment
import com.example.codingchallenge.domain.model.hl7Segment.NTESegment
import com.example.codingchallenge.domain.model.hl7Segment.OBXSegment
import com.example.codingchallenge.domain.model.hl7Segment.PIDSegment
import com.example.codingchallenge.domain.repository.HL7Repository
import com.example.codingchallenge.domain.usecase.CreateSegmentUseCase
import com.example.codingchallenge.domain.usecase.ParseToTestResultsUseCase
import com.example.codingchallenge.domain.usecase.ParseToUserUseCase
import com.example.codingchallenge.domain.usecase.ProcessHL7DataUseCase
import javax.inject.Inject

class ProcessHL7DataUseCaseImpl @Inject constructor(
    private val segmentCreator: CreateSegmentUseCase,
    private val parseToUserUseCase: ParseToUserUseCase,
    private val parseToTestResultsUseCase: ParseToTestResultsUseCase,
    private val hL7Repository: HL7Repository
) : ProcessHL7DataUseCase {

    private val TAG = "Hl7Parser"
    private var fieldDelimiter: Char = '|'

    override fun parseToHL7Data(): HL7Data? {
        var mshSegment: MSHSegment? = null
        var pidSegment: PIDSegment? = null
        val obxList = mutableListOf<OBXSegment>()
        // Map of the OBX id to the corresponding NTE note
        val obxToNteMap = mutableMapOf<Long, MutableList<NTESegment>>()

        val segments = HL7_FILE.split('\r', '\n').filter { it.isNotBlank() }
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

    override suspend fun save(hl7data: HL7Data) {
        hL7Repository.saveHL7FileData(hl7data)
    }

    override suspend fun retrieve(): HL7Data {
        return hL7Repository.retrieveHL7FileData()
    }

    override fun mapToTestResult(
        obxSegments: List<OBXSegment>,
        nteMap: Map<Long, List<NTESegment>>
    ): List<TestResult> {
        return parseToTestResultsUseCase.parseToTestResults(obxSegments, nteMap)
    }

    override fun mapToUser(pidSegment: PIDSegment?, mshSegment: MSHSegment?): User {
        return parseToUserUseCase.parseToUser(pidSegment, mshSegment)
    }
}