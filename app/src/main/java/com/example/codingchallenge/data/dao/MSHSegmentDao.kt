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

    @Query("DELETE FROM msh_segments")
    suspend fun deleteAll(): Int

    @Query("SELECT * FROM msh_segments")
    fun observeAllMshSegments(): Flow<List<MSHSegmentEntity>>
}
