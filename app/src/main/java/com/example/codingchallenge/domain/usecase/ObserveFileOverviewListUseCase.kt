package com.example.codingchallenge.domain.usecase

import com.example.codingchallenge.domain.model.OverviewFileData
import kotlinx.coroutines.flow.Flow

// Top level use case used by HL7ViewModel
interface ObserveFileOverviewListUseCase {

    fun observeChangesForOverview(
    ): Flow<List<OverviewFileData>>

    suspend fun parseAndSaveHL7FileToDatabase(hl7Raw: String)

}