package com.example.codingchallenge.domain.model.hl7Segment

data class MSHSegment(
    // MSH.1 - Field Separator (ST) - Required in HL7, but String? here
    val fieldSeparator: String?,

    // MSH.2 - Encoding Characters (ST) - Required in HL7, but String? here
    val encodingCharacters: String?,

    // MSH.3 - Sending Application (HD) - Optional
    val sendingApplication: String?,

    // MSH.4 - Sending Facility (HD) - Optional
    val sendingFacility: String?,

    // MSH.5 - Receiving Application (HD) - Optional
    val receivingApplication: String?,

    // MSH.6 - Receiving Facility (HD) - Optional
    val receivingFacility: String?,

    // MSH.7 - Date/Time Of Message (TS) - Required in HL7, but String? here
    val dateTimeOfMessage: String?,

    // MSH.8 - Security (ST) - Optional
    val security: String?,

    // MSH.9 - Message Type (MSG) - Required in HL7, but String? here
    val messageType: String?, // HL7 typically splits this into MSH.9.1 (Code) and MSH.9.2 (Trigger Event)

    // MSH.10 - Message Control ID (ST) - Required in HL7, but String? here
    val messageControlID: String?,

    // MSH.11 - Processing ID (PT) - Required in HL7, but String? here
    val processingID: String?,

    // MSH.12 - Version ID (VID) - Required in HL7, but String? here
    val versionID: String?,

    // MSH.13 - Sequence Number (NM) - Optional
    val sequenceNumber: String?,

    // MSH.14 - Continuation Pointer (ST) - Optional
    val continuationPointer: String?,

    // MSH.15 - Accept Acknowledgment Type (ID) - Optional
    val acceptAcknowledgmentType: String?,

    // MSH.16 - Application Acknowledgment Type (ID) - Optional
    val applicationAcknowledgmentType: String?,

    // MSH.17 - Country Code (ID) - Optional
    val countryCode: String?,

    // MSH.18 - Character Set (ID) - Optional, Repeatable
    val characterSet: String?, // If repeatable, multiple character sets would be concatenated here

    // MSH.19 - Principal Language Of Message (CE) - Optional
    val principalLanguageOfMessage: String?,

    // MSH.20 - Alternate Character Set Handling Scheme (ID) - Optional
    val alternateCharacterSetHandlingScheme: String?,

    // MSH.21 - Message Profile Identifier (EI) - Optional, Repeatable
    val messageProfileIdentifier: String? // If repeatable, multiple identifiers would be concatenated here
)