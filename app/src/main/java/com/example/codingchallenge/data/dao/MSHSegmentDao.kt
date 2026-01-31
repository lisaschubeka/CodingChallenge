package com.example.codingchallenge.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.codingchallenge.data.model.HL7DataEntity
import com.example.codingchallenge.domain.model.hl7Segment.MSHSegmentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MSHSegmentDao {
    @Insert
    suspend fun insertMSHSegmentEntity(message: MSHSegmentEntity): Long

    @Query("DELETE FROM msh_segments")
    suspend fun deleteAll(): Int

    @Query("SELECT * FROM msh_segments WHERE mshId = :mshId LIMIT 1")
    fun observeMshSegment(mshId: Long): Flow<MSHSegmentEntity>

    @Transaction
    @Query("SELECT * FROM msh_segments")
    fun getHL7DataEntityList(): Flow<List<HL7DataEntity>>

    @Transaction
    @Query("SELECT * FROM msh_segments WHERE mshId = :mshId LIMIT 1")
    fun getHL7DataEntity(mshId: Long): Flow<HL7DataEntity>
}
