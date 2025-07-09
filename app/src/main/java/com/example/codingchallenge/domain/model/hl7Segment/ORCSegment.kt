package com.example.codingchallenge.domain.model.hl7Segment

data class ORCSegment(
    // ORC.1 - Order Control (ID) - Required in HL7, but String? here as requested
    val orderControl: String?,

    // ORC.2 - Placer Order Number (EI) - Conditional in HL7
    val placerOrderNumber: String?,

    // ORC.3 - Filler Order Number (EI) - Conditional in HL7
    val fillerOrderNumber: String?,

    // ORC.4 - Placer Group Number (EI) - Optional
    val placerGroupNumber: String?,

    // ORC.5 - Order Status (ID) - Optional
    val orderStatus: String?,

    // ORC.6 - Response Flag (ID) - Optional
    val responseFlag: String?,

    // ORC.7 - Quantity/Timing (TQ) - Repeatable in HL7
    val quantityTiming: String?,

    // ORC.8 - Parent (EIP) - Optional
    val parent: String?,

    // ORC.9 - Date/Time Of Transaction (DTM) - Optional
    val dateTimeOfTransaction: String?,

    // ORC.10 - Entered By (XCN) - Backward Compatible, Repeatable
    val enteredBy: String?,

    // ORC.11 - Verified By (XCN) - Backward Compatible, Repeatable
    val verifiedBy: String?,

    // ORC.12 - Ordering Provider (XCN) - Backward Compatible, Repeatable
    val orderingProvider: String?,

    // ORC.13 - Enterer's Location (PL) - Optional
    val enterersLocation: String?,

    // ORC.14 - Call Back Phone Number (XTN) - Optional, Repeatable (up to 2)
    val callBackPhoneNumber: String?,

    // ORC.15 - Order Effective Date/Time (DTM) - Optional
    val orderEffectiveDateTime: String?,

    // ORC.16 - Order Control Code Reason (CWE) - Optional
    val orderControlCodeReason: String?,

    // ORC.17 - Entering Organization (CWE) - Backward Compatible
    val enteringOrganization: String?,

    // ORC.18 - Entering Device (CWE) - Backward Compatible
    val enteringDevice: String?,

    // ORC.19 - Action By (XCN) - Backward Compatible, Repeatable
    val actionBy: String?,

    // ORC.20 - Advanced Beneficiary Notice Code (CWE) - Optional
    val advancedBeneficiaryNoticeCode: String?,

    // ORC.21 - Ordering Facility Name (XON) - Backward Compatible, Repeatable
    val orderingFacilityName: String?,

    // ORC.22 - Ordering Facility Address (XAD) - Backward Compatible, Repeatable
    val orderingFacilityAddress: String?,

    // ORC.23 - Ordering Facility Phone Number (XTN) - Backward Compatible, Repeatable
    val orderingFacilityPhoneNumber: String?,

    // ORC.24 - Ordering Provider Address (XAD) - Backward Compatible, Repeatable
    val orderingProviderAddress: String?,

    // ORC.25 - Order Status Modifier (CWE) - Optional
    val orderStatusModifier: String?,

    // ORC.26 - Advanced Beneficiary Notice Override Reason (CWE) - Conditional
    val advancedBeneficiaryNoticeOverrideReason: String?,

    // ORC.27 - Filler's Expected Availability Date/Time (DTM) - Optional
    val fillersExpectedAvailabilityDateTime: String?,

    // ORC.28 - Confidentiality Code (CWE) - Optional
    val confidentialityCode: String?,

    // ORC.29 - Order Type (CWE) - Optional
    val orderType: String?,

    // ORC.30 - Enterer Authorization Mode (CNE) - Optional
    val entererAuthorizationMode: String?,

    // ORC.31 - Parent Universal Service Identifier (CWE) - Backward Compatible
    val parentUniversalServiceIdentifier: String?,

    // ORC.32 - Advanced Beneficiary Notice Date (DT) - Optional
    val advancedBeneficiaryNoticeDate: String?,

    // ORC.33 - Alternate Placer Order Number (CX) - Optional, Repeatable
    val alternatePlacerOrderNumber: String?,

    // ORC.34 - Order Workflow Profile (EI) - Optional, Repeatable
    val orderWorkflowProfile: String?
)