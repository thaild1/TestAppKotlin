package com.miagi.testappkotlin.viewmodel

import android.graphics.Color
import android.util.Log
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.miagi.testappkotlin.R
import com.miagi.testappkotlin.api.RetrofitClient
import com.miagi.testappkotlin.api.responce.SignInResponse
import com.miagi.testappkotlin.base.BaseViewModel
import com.miagi.testappkotlin.model.User
import com.miagi.testappkotlin.repository.SignInRepository
import kotlinx.coroutines.launch

class SignInViewModel : BaseViewModel() {

    interface OnSignInViewModelListener {
        fun onClickSignInListener()

        fun onClickSignUpListener()
    }

    private lateinit var mListener: OnSignInViewModelListener

    private val authRepository = SignInRepository(RetrofitClient.apiDefinition)

    val signInResponseData = MutableLiveData<SignInResponse>()
    val signUpResponseData = MutableLiveData<User>()
    val emailField = MutableLiveData<String>()
    val passwordField = MutableLiveData<String>()
    val passwordStrengthLevel = MutableLiveData<String>().apply {
        value = "Too Short"
    }
    val passwordStrengthColor = MutableLiveData<Int>()

    fun signIn() {
        viewModelScope.launch {
            val response = authRepository.signIn(emailField.value!!, passwordField.value!!)
            signInResponseData.value = response
        }
    }

    fun signUp() {
        viewModelScope.launch {
            val response = authRepository.signUp("Name","LastName",emailField.value!!, passwordField.value!!)
            signUpResponseData.value = response
        }
    }

    fun updatePasswordStrength(password: String) {
        val isUppercase = "[A-Z]".toRegex().containsMatchIn(password)
        val isLowercase = "[a-z]".toRegex().containsMatchIn(password)
        val isNumeric = "\\d".toRegex().containsMatchIn(password)
        val isSpecialChar = "[!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]".toRegex().containsMatchIn(password)

        val level = when {
            password.length >= 8 && isUppercase && isLowercase && isNumeric && isSpecialChar -> "Strong"
            password.length >= 6 && isUppercase && isLowercase && isNumeric -> "Good"
            password.length >= 4 && (isUppercase || isLowercase) && isNumeric -> "Fair"
            password.isEmpty() -> "Too Short"
            else -> "Weak"
        }

        passwordStrengthLevel.value = level
        passwordStrengthColor.value = setPasswordStrengthColor(level)
    }

    private fun setPasswordStrengthColor(level: String): Int {
        return when (level) {
            "Weak" -> R.color.weakColor
            "Fair" -> R.color.fairColor
            "Good" -> R.color.goodColor
            "Strong" -> R.color.strongColor
            else -> R.color.defaultColor
        }
    }

    fun setOnSignInListener(listener: OnSignInViewModelListener) {
        mListener = listener
    }

    fun doSignIn() {
        mListener.onClickSignInListener()
    }

    fun doSignUp() {
        mListener.onClickSignUpListener()
    }
}