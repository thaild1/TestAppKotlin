package com.miagi.testappkotlin.model

data class User(
    val id: Int,
    val email: String,
    val firstName: String,
    val lastName: String,
    val role: String,
    val createdAt: String,
    val updatedAt: String
)