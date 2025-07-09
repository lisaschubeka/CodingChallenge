package com.example.codingchallenge.domain.model.hl7Segment

data class PV1Segment(
    // PV1.1 - Set ID - PV1 (SI)
    val setID: String?,

    // PV1.2 - Patient Class (IS) - Required in HL7, but String? here
    val patientClass: String?,

    // PV1.3 - Assigned Patient Location (PL) - Optional
    val assignedPatientLocation: String?,

    // PV1.4 - Admission Type (IS) - Optional
    val admissionType: String?,

    // PV1.5 - Preadmit Number (CX) - Optional
    val preadmitNumber: String?,

    // PV1.6 - Prior Patient Location (PL) - Optional
    val priorPatientLocation: String?,

    // PV1.7 - Attending Doctor (XCN) - Optional, Repeatable
    val attendingDoctor: String?,

    // PV1.8 - Referring Doctor (XCN) - Optional, Repeatable
    val referringDoctor: String?,

    // PV1.9 - Consulting Doctor (XCN) - Backward Compatible, Repeatable
    val consultingDoctor: String?,

    // PV1.10 - Hospital Service (IS) - Optional
    val hospitalService: String?,

    // PV1.11 - Temporary Location (PL) - Optional
    val temporaryLocation: String?,

    // PV1.12 - Preadmit Test Indicator (IS) - Optional
    val preadmitTestIndicator: String?,

    // PV1.13 - Re-admission Indicator (IS) - Optional
    val reAdmissionIndicator: String?,

    // PV1.14 - Admit Source (IS) - Optional
    val admitSource: String?,

    // PV1.15 - Ambulatory Status (IS) - Optional, Repeatable
    val ambulatoryStatus: String?,

    // PV1.16 - VIP Indicator (IS) - Optional
    val vipIndicator: String?,

    // PV1.17 - Admitting Doctor (XCN) - Optional, Repeatable
    val admittingDoctor: String?,

    // PV1.18 - Patient Type (IS) - Optional
    val patientType: String?,

    // PV1.19 - Visit Number (CX) - Optional
    val visitNumber: String?,

    // PV1.20 - Financial Class (FC) - Optional, Repeatable
    val financialClass: String?,

    // PV1.21 - Charge Price Indicator (IS) - Optional
    val chargePriceIndicator: String?,

    // PV1.22 - Courtesy Code (IS) - Optional
    val courtesyCode: String?,

    // PV1.23 - Credit Rating (IS) - Optional
    val creditRating: String?,

    // PV1.24 - Contract Code (IS) - Optional, Repeatable
    val contractCode: String?,

    // PV1.25 - Contract Effective Date (DT) - Optional, Repeatable
    val contractEffectiveDate: String?,

    // PV1.26 - Contract Amount (NM) - Optional, Repeatable
    val contractAmount: String?,

    // PV1.27 - Contract Period (NM) - Optional, Repeatable
    val contractPeriod: String?,

    // PV1.28 - Interest Code (IS) - Optional
    val interestCode: String?,

    // PV1.29 - Transfer to Bad Debt Code (IS) - Optional
    val transferToBadDebtCode: String?,

    // PV1.30 - Transfer to Bad Debt Date (DT) - Optional
    val transferToBadDebtDate: String?,

    // PV1.31 - Bad Debt Agency Code (IS) - Optional
    val badDebtAgencyCode: String?,

    // PV1.32 - Bad Debt Transfer Amount (NM) - Optional
    val badDebtTransferAmount: String?,

    // PV1.33 - Bad Debt Recovery Amount (NM) - Optional
    val badDebtRecoveryAmount: String?,

    // PV1.34 - Delete Account Indicator (IS) - Optional
    val deleteAccountIndicator: String?,

    // PV1.35 - Delete Account Date (DT) - Optional
    val deleteAccountDate: String?,

    // PV1.36 - Discharge Disposition (IS) - Optional
    val dischargeDisposition: String?,

    // PV1.37 - Discharged to Location (DLD) - Optional
    val dischargedToLocation: String?,

    // PV1.38 - Diet Type (CE) - Optional
    val dietType: String?,

    // PV1.39 - Servicing Facility (IS) - Optional
    val servicingFacility: String?,

    // PV1.40 - Bed Status (IS) - Backward Compatible
    val bedStatus: String?,

    // PV1.41 - Account Status (IS) - Optional
    val accountStatus: String?,

    // PV1.42 - Pending Location (PL) - Optional
    val pendingLocation: String?,

    // PV1.43 - Prior Temporary Location (PL) - Optional
    val priorTemporaryLocation: String?,

    // PV1.44 - Admit Date/Time (TS) - Optional
    val admitDateTime: String?,

    // PV1.45 - Discharge Date/Time (TS) - Optional, Repeatable
    val dischargeDateTime: String?,

    // PV1.46 - Current Patient Balance (NM) - Optional
    val currentPatientBalance: String?,

    // PV1.47 - Total Charges (NM) - Optional
    val totalCharges: String?,

    // PV1.48 - Total Adjustments (NM) - Optional
    val totalAdjustments: String?,

    // PV1.49 - Total Payments (NM) - Optional
    val totalPayments: String?,

    // PV1.50 - Alternate Visit ID (CX) - Optional
    val alternateVisitID: String?,

    // PV1.51 - Visit Indicator (IS) - Optional
    val visitIndicator: String?,

    // PV1.52 - Other Healthcare Provider (XCN) - Backward Compatible, Repeatable
    val otherHealthcareProvider: String?
)