package com.example.codingchallenge.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.codingchallenge.domain.model.hl7Segment.PIDSegmentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PIDSegmentDao {
    @Insert
    suspend fun insertPIDSegmentEntity(message: PIDSegmentEntity): Long

    @Query("SELECT * FROM pid_segments ORDER BY set_id DESC LIMIT 1")
    fun observePidSegment(): Flow<PIDSegmentEntity?>
}
