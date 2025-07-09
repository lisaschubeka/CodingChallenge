package com.example.codingchallenge.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.codingchallenge.domain.model.hl7Segment.PIDSegmentEntity

/**
Not currently in use, MSH, NTE, OBX and PID segments are not saved to database, only the status of if OBX is read or not
 **/
@Dao
interface PIDSegmentDao {
    @Insert
    suspend fun insertPIDSegmentEntity(message: PIDSegmentEntity): Long

    @Query("SELECT * FROM pid_segments WHERE id = :id LIMIT 1")
    suspend fun getPIDEntityById(id: Long): PIDSegmentEntity?
}
