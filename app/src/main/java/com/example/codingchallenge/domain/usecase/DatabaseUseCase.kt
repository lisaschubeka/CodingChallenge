package com.example.codingchallenge.domain.usecase

import com.example.codingchallenge.domain.model.HL7Data

// TODO should it be called this??
interface DatabaseUseCase {
    fun save(hl7data: HL7Data)
    fun retrieve(): HL7Data
}
