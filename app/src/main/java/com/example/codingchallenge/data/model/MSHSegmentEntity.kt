package com.example.codingchallenge.domain.model.hl7Segment

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "msh_segments")
data class MSHSegmentEntity(

    // Useful if there are more MSHSegments from users in the future
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    // MSH.1 - Field Separator (ST) - Required in HL7, but String? here
    @ColumnInfo(name = "field_separator") // Custom column name for database
    val fieldSeparator: String?,

    // MSH.2 - Encoding Characters (ST) - Required in HL7, but String? here
    @ColumnInfo(name = "encoding_characters")
    val encodingCharacters: String?,

    // MSH.3 - Sending Application (HD) - Optional
    @ColumnInfo(name = "sending_application")
    val sendingApplication: String?,

    // MSH.4 - Sending Facility (HD) - Optional
    @ColumnInfo(name = "sending_facility")
    val sendingFacility: String?,

    // MSH.5 - Receiving Application (HD) - Optional
    @ColumnInfo(name = "receiving_application")
    val receivingApplication: String?,

    // MSH.6 - Receiving Facility (HD) - Optional
    @ColumnInfo(name = "receiving_facility")
    val receivingFacility: String?,

    // MSH.7 - Date/Time Of Message (TS) - Required in HL7, but String? here
    @ColumnInfo(name = "date_time_of_message")
    val dateTimeOfMessage: String?,

    // MSH.8 - Security (ST) - Optional
    @ColumnInfo(name = "security")
    val security: String?,

    // MSH.9 - Message Type (MSG) - Required in HL7, but String? here
    @ColumnInfo(name = "message_type")
    val messageType: String?, // HL7 typically splits this into MSH.9.1 (Code) and MSH.9.2 (Trigger Event)

    // MSH.10 - Message Control ID (ST) - Required in HL7, but String? here
    // If you want to keep MSH.10 as a unique identifier *within* the HL7 domain but rely on 'id' for DB primary key
    @ColumnInfo(name = "message_control_id")
    val messageControlID: String?,

    // MSH.11 - Processing ID (PT) - Required in HL7, but String? here
    @ColumnInfo(name = "processing_id")
    val processingID: String?,

    // MSH.12 - Version ID (VID) - Required in HL7, but String? here
    @ColumnInfo(name = "version_id")
    val versionID: String?,

    // MSH.13 - Sequence Number (NM) - Optional
    @ColumnInfo(name = "sequence_number")
    val sequenceNumber: String?,

    // MSH.14 - Continuation Pointer (ST) - Optional
    @ColumnInfo(name = "continuation_pointer")
    val continuationPointer: String?,

    // MSH.15 - Accept Acknowledgment Type (ID) - Optional
    @ColumnInfo(name = "accept_acknowledgment_type")
    val acceptAcknowledgmentType: String?,

    // MSH.16 - Application Acknowledgment Type (ID) - Optional
    @ColumnInfo(name = "application_acknowledgment_type")
    val applicationAcknowledgmentType: String?,

    // MSH.17 - Country Code (ID) - Optional
    @ColumnInfo(name = "country_code")
    val countryCode: String?,

    // MSH.18 - Character Set (ID) - Optional, Repeatable
    @ColumnInfo(name = "character_set")
    val characterSet: String?, // If repeatable, multiple character sets would be concatenated here

    // MSH.19 - Principal Language Of Message (CE) - Optional
    @ColumnInfo(name = "principal_language_of_message")
    val principalLanguageOfMessage: String?,

    // MSH.20 - Alternate Character Set Handling Scheme (ID) - Optional
    @ColumnInfo(name = "alternate_character_set_handling_scheme")
    val alternateCharacterSetHandlingScheme: String?,

    // MSH.21 - Message Profile Identifier (EI) - Optional, Repeatable
    @ColumnInfo(name = "message_profile_identifier")
    val messageProfileIdentifier: String? // If repeatable, multiple identifiers would be concatenated here
)

fun MSHSegmentEntity.mapToDomain() = MSHSegment(
    fieldSeparator = fieldSeparator,
    encodingCharacters = encodingCharacters,
    sendingApplication = sendingApplication,
    sendingFacility = sendingFacility,
    receivingApplication = receivingApplication,
    receivingFacility = receivingFacility,
    dateTimeOfMessage = dateTimeOfMessage,
    security = security,
    messageType = messageType,
    messageControlID = messageControlID,
    processingID = processingID,
    versionID = versionID,
    sequenceNumber = sequenceNumber,
    continuationPointer = continuationPointer,
    acceptAcknowledgmentType = acceptAcknowledgmentType,
    applicationAcknowledgmentType = applicationAcknowledgmentType,
    countryCode = countryCode,
    characterSet = characterSet,
    principalLanguageOfMessage = principalLanguageOfMessage,
    alternateCharacterSetHandlingScheme = alternateCharacterSetHandlingScheme,
    messageProfileIdentifier = messageProfileIdentifier
)

// TODO is this the correct place?
fun MSHSegment.mapToEntity() = MSHSegmentEntity(
    fieldSeparator = fieldSeparator,
    encodingCharacters = encodingCharacters,
    sendingApplication = sendingApplication,
    sendingFacility = sendingFacility,
    receivingApplication = receivingApplication,
    receivingFacility = receivingFacility,
    dateTimeOfMessage = dateTimeOfMessage,
    security = security,
    messageType = messageType,
    messageControlID = messageControlID,
    processingID = processingID,
    versionID = versionID,
    sequenceNumber = sequenceNumber,
    continuationPointer = continuationPointer,
    acceptAcknowledgmentType = acceptAcknowledgmentType,
    applicationAcknowledgmentType = applicationAcknowledgmentType,
    countryCode = countryCode,
    characterSet = characterSet,
    principalLanguageOfMessage = principalLanguageOfMessage,
    alternateCharacterSetHandlingScheme = alternateCharacterSetHandlingScheme,
    messageProfileIdentifier = messageProfileIdentifier
)