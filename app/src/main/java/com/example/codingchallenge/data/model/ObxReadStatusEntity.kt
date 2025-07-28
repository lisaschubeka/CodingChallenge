package com.example.codingchallenge.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.codingchallenge.domain.model.ObxReadStatus
import com.example.codingchallenge.domain.model.hl7Segment.OBXSegmentEntity

@Entity(
    tableName = "obx_read_status",
    foreignKeys = [ForeignKey(
        entity = OBXSegmentEntity::class,
        parentColumns = ["obxId"],
        childColumns = ["obx_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["obx_id"], unique = true)]
)
data class ObxReadStatusEntity(
    @PrimaryKey
    @ColumnInfo(name = "obx_id")
    val obxId: Long,

    @ColumnInfo(name = "is_read")
    val isRead: Boolean
)

fun ObxReadStatusEntity.mapToDomain(): ObxReadStatus {
    return ObxReadStatus(
        obxId = this.obxId,
        isRead = this.isRead
    )
}

fun ObxReadStatus.mapToEntity(): ObxReadStatusEntity {
    return ObxReadStatusEntity(
        obxId = this.obxId,
        isRead = this.isRead,
    )
}