// app/di/DatabaseModule.kt
package com.example.codingchallenge.app.di

import android.content.Context
import androidx.room.Room
import com.example.codingchallenge.app.AppDatabase
import com.example.codingchallenge.data.dao.MSHSegmentDao
import com.example.codingchallenge.data.dao.NTESegmentDao
import com.example.codingchallenge.data.dao.OBXReadStatusDao
import com.example.codingchallenge.data.dao.OBXSegmentDao
import com.example.codingchallenge.data.dao.PIDSegmentDao

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "coding_challenge_database"
        )
            .build()
    }

    @Provides
    @Singleton
    fun provideObxReadStatusDao(database: AppDatabase): OBXReadStatusDao {
        return database.obxReadStatusDao()
    }

    @Provides
    @Singleton
    fun provideMshSegmentDao(database: AppDatabase): MSHSegmentDao {
        return database.mshSegmentDao()
    }

    @Provides
    @Singleton
    fun providePidSegmentDao(database: AppDatabase): PIDSegmentDao {
        return database.pidSegmentDao()
    }

    @Provides
    @Singleton
    fun provideObxSegmentDao(database: AppDatabase): OBXSegmentDao {
        return database.obxSegmentDao()
    }

    @Provides
    @Singleton
    fun provideNteSegmentDao(database: AppDatabase): NTESegmentDao {
        return database.nteSegmentDao()
    }
}