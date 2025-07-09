package com.example.codingchallenge.domain.model.hl7Segment

data class OBRSegment(
    // OBR.1 - Set ID - OBR (SI)
    val setID: String?,

    // OBR.2 - Placer Order Number (EI)
    val placerOrderNumber: String?,

    // OBR.3 - Filler Order Number (EI) - Required in some contexts, Conditional here
    val fillerOrderNumber: String?,

    // OBR.4 - Universal Service Identifier (CE) - Required
    val universalServiceIdentifier: String?,

    // OBR.5 - Priority (ID) - Backward Compatibility
    val priority: String?,

    // OBR.6 - Requested Date / Time (TS) - Backward Compatibility
    val requestedDateTime: String?, // TS is typically YYYYMMDD[HHMM[SS[.uuuu]]][+/-ZZZZ]

    // OBR.7 - Observation Date / Time (TS) - Conditional
    val observationDateTime: String?,

    // OBR.8 - Observation End Date / Time (TS) - Optional
    val observationEndDateTime: String?,

    // OBR.9 - Collection Volume (CQ) - Optional
    val collectionVolume: String?,

    // OBR.10 - Collector Identifier (CN_PERSON) - Optional, Repeatable
    val collectorIdentifiers: String?,

    // OBR.11 - Specimen Action Code (ID) - Optional
    val specimenActionCode: String?,

    // OBR.12 - Danger Code (CE) - Optional
    val dangerCode: String?,

    // OBR.13 - Relevant Clinical Information (ST) - Conditional
    val relevantClinicalInformation: String?,

    // OBR.14 - Specimen Received Date / Time (TS) - Optional
    val specimenReceivedDateTime: String?,

    // OBR.15 - Specimen Source (CM_SPS) - Optional (simplified to String)
    val specimenSource: String?,

    // OBR.16 - Ordering Provider (CN_PERSON) - Optional, Repeatable
    val orderingProviders: String?,

    // OBR.17 - Order Callback Phone Number (XTN) - Optional, Repeatable (up to 2)
    val orderCallbackPhoneNumbers: String?,

    // OBR.18 - Placer Field 1 (ST) - Optional
    val placerField1: String?,

    // OBR.19 - Placer Field 2 (ST) - Optional
    val placerField2: String?,

    // OBR.20 - Filler Field 1 (ST) - Optional
    val fillerField1: String?,

    // OBR.21 - Filler Field 2 (ST) - Conditional
    val fillerField2: String?,

    // OBR.22 - Results Report / Status Change - Date / Time (TS) - Optional
    val resultsRptStatusChangeDateTime: String?,

    // OBR.23 - Charge To Practice (MOC) - Optional (simplified to String)
    val chargeToPractice: String?,

    // OBR.24 - Diagnostic Service Section Id (ID) - Conditional
    val diagnosticServiceSectionId: String?,

    // OBR.25 - Result Status (ID) - Optional
    val resultStatus: String?,

    // OBR.26 - Parent Result (PRL) - Optional (simplified to String)
    val parentResult: String?,

    // OBR.27 - Quantity / Timing (TQ) - Optional, Repeatable
    val quantityTiming: String?,

    // OBR.28 - Result Copies To (CN_PERSON) - Optional, Repeatable (up to 5)
    val resultCopiesTo: String?,

    // OBR.29 - Parent Number (EIP) - Optional (simplified to String)
    val parentNumber: String?,

    // OBR.30 - Transportation Mode (ID) - Optional
    val transportationMode: String?,

    // OBR.31 - Reason For Study (CE) - Optional, Repeatable
    val reasonForStudy: String?,

    // OBR.32 - Principal Result Interpreter (NDL) - Optional (simplified to String)
    val principalResultInterpreter: String?,

    // OBR.33 - Assistant Result Interpreter (NDL) - Optional, Repeatable
    val assistantResultInterpreters: String?,

    // OBR.34 - Technician (NDL) - Optional, Repeatable
    val technicians: String?,

    // OBR.35 - Transcriptionist (NDL) - Optional, Repeatable
    val transcriptionists: String?,

    // OBR.36 - Scheduled Date / Time (TS) - Optional
    val scheduledDateTime: String?
)