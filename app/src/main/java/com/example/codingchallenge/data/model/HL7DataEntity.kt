package com.example.codingchallenge.data.model

import androidx.room.Embedded
import androidx.room.Relation
import com.example.codingchallenge.domain.model.HL7Data
import com.example.codingchallenge.domain.model.hl7Segment.MSHSegmentEntity
import com.example.codingchallenge.domain.model.hl7Segment.OBXSegmentEntity
import com.example.codingchallenge.domain.model.hl7Segment.PIDSegmentEntity
import com.example.codingchallenge.domain.model.hl7Segment.mapToDomain

// This entity combines all the database queries necessary to gather the
// data for each file entry in the overview screen
data class HL7DataEntity(
    @Embedded val mshSegmentEntity: MSHSegmentEntity,
    @Relation(
        parentColumn = "mshId",
        entityColumn = "msh_id"
    )
    val pidSegmentEntity: PIDSegmentEntity,
    @Relation(
        parentColumn = "mshId",
        entity = OBXSegmentEntity::class,
        entityColumn = "msh_id"
    )
    val obxSegmentEntityList: List<OBXAndNTEDataEntity>,
)

fun HL7DataEntity.mapToDomain() = HL7Data(
    msh = mshSegmentEntity.mapToDomain(),
    pid = pidSegmentEntity.mapToDomain(),
    obxSegmentList = obxSegmentEntityList.map { segment -> segment.obxSegmentEntity.mapToDomain() },
    nteSegmentList = obxSegmentEntityList.flatMap { segment ->
        segment.nteSegmentEntityList.map {
            it.mapToDomain()
        }
    },
)
