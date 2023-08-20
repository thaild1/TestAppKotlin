package com.miagi.testappkotlin.base

import android.content.Context
import android.content.SharedPreferences

open class BaseSharedPreferences(context: Context) {
    object Constants {
        const val ACCESS_TOKEN = "access_token"
        const val SELECTED_ITEMS = "selected_items"
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    fun saveAccessToken(accessToken: String) {
        sharedPreferences.edit().putString(Constants.ACCESS_TOKEN, accessToken).apply()
    }

    fun getAccessToken(): String? {
        return sharedPreferences.getString(Constants.ACCESS_TOKEN, null)
    }

    fun saveSelectedItems(selectedItems : HashSet<Int>) {
        sharedPreferences.edit().putStringSet(Constants.SELECTED_ITEMS, selectedItems.map { it.toString() }.toSet()).apply()
    }

    fun getSelectedItems(): HashSet<Int> {
        val selectedItemsSet = sharedPreferences.getStringSet(Constants.SELECTED_ITEMS, emptySet())
        return selectedItemsSet?.mapNotNull { it.toIntOrNull() }?.toHashSet() ?: HashSet()
    }
}