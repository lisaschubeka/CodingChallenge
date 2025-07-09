package com.example.codingchallenge.domain.model

data class TestResult(
    val id: String,
    var testName: String,
    val value: String,
    val unit: String,
    val range: String?,
    val note: String?
)