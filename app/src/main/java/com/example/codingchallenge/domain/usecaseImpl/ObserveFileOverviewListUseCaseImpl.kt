package com.example.codingchallenge.domain.usecaseImpl

import android.util.Log
import com.example.codingchallenge.domain.model.OverviewFileData
import com.example.codingchallenge.domain.repository.FileRepository
import com.example.codingchallenge.domain.repository.OBXReadStatusRepository
import com.example.codingchallenge.domain.usecase.CreateSegmentUseCase
import com.example.codingchallenge.domain.usecase.ObserveFileOverviewListUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class ObserveFileOverviewListUseCaseImpl @Inject constructor(
    private val segmentCreator: CreateSegmentUseCase,
    private val fileRepository: FileRepository,
    private val obxReadStatusRepository: OBXReadStatusRepository
) : ObserveFileOverviewListUseCase {

    private val TAG = "Hl7Parser"
    private var fieldDelimiter: Char = '|'

    override suspend fun parseAndSaveHL7FileToDatabase(hl7Raw: String) {
        Log.w("FILE READING", "1. just entered parseandsave")

        val mshSegmentString = hl7Raw.split('\r', '\n').filter { it.isNotBlank() }[0]
        if (mshSegmentString.isEmpty()) {
            return
        }
        val mshFields = mshSegmentString.split(fieldDelimiter).toMutableList()
        val mshSegment = segmentCreator.createMSHSegment(stringSegment = mshFields)
        var mshId = 1L
        try {
            mshId = fileRepository.saveMSHSegment(mshSegment)

        } catch (e: Exception) {
            mshId = 1
            Log.w("FILE READING", e.toString())
        }

        val pidSegmentString = hl7Raw.split('\r', '\n').filter { it.isNotBlank() }[1]
        if (pidSegmentString.isEmpty()) {
            return
        }
        Log.w("FILE READING", "2. after pid")

        val pidFields =
            pidSegmentString.split(fieldDelimiter).toMutableList()
        try {
            val pidSegment =
                segmentCreator.createPIDSegment(stringSegment = pidFields, mshId = mshId)

        } catch (e: Exception) {
            Log.w("FILE READING", e.toString())
        }
        val pidSegment = segmentCreator.createPIDSegment(stringSegment = pidFields, mshId = mshId)

        fileRepository.savePIDSegment(pidSegment)

        val segments = hl7Raw.split('\r', '\n').filter { it.isNotBlank() }
        if (segments.isEmpty()) {
            return
        }
        Log.w("FILE READING", "3. after pid")


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
                if (!obxSegment.referencesRange.isNullOrBlank()
                    && obxSegment.observationValue != "!!Storno"
                    && obxSegment.observationValue != null &&
                    obxSegment.observationValue.toFloatOrNull() != null
                ) {
                    obxSegment.referencesRange.let { Log.w(TAG, it) }
                    obxId = fileRepository.saveOBXSegment(obxSegment = obxSegment)
                    obxReadStatusRepository.addObxReadStatusAsUnread(obxId)
                } else {
                    obxId = -1L
                }
            }
            // if obxNr is -1, then it is unclear where the NTE segment belongs to and
            // it will be ignored
            else if (segmentName == "NTE" && obxId != -1L) {
                val nteSegment =
                    segmentCreator.createNTESegment(stringSegment = fields, obxId = obxId)
                fileRepository.saveNTESegment(nteSegment)
            }


        }

    }

    override fun observeChangesForOverview(): Flow<List<OverviewFileData>> {
        return fileRepository.observeOverviewFileData().transform { segmentList ->
            val transformedList = segmentList.map { segment ->
                var name = segment.pidSegmentEntity.patientName ?: "Unknown"
                if (name.contains("^")) {
                    name = segment.pidSegmentEntity.patientName?.split("^")?.get(1) ?: "Unknown"
                }
                val diaryNumber = segment.mshSegmentEntity.dateTimeOfMessage ?: "Unknown"
                val obxSegmentList = segment.obxSegmentEntityList.size
                OverviewFileData(
                    segment.mshSegmentEntity.mshId,
                    name,
                    diaryNumber,
                    obxSegmentList
                )
            }
            emit(transformedList)
        }
    }
}