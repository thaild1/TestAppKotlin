package com.miagi.testappkotlin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.miagi.testappkotlin.api.RetrofitClient
import com.miagi.testappkotlin.base.BaseViewModel
import com.miagi.testappkotlin.model.Category
import com.miagi.testappkotlin.repository.CategoryListRepository
import kotlinx.coroutines.launch

class CategoryListViewModel : BaseViewModel() {

    val categoryListData = MutableLiveData<List<Category>>()
    private val categoryListRepository = CategoryListRepository(RetrofitClient.apiDefinition)

    fun getCategoryList() {
        viewModelScope.launch {
            val categories = categoryListRepository.getCategories()
            categoryListData.value = categories
        }
    }

}