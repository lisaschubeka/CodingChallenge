package com.example.codingchallenge.domain.usecase

import com.example.codingchallenge.domain.model.TestResult
import com.example.codingchallenge.domain.model.User
import kotlinx.coroutines.flow.Flow

// Top level use case used by HL7ViewModel
interface ProcessHL7DataUseCase {

    fun observeChangesForHL7File(
    ): Flow<List<Pair<User, List<TestResult>>>>

    suspend fun parseAndSaveHL7FileToDatabase(hl7Raw: String)
    suspend fun markObxAsRead(obxId: Long, isRead: Boolean)

}