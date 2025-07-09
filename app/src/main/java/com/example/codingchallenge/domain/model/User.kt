package com.example.codingchallenge.domain.model

// TODO does this need to be in domain? This object is only created to make it easier to show in UI
data class User(
    val name: String,
    val diaryNumber: String,
    val birthday: String
)