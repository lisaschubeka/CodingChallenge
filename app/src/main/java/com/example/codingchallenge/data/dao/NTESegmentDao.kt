package com.example.codingchallenge.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.codingchallenge.domain.model.hl7Segment.NTESegmentEntity

@Dao
interface NTESegmentDao {
    @Insert
    suspend fun insertNTESegmentEntity(message: NTESegmentEntity): Long

    @Insert
    suspend fun insertAllNteSegments(segments: List<NTESegmentEntity>)

    @Query("SELECT * FROM nte_segments WHERE set_id = :id LIMIT 1")
    suspend fun getNTEEntityById(id: Long): NTESegmentEntity?

    @Query("SELECT * FROM nte_segments")
    suspend fun getAllNteSegments(): List<NTESegmentEntity>
}
