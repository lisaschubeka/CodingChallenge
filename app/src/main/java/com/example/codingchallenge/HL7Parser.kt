package com.example.codingchallenge

import android.util.Log

class HL7Parser {

    private val TAG = "Hl7Parser"

    private var fieldDelimiter: Char = '|'
    private var componentDelimiter: Char = '^'
    private var repetitionDelimiter: Char = '~'
    private var escapeCharacter: Char = '\\'
    private var subComponentDelimiter: Char = '&'

    fun parseHL7Data(hl7Message: String): Map<String, List<List<List<String>>>> {
        val parsedData = mutableMapOf<String, MutableList<MutableList<MutableList<String>>>>()

        val segments = hl7Message.split('\r', '\n').filter { it.isNotBlank() }
        if (segments.isEmpty()) {
            Log.w(TAG, "No segments found in the HL7 message.")
            return parsedData
        }

        val mshSegment = segments.find { it.startsWith("MSH") }
        if (mshSegment != null) {
            parseMshSegmentForDelimiters(mshSegment)
        } else {
            Log.w(TAG, "MSH segment not found. Using default delimiters.")
        }
        var prevSegmentId = ""
        for (segmentString in segments) {
            if (segmentString.isBlank()) continue

            // Split segment into fields using the determined field delimiter
            val fields = segmentString.split(fieldDelimiter)

            if (fields.isNotEmpty()) {
                val segmentName = fields[0]

                // Deliberate ignored the other delimiters due to time constraints
                val processedFields: MutableList<MutableList<String>> = fields.map { field ->
                    field.split(componentDelimiter).toMutableList()
                }.toMutableList()

                if (segmentName == "NTE") {
                    processedFields.add(0, mutableListOf(prevSegmentId))
                } else {
                    if (fields.size > 1) {
                        prevSegmentId = fields[1]
                    }
                }

                if (!hasInvalidTestValue(processedFields)) {
                    parsedData.computeIfAbsent(segmentName) { mutableListOf() }.add(processedFields)
                }

            }
        }
        Log.i(TAG, parsedData.toString())
        return parsedData
    }

    private fun hasInvalidTestValue(obxValues: MutableList<MutableList<String>>): Boolean {
        for (list in obxValues) {
            for (parsedValue in list) {
                if (parsedValue.contains("!!Storno")) {
                    return true
                }
            }
        }
        return false
    }

    // The delimiters can apparently be different, double check online
    private fun parseMshSegmentForDelimiters(mshSegment: String) {
        val fields = mshSegment.split('|')
        if (fields.size >= 2) {
            fieldDelimiter = fields[1].singleOrNull() ?: '|'
            if (fields.size >= 3) {
                val encodingChars = fields[1]
                if (encodingChars.length >= 1) componentDelimiter = encodingChars[0]
                if (encodingChars.length >= 2) repetitionDelimiter = encodingChars[1]
                if (encodingChars.length >= 3) escapeCharacter = encodingChars[2]
                if (encodingChars.length >= 4) subComponentDelimiter = encodingChars[3]
            }
            Log.d(
                TAG,
                "Delimiters updated: Field='$fieldDelimiter', Component='$componentDelimiter', Repetition='$repetitionDelimiter', Escape='$escapeCharacter', SubComponent='$subComponentDelimiter'"
            )
        }
    }
}