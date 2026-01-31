package com.example.codingchallenge.app.di

import com.example.codingchallenge.data.repository.FileRepositoryImpl
import com.example.codingchallenge.data.repository.OBXReadStatusRepositoryImpl
import com.example.codingchallenge.domain.repository.FileRepository
import com.example.codingchallenge.domain.repository.OBXReadStatusRepository
import com.example.codingchallenge.domain.usecase.CreateSegmentUseCase
import com.example.codingchallenge.domain.usecase.ObserveFileOverviewListUseCase
import com.example.codingchallenge.domain.usecase.ObserveFileUseCase
import com.example.codingchallenge.domain.usecaseImpl.CreateSegmentUseCaseImpl
import com.example.codingchallenge.domain.usecaseImpl.ObserveFileOverviewListUseCaseImpl
import com.example.codingchallenge.domain.usecaseImpl.ObserveFileUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class hl7Module {

    @Binds
    abstract fun bindObserveFileOverviewListUseCase(
        impl: ObserveFileOverviewListUseCaseImpl
    ): ObserveFileOverviewListUseCase

    @Binds
    abstract fun bindCreateSegmentUseCase(
        impl: CreateSegmentUseCaseImpl
    ): CreateSegmentUseCase

    @Binds
    abstract fun bindObserveFileUseCase(
        impl: ObserveFileUseCaseImpl
    ): ObserveFileUseCase

    @Binds
    abstract fun bindOBXReadStatusRepository(
        impl: OBXReadStatusRepositoryImpl
    ): OBXReadStatusRepository

    @Binds
    abstract fun bindFileRepository(
        impl: FileRepositoryImpl
    ): FileRepository
}