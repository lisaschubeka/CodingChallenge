package com.example.codingchallenge.app

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.codingchallenge.data.dao.MSHSegmentDao
import com.example.codingchallenge.data.dao.NTESegmentDao
import com.example.codingchallenge.data.dao.OBXReadStatusDao
import com.example.codingchallenge.data.dao.OBXSegmentDao
import com.example.codingchallenge.data.dao.PIDSegmentDao
import com.example.codingchallenge.data.model.ObxReadStatusEntity
import com.example.codingchallenge.domain.model.hl7Segment.MSHSegmentEntity
import com.example.codingchallenge.domain.model.hl7Segment.NTESegmentEntity
import com.example.codingchallenge.domain.model.hl7Segment.OBXSegmentEntity
import com.example.codingchallenge.domain.model.hl7Segment.PIDSegmentEntity

@Database(
    entities = [
        MSHSegmentEntity::class,
        PIDSegmentEntity::class,
        OBXSegmentEntity::class,
        NTESegmentEntity::class,
        ObxReadStatusEntity::class
    ],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun mshSegmentDao(): MSHSegmentDao
    abstract fun pidSegmentDao(): PIDSegmentDao
    abstract fun obxSegmentDao(): OBXSegmentDao
    abstract fun nteSegmentDao(): NTESegmentDao
    abstract fun obxReadStatusDao(): OBXReadStatusDao
}