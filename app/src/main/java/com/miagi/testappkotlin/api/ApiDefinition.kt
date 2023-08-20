package com.miagi.testappkotlin.api

import com.miagi.testappkotlin.api.request.SignInRequest
import com.miagi.testappkotlin.api.request.SignUpRequest
import com.miagi.testappkotlin.api.responce.SignInResponse
import com.miagi.testappkotlin.model.Category
import com.miagi.testappkotlin.model.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiDefinition {
    @POST("auth/signin")
    suspend fun signIn(@Body signInRequest: SignInRequest): SignInResponse

    @GET("categories")
    suspend fun getCategories(): List<Category>

    @POST("auth/signup")
    suspend fun signUp(@Body signInRequest: SignUpRequest): User
}