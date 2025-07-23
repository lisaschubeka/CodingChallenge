package com.example.codingchallenge.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.codingchallenge.domain.model.hl7Segment.OBXSegmentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OBXSegmentDao {
    @Insert
    suspend fun insertAllObxSegments(segments: List<OBXSegmentEntity>)

    @Query("SELECT * FROM obx_segments")
    fun observeAllObxSegments(): Flow<List<OBXSegmentEntity>>
}
