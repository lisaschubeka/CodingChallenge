package com.example.codingchallenge.data.usecase

import com.example.codingchallenge.domain.model.hl7Segment.MSHSegment
import com.example.codingchallenge.domain.model.hl7Segment.NTESegment
import com.example.codingchallenge.domain.model.hl7Segment.OBXSegment
import com.example.codingchallenge.domain.model.hl7Segment.PIDSegment
import com.example.codingchallenge.domain.usecase.CreateSegmentUseCase
import javax.inject.Inject

class CreateSegmentUseCaseImpl @Inject constructor() : CreateSegmentUseCase {
    override fun createMSHSegment(stringSegment: List<String>): MSHSegment {
        return MSHSegment(
            fieldSeparator = stringSegment.getOrNull(0), // MSH.1
            encodingCharacters = stringSegment.getOrNull(1), // MSH.2
            sendingApplication = stringSegment.getOrNull(2), // MSH.3
            sendingFacility = stringSegment.getOrNull(3), // MSH.4
            receivingApplication = stringSegment.getOrNull(4), // MSH.5
            receivingFacility = stringSegment.getOrNull(5), // MSH.6
            dateTimeOfMessage = stringSegment.getOrNull(6), // MSH.7
            security = stringSegment.getOrNull(7), // MSH.8
            messageType = stringSegment.getOrNull(8), // MSH.9
            messageControlID = stringSegment.getOrNull(9), // MSH.10
            processingID = stringSegment.getOrNull(10), // MSH.11
            versionID = stringSegment.getOrNull(11), // MSH.12
            sequenceNumber = stringSegment.getOrNull(12), // MSH.13
            continuationPointer = stringSegment.getOrNull(13), // MSH.14
            acceptAcknowledgmentType = stringSegment.getOrNull(14), // MSH.15
            applicationAcknowledgmentType = stringSegment.getOrNull(15), // MSH.16
            countryCode = stringSegment.getOrNull(16), // MSH.17
            characterSet = stringSegment.getOrNull(17), // MSH.18
            principalLanguageOfMessage = stringSegment.getOrNull(18), // MSH.19
            alternateCharacterSetHandlingScheme = stringSegment.getOrNull(19), // MSH.20
            messageProfileIdentifier = stringSegment.getOrNull(20) // MSH.21
        )
    }

    override fun createPIDSegment(stringSegment: List<String>): PIDSegment {
        return PIDSegment(
            setId = stringSegment[0].toLong(),                            // PID.1
            patientID = stringSegment.getOrNull(1),                 // PID.2
            patientIdentifierList = stringSegment.getOrNull(2),     // PID.3
            alternatePatientID = stringSegment.getOrNull(3),        // PID.4
            patientName = stringSegment.getOrNull(4),               // PID.5
            mothersMaidenName = stringSegment.getOrNull(5),         // PID.6
            dateTimeOfBirth = stringSegment.getOrNull(6),           // PID.7
            administrativeSex = stringSegment.getOrNull(7),         // PID.8
            patientAlias = stringSegment.getOrNull(8),              // PID.9
            race = stringSegment.getOrNull(9),                      // PID.10
            patientAddress = stringSegment.getOrNull(10),           // PID.11
            countyCode = stringSegment.getOrNull(11),               // PID.12
            phoneNumberHome = stringSegment.getOrNull(12),          // PID.13
            phoneNumberBusiness = stringSegment.getOrNull(13),      // PID.14
            primaryLanguage = stringSegment.getOrNull(14),          // PID.15
            maritalStatus = stringSegment.getOrNull(15),            // PID.16
            religion = stringSegment.getOrNull(16),                 // PID.17
            patientAccountNumber = stringSegment.getOrNull(17),     // PID.18
            ssnNumberPatient = stringSegment.getOrNull(18),         // PID.19
            driversLicenseNumberPatient = stringSegment.getOrNull(19), // PID.20
            mothersIdentifier = stringSegment.getOrNull(20),        // PID.21
            ethnicGroup = stringSegment.getOrNull(21),              // PID.22
            birthPlace = stringSegment.getOrNull(22),               // PID.23
            multipleBirthIndicator = stringSegment.getOrNull(23),   // PID.24
            birthOrder = stringSegment.getOrNull(24),               // PID.25
            citizenship = stringSegment.getOrNull(25),              // PID.26
            veteransMilitaryStatus = stringSegment.getOrNull(26),   // PID.27
            nationality = stringSegment.getOrNull(27),              // PID.28
            patientDeathDateTime = stringSegment.getOrNull(28),     // PID.29
            patientDeathIndicator = stringSegment.getOrNull(29),    // PID.30
            identityUnknownIndicator = stringSegment.getOrNull(30), // PID.31
            identityReliabilityCode = stringSegment.getOrNull(31),  // PID.32
            lastUpdateDateTime = stringSegment.getOrNull(32),       // PID.33
            lastUpdateFacility = stringSegment.getOrNull(33),       // PID.34
            speciesCode = stringSegment.getOrNull(34),              // PID.35
            breedCode = stringSegment.getOrNull(35),                // PID.36
            strain = stringSegment.getOrNull(36),                   // PID.37
            productionClassCode = stringSegment.getOrNull(37)       // PID.38
        )
    }

    override fun createOBXSegment(stringSegment: List<String>): OBXSegment {
        return OBXSegment(
            setId = stringSegment[0].toLong(),                     // OBX.1
            valueType = stringSegment.getOrNull(1),                 // OBX.2
            observationIdentifier = stringSegment.getOrNull(2),     // OBX.3
            observationSubID = stringSegment.getOrNull(3),          // OBX.4
            observationValue = stringSegment.getOrNull(4),          // OBX.5
            units = stringSegment.getOrNull(5),                     // OBX.6
            referencesRange = stringSegment.getOrNull(6),           // OBX.7
            abnormalFlags = stringSegment.getOrNull(7),             // OBX.8
            probability = stringSegment.getOrNull(8),               // OBX.9
            natureOfAbnormalTest = stringSegment.getOrNull(9),      // OBX.10
            observationResultStatus = stringSegment.getOrNull(10),  // OBX.11
            effectiveDateOfReferenceRange = stringSegment.getOrNull(11), // OBX.12
            userDefinedAccessChecks = stringSegment.getOrNull(12),  // OBX.13
            dateTimeOfTheObservation = stringSegment.getOrNull(13), // OBX.14
            producersID = stringSegment.getOrNull(14),              // OBX.15
            responsibleObserver = stringSegment.getOrNull(15),      // OBX.16
            observationMethod = stringSegment.getOrNull(16),        // OBX.17
            equipmentInstanceIdentifier = stringSegment.getOrNull(17), // OBX.18
            dateTimeOfTheAnalysis = stringSegment.getOrNull(18)     // OBX.19
        )
    }

    override fun createNTESegment(stringSegment: List<String>): NTESegment {
        return NTESegment(
            setId = stringSegment[0].toLong(),          // NTE.1
            sourceOfComment = stringSegment.getOrNull(1), // NTE.2
            comment = stringSegment.getOrNull(2),        // NTE.3
            commentType = stringSegment.getOrNull(3)     // NTE.4
        )
    }
}