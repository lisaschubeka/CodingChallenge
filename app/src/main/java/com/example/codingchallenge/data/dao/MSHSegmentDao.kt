package com.example.codingchallenge.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.codingchallenge.domain.model.hl7Segment.MSHSegmentEntity

/**
Not currently in use, MSH, NTE, OBX and PID segments are not saved to database, only the status of if OBX is read or not
 **/
@Dao
interface MSHSegmentDao {
    @Insert
    suspend fun insertMSHSegmentEntity(message: MSHSegmentEntity): Long

    @Query("SELECT * FROM msh_segments WHERE id = :id LIMIT 1")
    suspend fun getMSHEntityById(id: Long): MSHSegmentEntity?
}
