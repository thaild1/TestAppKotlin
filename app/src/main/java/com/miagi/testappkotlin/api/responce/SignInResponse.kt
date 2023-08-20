package com.miagi.testappkotlin.api.responce

import com.miagi.testappkotlin.model.User

data class SignInResponse(
    val user: User,
    val accessToken: String,
    val refreshToken: String
)