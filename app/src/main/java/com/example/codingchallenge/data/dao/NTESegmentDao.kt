package com.example.codingchallenge.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.codingchallenge.domain.model.hl7Segment.NTESegmentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NTESegmentDao {

    @Insert
    suspend fun insertAllNteSegments(segments: List<NTESegmentEntity>)

    @Query("SELECT * FROM nte_segments")
    fun observeAllNteSegments(): Flow<List<NTESegmentEntity>>
}
