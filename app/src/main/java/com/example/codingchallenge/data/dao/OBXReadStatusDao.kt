package com.example.codingchallenge.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.codingchallenge.data.model.ObxReadStatusEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OBXReadStatusDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertObxReadStatus(status: ObxReadStatusEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllObxAsUnread(statuses: List<ObxReadStatusEntity>)

    @Query("SELECT * FROM obx_read_status")
    fun observeAllObxNotRead(): Flow<List<ObxReadStatusEntity>>

    @Query("DELETE FROM obx_read_status")
    suspend fun deleteAll(): Int
}