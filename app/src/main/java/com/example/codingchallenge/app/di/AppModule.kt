package com.example.codingchallenge.app.di

import com.example.codingchallenge.data.CreateSegmentUseCaseImpl
import com.example.codingchallenge.data.ParseToHL7MsgUseCaseImpl
import com.example.codingchallenge.data.ParseToTestResultsUseCaseImpl
import com.example.codingchallenge.data.ParseToUserUseCaseImpl
import com.example.codingchallenge.domain.usecase.CreateSegmentUseCase
import com.example.codingchallenge.domain.usecase.ParseToHL7MsgUseCase
import com.example.codingchallenge.domain.usecase.ParseToTestResultsUseCase
import com.example.codingchallenge.domain.usecase.ParseToUserUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class hl7Module {

    @Binds
    abstract fun bindParseToHLZMsgUseCase(
        impl: ParseToHL7MsgUseCaseImpl
    ): ParseToHL7MsgUseCase

    @Binds
    abstract fun bindCreateSegmentUseCase(
        impl: CreateSegmentUseCaseImpl
    ): CreateSegmentUseCase

    @Binds
    abstract fun bindParseToUserUseCase(
        impl: ParseToUserUseCaseImpl
    ): ParseToUserUseCase

    @Binds
    abstract fun bindParseToTestResultsUseCase(
        impl: ParseToTestResultsUseCaseImpl
    ): ParseToTestResultsUseCase
}