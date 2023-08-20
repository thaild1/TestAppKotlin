package com.miagi.testappkotlin.repository

import com.miagi.testappkotlin.api.ApiDefinition
import com.miagi.testappkotlin.model.Category

class CategoryListRepository(private val apiDefinition: ApiDefinition) {
    suspend fun getCategories(): List<Category>? {
        return try {
            val response = apiDefinition.getCategories()
            response
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}