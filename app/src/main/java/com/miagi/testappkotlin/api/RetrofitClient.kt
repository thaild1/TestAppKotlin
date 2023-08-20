package com.miagi.testappkotlin.api

import android.content.Context
import com.miagi.testappkotlin.base.BaseSharedPreferences
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://streaming.nexlesoft.com:3001/"
    private lateinit var appContext: Context

    fun initialize(context: Context) {
        appContext = context
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val baseSharedPreferences = BaseSharedPreferences(appContext)
            val accessToken = baseSharedPreferences.getAccessToken()
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .header("Authorization", "Bearer $accessToken")
                .build()
            chain.proceed(requestBuilder)
        }
        .build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiDefinition: ApiDefinition by lazy {
        retrofit.create(ApiDefinition::class.java)
    }
}
