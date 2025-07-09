package com.example.codingchallenge.domain.model.hl7Segment

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
Not currently in use, MSH, NTE, OBX and PID segments are not saved to database, only the status of if OBX is read or not
 **/
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
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "obx_id", index = true)
    val hl7MessageId: Long,

    // NTE.1 - Set ID - NTE (SI)
    @ColumnInfo(name = "set_id_nte")
    val setID: String?,

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
        setID = this.setID,
        sourceOfComment = this.sourceOfComment,
        comment = this.comment,
        commentType = this.commentType
    )
}