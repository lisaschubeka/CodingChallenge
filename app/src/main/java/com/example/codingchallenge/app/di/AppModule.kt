package com.example.codingchallenge.app.di

import com.example.codingchallenge.data.repository.HL7RepositoryImpl
import com.example.codingchallenge.data.repository.OBXReadStatusRepositoryImpl
import com.example.codingchallenge.domain.repository.HL7Repository
import com.example.codingchallenge.domain.repository.OBXReadStatusRepository
import com.example.codingchallenge.domain.usecase.CombineForHL7UIUseCase
import com.example.codingchallenge.domain.usecase.CreateSegmentUseCase
import com.example.codingchallenge.domain.usecase.ProcessHL7DataUseCase
import com.example.codingchallenge.domain.usecaseImpl.CombineTestResultsUseCaseImpl
import com.example.codingchallenge.domain.usecaseImpl.CreateSegmentUseCaseImpl
import com.example.codingchallenge.domain.usecaseImpl.ProcessHL7DataUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class hl7Module {

    @Binds
    abstract fun bindParseToHLZMsgUseCase(
        impl: ProcessHL7DataUseCaseImpl
    ): ProcessHL7DataUseCase

    @Binds
    abstract fun bindCreateSegmentUseCase(
        impl: CreateSegmentUseCaseImpl
    ): CreateSegmentUseCase

    @Binds
    abstract fun bindParseToTestResultsUseCase(
        impl: CombineTestResultsUseCaseImpl
    ): CombineForHL7UIUseCase

    @Binds
    abstract fun bindOBXReadStatusRepository(
        impl: OBXReadStatusRepositoryImpl
    ): OBXReadStatusRepository

    @Binds
    abstract fun bindHL7Repository(
        impl: HL7RepositoryImpl
    ): HL7Repository
}