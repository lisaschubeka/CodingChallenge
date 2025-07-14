package com.example.codingchallenge.domain.model.hl7Segment

data class NTESegment(
    // NTE.1 - Set ID - NTE (SI)
    val setId: Long,

    // NTE.2 - Source of Comment (ID)
    val sourceOfComment: String?,

    // NTE.3 - Comment (FT) - Can be repeatable in HL7
    val comment: String?, // Note: FT can be multi-line or contain embedded formatting.
    // If repeatable, multiple comments might be concatenated in the raw string.

    // NTE.4 - Comment Type (CE)
    val commentType: String?
)