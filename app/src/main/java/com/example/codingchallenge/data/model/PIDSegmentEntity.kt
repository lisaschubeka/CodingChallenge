package com.example.codingchallenge.domain.model.hl7Segment

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "pid_segments",
    foreignKeys = [ForeignKey(
        entity = MSHSegmentEntity::class,
        parentColumns = ["id"],
        childColumns = ["msh_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class PIDSegmentEntity(
    @PrimaryKey
    @ColumnInfo(name = "set_id")
    val setId: Long,
    // TODO will need combine this with the primary key when saving multiple files
    // Foreign key reference
    @ColumnInfo(name = "msh_id", index = true)
    val mshId: Long,

    // PID.2 - Patient ID (external ID) (CX) - Optional
    @ColumnInfo(name = "patient_id_external")
    val patientIdExternal: String?,

    // PID.3 - Patient Identifier List (CX) - Required in HL7, commonly includes internal IDs
    @ColumnInfo(name = "patient_id_internal")
    val patientIdInternal: String?, // Often the primary internal identifier

    // PID.4 - Alternate Patient ID – PID (CX) - Optional
    @ColumnInfo(name = "alternate_patient_id")
    val alternatePatientId: String?,

    @ColumnInfo(name = "patient_name")
    val patientName: String?,

    // PID.6 - Mother's Maiden Name (XPN) - Optional
    @ColumnInfo(name = "mothers_maiden_name")
    val mothersMaidenName: String?,

    // PID.7 - Date/Time Of Birth (TS) - Required
    @ColumnInfo(name = "date_of_birth")
    val dateOfBirth: String?,

    // PID.8 - Administrative Sex (IS) - Required
    @ColumnInfo(name = "administrative_sex")
    val administrativeSex: String?,

    // PID.9 - Patient Alias (XPN) - Optional, Repeatable (concatenated if multiple)
    @ColumnInfo(name = "patient_alias")
    val patientAlias: String?,

    // PID.10 - Race (CE) - Optional, Repeatable (concatenated if multiple)
    @ColumnInfo(name = "race")
    val race: String?,

    // PID.11 - Patient Address (XAD) - Optional, Repeatable (concatenated if multiple)
    @ColumnInfo(name = "patient_address")
    val patientAddress: String?,

    // PID.12 - County Code (IS) - Optional
    @ColumnInfo(name = "county_code")
    val countyCode: String?,

    // PID.13 - Phone Number - Home (XTN) - Optional, Repeatable
    @ColumnInfo(name = "phone_number_home")
    val phoneNumberHome: String?,

    // PID.14 - Phone Number - Business (XTN) - Optional, Repeatable
    @ColumnInfo(name = "phone_number_business")
    val phoneNumberBusiness: String?,

    // PID.15 - Primary Language (CE) - Optional
    @ColumnInfo(name = "primary_language")
    val primaryLanguage: String?,

    // PID.16 - Marital Status (CE) - Optional
    @ColumnInfo(name = "marital_status")
    val maritalStatus: String?,

    // PID.17 - Religion (CE) - Optional
    @ColumnInfo(name = "religion")
    val religion: String?,

    // PID.18 - Patient Account Number (CX) - Optional
    @ColumnInfo(name = "patient_account_number")
    val patientAccountNumber: String?,

    // PID.19 - SSN (Social Security Number) – Patient (ST) - Optional
    @ColumnInfo(name = "ssn_patient")
    val ssnPatient: String?,

    // PID.20 - Driver's License Number – Patient (DLN) - Optional
    @ColumnInfo(name = "drivers_license_number_patient")
    val driversLicenseNumberPatient: String?,

    // PID.21 - Mother's Identifier (CX) - Optional, Repeatable
    @ColumnInfo(name = "mothers_identifier")
    val mothersIdentifier: String?,

    // PID.22 - Ethnic Group (CE) - Optional, Repeatable
    @ColumnInfo(name = "ethnic_group")
    val ethnicGroup: String?,

    // PID.23 - Birth Place (ST) - Optional
    @ColumnInfo(name = "birth_place")
    val birthPlace: String?,

    // PID.24 - Multiple Birth Indicator (ID) - Optional
    @ColumnInfo(name = "multiple_birth_indicator")
    val multipleBirthIndicator: String?,

    // PID.25 - Birth Order (NM) - Optional
    @ColumnInfo(name = "birth_order")
    val birthOrder: String?,

    // PID.26 - Citizenship (CE) - Optional, Repeatable
    @ColumnInfo(name = "citizenship")
    val citizenship: String?,

    // PID.27 - Veterans Military Status (CE) - Optional
    @ColumnInfo(name = "veterans_military_status")
    val veteransMilitaryStatus: String?,

    // PID.28 - Nationality (CE) - Optional
    @ColumnInfo(name = "nationality")
    val nationality: String?,

    // PID.29 - Patient Death Date And Time (TS) - Optional
    @ColumnInfo(name = "patient_death_date_time")
    val patientDeathDateTime: String?,

    // PID.30 - Patient Death Indicator (ID) - Optional
    @ColumnInfo(name = "patient_death_indicator")
    val patientDeathIndicator: String?,

    // PID.31 - Identity Unknown Indicator (ID) - Optional
    @ColumnInfo(name = "identity_unknown_indicator")
    val identityUnknownIndicator: String?,

    // PID.32 - Identity Reliability Code (CWE) - Optional, Repeatable
    @ColumnInfo(name = "identity_reliability_code")
    val identityReliabilityCode: String?,

    // PID.33 - Last Update Date/Time (TS) - Optional
    @ColumnInfo(name = "last_update_date_time")
    val lastUpdateDateTime: String?,

    // PID.34 - Last Update Facility (HD) - Optional
    @ColumnInfo(name = "last_update_facility")
    val lastUpdateFacility: String?,

    // PID.35 - Species Code (CWE) - Optional
    @ColumnInfo(name = "species_code")
    val speciesCode: String?,

    // PID.36 - Breed Code (CWE) - Optional
    @ColumnInfo(name = "breed_code")
    val breedCode: String?,

    // PID.37 - Strain (ST) - Optional
    @ColumnInfo(name = "strain")
    val strain: String?,

    // PID.38 - Production Class Code (CWE) - Optional, Repeatable
    @ColumnInfo(name = "production_class_code")
    val productionClassCode: String?,
)

fun PIDSegmentEntity.mapToDomain(): PIDSegment {

    return PIDSegment(
        setId = this.setId,
        patientID = this.patientIdExternal,
        patientIdentifierList = this.patientIdInternal,
        alternatePatientID = this.alternatePatientId,
        patientName = this.patientName,
        mothersMaidenName = this.mothersMaidenName,
        dateTimeOfBirth = this.dateOfBirth,
        administrativeSex = this.administrativeSex,
        patientAlias = this.patientAlias,
        race = this.race,
        patientAddress = this.patientAddress,
        countyCode = this.countyCode,
        phoneNumberHome = this.phoneNumberHome,
        phoneNumberBusiness = this.phoneNumberBusiness,
        primaryLanguage = this.primaryLanguage,
        maritalStatus = this.maritalStatus,
        religion = this.religion,
        patientAccountNumber = this.patientAccountNumber,
        ssnNumberPatient = this.ssnPatient,
        driversLicenseNumberPatient = this.driversLicenseNumberPatient,
        mothersIdentifier = this.mothersIdentifier,
        ethnicGroup = this.ethnicGroup,
        birthPlace = this.birthPlace,
        multipleBirthIndicator = this.multipleBirthIndicator,
        birthOrder = this.birthOrder,
        citizenship = this.citizenship,
        veteransMilitaryStatus = this.veteransMilitaryStatus,
        nationality = this.nationality,
        patientDeathDateTime = this.patientDeathDateTime,
        patientDeathIndicator = this.patientDeathIndicator,
        identityUnknownIndicator = this.identityUnknownIndicator,
        identityReliabilityCode = this.identityReliabilityCode,
        lastUpdateDateTime = this.lastUpdateDateTime,
        lastUpdateFacility = this.lastUpdateFacility,
        speciesCode = this.speciesCode,
        breedCode = this.breedCode,
        strain = this.strain,
        productionClassCode = this.productionClassCode
    )
}

fun PIDSegment.mapToEntity(mshId: Long): PIDSegmentEntity {
    return PIDSegmentEntity(
        setId = this.setId,
        mshId = mshId,
        patientIdExternal = this.patientID,
        patientIdInternal = this.patientIdentifierList,
        alternatePatientId = this.alternatePatientID,
        patientName = this.patientName,
        mothersMaidenName = this.mothersMaidenName,
        dateOfBirth = this.dateTimeOfBirth,
        administrativeSex = this.administrativeSex,
        patientAlias = this.patientAlias,
        race = this.race,
        patientAddress = this.patientAddress,
        countyCode = this.countyCode,
        phoneNumberHome = this.phoneNumberHome,
        phoneNumberBusiness = this.phoneNumberBusiness,
        primaryLanguage = this.primaryLanguage,
        maritalStatus = this.maritalStatus,
        religion = this.religion,
        patientAccountNumber = this.patientAccountNumber,
        ssnPatient = this.ssnNumberPatient,
        driversLicenseNumberPatient = this.driversLicenseNumberPatient,
        mothersIdentifier = this.mothersIdentifier,
        ethnicGroup = this.ethnicGroup,
        birthPlace = this.birthPlace,
        multipleBirthIndicator = this.multipleBirthIndicator,
        birthOrder = this.birthOrder,
        citizenship = this.citizenship,
        veteransMilitaryStatus = this.veteransMilitaryStatus,
        nationality = this.nationality,
        patientDeathDateTime = this.patientDeathDateTime,
        patientDeathIndicator = this.patientDeathIndicator,
        identityUnknownIndicator = this.identityUnknownIndicator,
        identityReliabilityCode = this.identityReliabilityCode,
        lastUpdateDateTime = this.lastUpdateDateTime,
        lastUpdateFacility = this.lastUpdateFacility,
        speciesCode = this.speciesCode,
        breedCode = this.breedCode,
        strain = this.strain,
        productionClassCode = this.productionClassCode
    )
}