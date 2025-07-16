package com.example.codingchallenge.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.codingchallenge.domain.model.ObxReadStatus

@Entity(
    tableName = "obx_read_status",
    indices = [androidx.room.Index(value = ["obx_id"], unique = true)]
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
        isRead = this.isRead
    )
}