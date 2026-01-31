package com.example.codingchallenge.data.model

import androidx.room.Embedded
import androidx.room.Relation
import com.example.codingchallenge.domain.model.hl7Segment.OBXSegmentEntity

data class OBXAndNTEDataEntity(
    @Embedded
    val obxSegmentEntity: OBXSegmentEntity,
    @Relation(
        parentColumn = "obxId",
        entityColumn = "obx_id"
    )
    val nteSegmentEntityList: List<NTESegmentEntity>
)