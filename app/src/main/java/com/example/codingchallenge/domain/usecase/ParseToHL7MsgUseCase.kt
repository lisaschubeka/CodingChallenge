package com.example.codingchallenge.domain.usecase

import com.example.codingchallenge.domain.model.HL7Message

interface ParseToHL7MsgUseCase {
    fun parseToHL7Msg(): HL7Message?
}