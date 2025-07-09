package com.example.codingchallenge.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.codingchallenge.domain.model.hl7Segment.NTESegmentEntity

/**
Not currently in use, MSH, NTE, OBX and PID segments are not saved to database, only the status of if OBX is read or not
 **/
@Dao
interface NTESegmentDao {
    @Insert
    suspend fun insertNTESegmentEntity(message: NTESegmentEntity): Long

    @Query("SELECT * FROM nte_segments WHERE id = :id LIMIT 1")
    suspend fun getNTEEntityById(id: Long): NTESegmentEntity?
}
