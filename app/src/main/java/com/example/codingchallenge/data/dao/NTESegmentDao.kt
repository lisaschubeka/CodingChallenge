package com.example.codingchallenge.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.codingchallenge.data.model.NTESegmentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NTESegmentDao {

    @Insert
    suspend fun insertAllNteSegments(segments: List<NTESegmentEntity>)

    @Insert
    suspend fun insertNteSegment(segment: NTESegmentEntity): Long

    @Query("SELECT * FROM nte_segments")
    fun observeAllNteSegments(): Flow<List<NTESegmentEntity>>
}
