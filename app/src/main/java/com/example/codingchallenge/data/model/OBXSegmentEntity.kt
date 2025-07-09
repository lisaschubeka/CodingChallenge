package com.example.codingchallenge.domain.model.hl7Segment

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
Not currently in use, MSH, NTE, OBX and PID segments are not saved to database, only the status of if OBX is read or not
 **/
@Entity(tableName = "obx_segments")
data class OBXSegmentEntity(
    // OBX.1 - Set ID - OBX (SI)
    @PrimaryKey
    @ColumnInfo(name = "set_id")
    val setId: Long?,

    // OBX.2 - Value Type (ID)
    @ColumnInfo(name = "value_type")
    val valueType: String?,

    // OBX.3 - Observation Identifier (CE)
    @ColumnInfo(name = "observation_identifier")
    val observationIdentifier: String?,

    // OBX.4 - Observation Sub-ID (ST)
    @ColumnInfo(name = "observation_sub_id")
    val observationSubID: String?,

    // OBX.5 - Observation Value (VARIES)
    // Note: This will store the raw, potentially pipe-separated, value.
    // For complex types or multiple values, consider parsing to separate fields/entities
    // in your domain model and handling serialization/deserialization.
    @ColumnInfo(name = "observation_value")
    val observationValue: String?,

    // OBX.6 - Units (CE)
    @ColumnInfo(name = "units")
    val units: String?,

    // OBX.7 - References Range (ST)
    @ColumnInfo(name = "references_range")
    val referencesRange: String?,

    // OBX.8 - Abnormal Flags (IS)
    @ColumnInfo(name = "abnormal_flags")
    val abnormalFlags: String?,

    // OBX.9 - Probability (NM)
    @ColumnInfo(name = "probability")
    val probability: String?,

    // OBX.10 - Nature of Abnormal Test (ID)
    @ColumnInfo(name = "nature_of_abnormal_test")
    val natureOfAbnormalTest: String?,

    // OBX.11 - Observation Result Status (ID)
    @ColumnInfo(name = "observation_result_status")
    val observationResultStatus: String?,

    // OBX.12 - Effective Date of Reference Range (TS)
    @ColumnInfo(name = "effective_date_of_reference_range")
    val effectiveDateOfReferenceRange: String?,

    // OBX.13 - User Defined Access Checks (ST)
    @ColumnInfo(name = "user_defined_access_checks")
    val userDefinedAccessChecks: String?,

    // OBX.14 - Date/Time of the Observation (TS)
    @ColumnInfo(name = "date_time_of_the_observation")
    val dateTimeOfTheObservation: String?,

    // OBX.15 - Producer's ID (CE)
    @ColumnInfo(name = "producers_id")
    val producersID: String?,

    // OBX.16 - Responsible Observer (XCN)
    @ColumnInfo(name = "responsible_observer")
    val responsibleObserver: String?,

    // OBX.17 - Observation Method (CE)
    @ColumnInfo(name = "observation_method")
    val observationMethod: String?,

    // OBX.18 - Equipment Instance Identifier (EI)
    @ColumnInfo(name = "equipment_instance_identifier")
    val equipmentInstanceIdentifier: String?,

    // OBX.19 - Date/Time of the Analysis (TS)
    @ColumnInfo(name = "date_time_of_the_analysis")
    val dateTimeOfTheAnalysis: String?
)

fun OBXSegmentEntity.mapToDomain(): OBXSegment {
    return OBXSegment(
        setID = this.setId,
        valueType = this.valueType,
        observationIdentifier = this.observationIdentifier,
        observationSubID = this.observationSubID,
        observationValue = this.observationValue,
        units = this.units,
        referencesRange = this.referencesRange,
        abnormalFlags = this.abnormalFlags,
        probability = this.probability,
        natureOfAbnormalTest = this.natureOfAbnormalTest,
        observationResultStatus = this.observationResultStatus,
        effectiveDateOfReferenceRange = this.effectiveDateOfReferenceRange,
        userDefinedAccessChecks = this.userDefinedAccessChecks,
        dateTimeOfTheObservation = this.dateTimeOfTheObservation,
        producersID = this.producersID,
        responsibleObserver = this.responsibleObserver,
        observationMethod = this.observationMethod,
        equipmentInstanceIdentifier = this.equipmentInstanceIdentifier,
        dateTimeOfTheAnalysis = this.dateTimeOfTheAnalysis
    )
}