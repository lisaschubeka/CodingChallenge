package com.example.codingchallenge.domain.model.hl7Segment

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "nte_segments",
    foreignKeys = [ForeignKey(
        entity = OBXSegmentEntity::class,
        parentColumns = ["set_id"],
        childColumns = ["obx_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class NTESegmentEntity(

    @ColumnInfo(name = "obx_id")
    val obxId: Long,

    @ColumnInfo(name = "set_id")
    @PrimaryKey
    val setId: Long,

    // NTE.2 - Source of Comment (ID)
    @ColumnInfo(name = "source_of_comment")
    val sourceOfComment: String?,

    // NTE.3 - Comment (FT) - Can be repeatable in HL7, but represented as single String here.
    // Full text fields (FT) can contain rich text or multiple lines; this stores the raw content.
    @ColumnInfo(name = "comment_text") // Changed to avoid conflict if 'comment' is a keyword
    val comment: String?,

    // NTE.4 - Comment Type (CE)
    @ColumnInfo(name = "comment_type")
    val commentType: String?
)

fun NTESegmentEntity.mapToDomain(): NTESegment {
    return NTESegment(
        setId = this.setId,
        sourceOfComment = this.sourceOfComment,
        comment = this.comment,
        commentType = this.commentType
    )
}

fun NTESegment.mapToEntity(obxId: Long): NTESegmentEntity {
    return NTESegmentEntity(
        setId = this.setId,
        obxId = obxId,
        sourceOfComment = this.sourceOfComment,
        comment = this.comment,
        commentType = this.commentType,
    )
}