package com.example.codingchallenge.domain.model.hl7Segment

data class OBXSegment(
    // OBX.1 - Set ID - OBX (SI)
    val setId: Long,

    // OBX.2 - Value Type (ID)
    val valueType: String?,

    // OBX.3 - Observation Identifier (CE)
    val observationIdentifier: String?,

    // OBX.4 - Observation Sub-ID (ST)
    val observationSubID: String?,

    // OBX.5 - Observation Value (VARIES) - Can be repeatable in HL7, but represented as single String here
    val observationValue: String?, // Note: Actual HL7 can have multiple values for OBX.5,
    // and their type depends on OBX.2. This String? will
    // hold the raw, potentially pipe-separated, value.

    // OBX.6 - Units (CE)
    val units: String?,

    // OBX.7 - References Range (ST)
    val referencesRange: String?,

    // OBX.8 - Abnormal Flags (IS) - Can be repeatable in HL7
    val abnormalFlags: String?,

    // OBX.9 - Probability (NM)
    val probability: String?,

    // OBX.10 - Nature of Abnormal Test (ID) - Can be repeatable in HL7
    val natureOfAbnormalTest: String?,

    // OBX.11 - Observation Result Status (ID)
    val observationResultStatus: String?,

    // OBX.12 - Effective Date of Reference Range (TS)
    val effectiveDateOfReferenceRange: String?,

    // OBX.13 - User Defined Access Checks (ST)
    val userDefinedAccessChecks: String?,

    // OBX.14 - Date/Time of the Observation (TS)
    val dateTimeOfTheObservation: String?,

    // OBX.15 - Producer's ID (CE)
    val producersID: String?,

    // OBX.16 - Responsible Observer (XCN) - Can be repeatable in HL7
    val responsibleObserver: String?,

    // OBX.17 - Observation Method (CE) - Can be repeatable in HL7
    val observationMethod: String?,

    // OBX.18 - Equipment Instance Identifier (EI) - Can be repeatable in HL7
    val equipmentInstanceIdentifier: String?,

    // OBX.19 - Date/Time of the Analysis (TS)
    val dateTimeOfTheAnalysis: String?
)