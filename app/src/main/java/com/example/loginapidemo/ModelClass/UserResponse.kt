package com.example.loginapidemo.ModelClass

data class UserResponse(
    val code: String,
    val `data`: Data,
    val messages: List<String>,
    val status: String
)