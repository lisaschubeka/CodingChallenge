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

    @Insert
    suspend fun insertOBXSegmentEntity(message: OBXSegmentEntity): Long

    @Query("SELECT * FROM obx_segments WHERE set_id = :id LIMIT 1")
    suspend fun getOBXEntityById(id: Long): OBXSegmentEntity?


    @Query("SELECT * FROM obx_segments")
    suspend fun getAllObxSegments(): List<OBXSegmentEntity>

    @Query("SELECT * FROM obx_segments")
    fun observeAllObxSegments(): Flow<List<OBXSegmentEntity>>
}
