package com.example.codingchallenge.data

import android.util.Log
import com.example.codingchallenge.domain.model.HL7Message
import com.example.codingchallenge.domain.model.hl7Segment.MSHSegment
import com.example.codingchallenge.domain.model.hl7Segment.NTESegment
import com.example.codingchallenge.domain.model.hl7Segment.OBXSegment
import com.example.codingchallenge.domain.model.hl7Segment.PIDSegment
import com.example.codingchallenge.domain.usecase.CreateSegmentUseCase
import com.example.codingchallenge.domain.usecase.ParseToHL7MsgUseCase
import javax.inject.Inject

class ParseToHL7MsgUseCaseImpl @Inject constructor(
    private val segmentCreator: CreateSegmentUseCase
) : ParseToHL7MsgUseCase {

    private val TAG = "Hl7Parser"
    private var fieldDelimiter: Char = '|'

    // TODO this should be moved somewhere else or dealt with
    private var componentDelimiter: Char = '^'

    override fun parseToHL7Msg(): HL7Message? {
        var mshSegment: MSHSegment? = null
        var pidSegment: PIDSegment? = null
        val obxList = mutableListOf<OBXSegment>()
        // Map of the OBX id to the corresponding NTE note
        val obxToNteMap = mutableMapOf<Int, MutableList<NTESegment>>()

        val segments = HL7_FILE.split('\r', '\n').filter { it.isNotBlank() }
        if (segments.isEmpty()) {
            Log.w(TAG, "No segments found in the HL7 message.")
            return null
        }

        var obxNr = 0
        for (segmentString in segments) {
            if (segmentString.isBlank()) continue

            val fields = segmentString.split(fieldDelimiter).toMutableList()

            if (fields.isEmpty()) continue

            val segmentName = fields[0]
            fields.removeAt(0)
            if (segmentName == "OBX") {
                try {
                    obxNr = fields[0].toInt()
                } catch (e: NumberFormatException) {
                    Log.w(TAG, e)
                    obxNr = -1
                }
                val obxSegment = segmentCreator.createOBXSegment(fields)
                obxList.add(obxSegment)
            }
            // if obxNr is -1, then it is unclear where the NTE segment belongs to and
            // it will be ignored
            else if (segmentName == "NTE" && obxNr != -1) {

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

        return HL7Message(mshSegment, pidSegment, obxList, obxToNteMap)
    }
}