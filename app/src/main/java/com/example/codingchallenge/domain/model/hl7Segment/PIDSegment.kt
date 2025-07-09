package com.example.codingchallenge.domain.model.hl7Segment

data class PIDSegment(
    // PID.1 - Set ID - PID (SI)
    val setID: String?,

    // PID.2 - Patient ID (CX) - Backward Compatible
    val patientID: String?,

    // PID.3 - Patient Identifier List (CX) - Required, Repeatable
    val patientIdentifierList: String?, // Note: Will contain all CX identifiers concatenated

    // PID.4 - Alternate Patient ID - PID (CX) - Backward Compatible, Repeatable
    val alternatePatientID: String?,

    // PID.5 - Patient Name (XPN) - Required, Repeatable
    val patientName: String?, // Note: Will contain all XPN names concatenated

    // PID.6 - Mother's Maiden Name (XPN) - Optional, Repeatable
    val mothersMaidenName: String?,

    // PID.7 - Date/Time Of Birth (TS) - Optional
    val dateTimeOfBirth: String?,

    // PID.8 - Administrative Sex (IS) - Optional
    val administrativeSex: String?,

    // PID.9 - Patient Alias (XPN) - Backward Compatible, Repeatable
    val patientAlias: String?,

    // PID.10 - Race (CE) - Optional, Repeatable
    val race: String?,

    // PID.11 - Patient Address (XAD) - Optional, Repeatable
    val patientAddress: String?,

    // PID.12 - County Code (IS) - Backward Compatible
    val countyCode: String?,

    // PID.13 - Phone Number - Home (XTN) - Optional, Repeatable
    val phoneNumberHome: String?,

    // PID.14 - Phone Number - Business (XTN) - Optional, Repeatable
    val phoneNumberBusiness: String?,

    // PID.15 - Primary Language (CE) - Optional
    val primaryLanguage: String?,

    // PID.16 - Marital Status (CE) - Optional
    val maritalStatus: String?,

    // PID.17 - Religion (CE) - Optional
    val religion: String?,

    // PID.18 - Patient Account Number (CX) - Optional
    val patientAccountNumber: String?,

    // PID.19 - SSN Number - Patient (ST) - Backward Compatible
    val ssnNumberPatient: String?,

    // PID.20 - Driver's License Number - Patient (DLN) - Optional
    val driversLicenseNumberPatient: String?,

    // PID.21 - Mother's Identifier (CX) - Optional, Repeatable
    val mothersIdentifier: String?,

    // PID.22 - Ethnic Group (CE) - Optional, Repeatable
    val ethnicGroup: String?,

    // PID.23 - Birth Place (ST) - Optional
    val birthPlace: String?,

    // PID.24 - Multiple Birth Indicator (ID) - Optional
    val multipleBirthIndicator: String?,

    // PID.25 - Birth Order (NM) - Optional
    val birthOrder: String?,

    // PID.26 - Citizenship (CE) - Optional, Repeatable
    val citizenship: String?,

    // PID.27 - Veterans Military Status (CE) - Optional
    val veteransMilitaryStatus: String?,

    // PID.28 - Nationality (CE) - Backward Compatible
    val nationality: String?,

    // PID.29 - Patient Death Date and Time (TS) - Optional
    val patientDeathDateTime: String?,

    // PID.30 - Patient Death Indicator (ID) - Optional
    val patientDeathIndicator: String?,

    // PID.31 - Identity Unknown Indicator (ID) - Optional
    val identityUnknownIndicator: String?,

    // PID.32 - Identity Reliability Code (IS) - Optional, Repeatable
    val identityReliabilityCode: String?,

    // PID.33 - Last Update Date/Time (TS) - Optional
    val lastUpdateDateTime: String?,

    // PID.34 - Last Update Facility (HD) - Optional
    val lastUpdateFacility: String?,

    // PID.35 - Species Code (CE) - Conditional (for veterinary use)
    val speciesCode: String?,

    // PID.36 - Breed Code (CE) - Conditional (for veterinary use)
    val breedCode: String?,

    // PID.37 - Strain (ST) - Optional (for veterinary use)
    val strain: String?,

    // PID.38 - Production Class Code (CE) - Optional, Repeatable (for veterinary use)
    val productionClassCode: String?
)