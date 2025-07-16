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

    // TODO get a flow of all obx segments, and make sure there are only 10 so they match the not read flow
    // THESE WILL BE COMBINED
    @Query("SELECT * FROM obx_segments")
    fun observeAllObxSegments(): Flow<List<OBXSegmentEntity>>
}
