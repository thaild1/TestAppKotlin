package com.miagi.testappkotlin.api.request

data class SignUpRequest(
    val email: String,
    val password: String,
    val firstName : String,
    val lastName: String
)