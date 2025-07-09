package com.example.codingchallenge.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.codingchallenge.data.model.ObxReadStatusEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OBXReadStatusDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertObxReadStatus(status: ObxReadStatusEntity)

    @Update
    suspend fun updateObxReadStatus(status: ObxReadStatusEntity)

    @Query("SELECT * FROM obx_read_status WHERE obx_id = :obxId LIMIT 1")
    fun getObxReadStatus(obxId: Long): Flow<ObxReadStatusEntity?>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllObxAsUnread(statuses: List<ObxReadStatusEntity>)

    @Query("SELECT COUNT(*) FROM obx_read_status WHERE is_read = 0")
    fun observeAmountObxNotRead(): Flow<Int>
}