package com.miagi.testappkotlin.api.request

data class SignInRequest(
    val email: String,
    val password: String
)