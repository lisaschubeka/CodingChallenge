package com.example.codingchallenge.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.codingchallenge.domain.model.hl7Segment.NTESegment
import com.example.codingchallenge.domain.model.hl7Segment.OBXSegmentEntity

@Entity(
    tableName = "nte_segments",
    foreignKeys = [ForeignKey(
        entity = OBXSegmentEntity::class,
        parentColumns = ["obxId"], // new primary key
        childColumns = ["obx_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["obx_id", "set_id"], unique = true)]
)
data class NTESegmentEntity(
    @PrimaryKey(autoGenerate = true)
    val nteId: Long = 0,

    @ColumnInfo(name = "obx_id") // Foreign key to OBXSegmentEntity's obxId
    val obxId: Long,

    @ColumnInfo(name = "set_id")
    val setId: Long, // The set_id from the HL7 message (not primary key)
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
        obxId = this.obxId,
        setId = this.setId,
        sourceOfComment = this.sourceOfComment,
        comment = this.comment,
        commentType = this.commentType
    )
}

fun NTESegment.mapToEntity(): NTESegmentEntity {
    return NTESegmentEntity(
        setId = this.setId,
        obxId = this.obxId,
        sourceOfComment = this.sourceOfComment,
        comment = this.comment,
        commentType = this.commentType,
    )
}