package com.example.codingchallenge.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.codingchallenge.domain.model.hl7Segment.MSHSegmentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MSHSegmentDao {
    @Insert
    suspend fun insertMSHSegmentEntity(message: MSHSegmentEntity): Long

    @Query("SELECT * FROM msh_segments WHERE id = :id LIMIT 1")
    suspend fun getMSHEntityById(id: Long): MSHSegmentEntity?

    @Query("SELECT * FROM msh_segments ORDER BY id DESC LIMIT 1")
    suspend fun getMshSegment(): MSHSegmentEntity?

    @Query("DELETE FROM msh_segments")
    suspend fun deleteAll(): Int

    @Query("SELECT * FROM msh_segments ORDER BY id DESC LIMIT 1")
    fun observeMshSegment(): Flow<MSHSegmentEntity?>
}
