package com.miagi.testappkotlin.repository

import com.miagi.testappkotlin.api.ApiDefinition
import com.miagi.testappkotlin.api.request.SignInRequest
import com.miagi.testappkotlin.api.request.SignUpRequest
import com.miagi.testappkotlin.api.responce.SignInResponse
import com.miagi.testappkotlin.model.User

class SignInRepository(private val apiDefinition: ApiDefinition) {
    suspend fun signIn(email: String, password: String): SignInResponse? {
        val signInRequest = SignInRequest(email, password)
        return try {
            val response = apiDefinition.signIn(signInRequest)
            response
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun signUp(name: String, lastName: String, email: String, password: String): User? {
        val signUpRequest = SignUpRequest(email, password, name, lastName)
        return try {
            val response = apiDefinition.signUp(signUpRequest)
            response
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}