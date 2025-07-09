package com.example.codingchallenge.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.codingchallenge.domain.model.hl7Segment.OBXSegmentEntity

/**
Not currently in use, MSH, NTE, OBX and PID segments are not saved to database, only the status of if OBX is read or not
 **/
@Dao
interface OBXSegmentDao {
    @Insert
    suspend fun insertOBXSegmentEntity(message: OBXSegmentEntity): Long

    @Query("SELECT * FROM obx_segments WHERE set_id = :id LIMIT 1")
    suspend fun getOBXEntityById(id: Long): OBXSegmentEntity?
}
