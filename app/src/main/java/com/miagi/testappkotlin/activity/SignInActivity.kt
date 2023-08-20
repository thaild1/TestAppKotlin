package com.miagi.testappkotlin.activity

import android.content.Intent
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.miagi.testappkotlin.R
import com.miagi.testappkotlin.base.BaseActivity
import com.miagi.testappkotlin.base.BaseSharedPreferences
import com.miagi.testappkotlin.databinding.ActivitySignInBinding
import com.miagi.testappkotlin.viewmodel.SignInViewModel

class SignInActivity :
    BaseActivity<SignInViewModel>(R.layout.activity_sign_in, SignInViewModel::class.java),
    SignInViewModel.OnSignInViewModelListener {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var sharedPreferences: BaseSharedPreferences
    override fun setupUI() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)
        binding.viewModel = viewModel
        binding.viewModel?.setOnSignInListener(this)
        sharedPreferences = BaseSharedPreferences(this)
        binding.signInButton.isEnabled = false
        binding.signUpButton.isEnabled = false
        setTermAndPolicy()
    }

    private fun setTermAndPolicy() {
        val termsText = getString(R.string.term_txt)
        val terms = termsText.indexOf(getString(R.string.terms_of_service))
        val privacy = termsText.indexOf(getString(R.string.privacy_policy))

        val spannable = SpannableString(termsText)

        val termsClickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                Toast.makeText(this@SignInActivity, R.string.terms_of_service, Toast.LENGTH_SHORT)
                    .show()
            }

            override fun updateDrawState(textPaint: TextPaint) {
                super.updateDrawState(textPaint)
                textPaint.color = getColor(R.color.purple_blue)
                textPaint.isUnderlineText = true
            }
        }

        val privacyClickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                Toast.makeText(this@SignInActivity, R.string.privacy_policy, Toast.LENGTH_SHORT)
                    .show()
            }

            override fun updateDrawState(textPaint: TextPaint) {
                super.updateDrawState(textPaint)
                textPaint.color = getColor(R.color.purple_blue)
                textPaint.isUnderlineText = true
            }
        }

        spannable.setSpan(
            termsClickableSpan,
            terms,
            terms + getString(R.string.terms_of_service).length,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannable.setSpan(
            privacyClickableSpan,
            privacy,
            privacy + getString(R.string.privacy_policy).length,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        binding.termTextView.text = spannable
        binding.termTextView.movementMethod = LinkMovementMethod.getInstance()
    }

    override fun observeViewModel() {
        binding.viewModel!!.signInResponseData.observe(this@SignInActivity) { response ->
            if (response != null) {
                sharedPreferences.saveAccessToken(response.accessToken)
                val intent = Intent(this@SignInActivity, CategoryListActivity::class.java)
                startActivity(intent)
            }
            hideLoading()
        }
        binding.viewModel!!.signUpResponseData.observe(this@SignInActivity) { response ->
            if (response != null) {
                Toast.makeText(this@SignInActivity, "Sign-Up Success", Toast.LENGTH_SHORT).show()
            }
            hideLoading()
        }
        binding.viewModel!!.emailField.observe(this@SignInActivity) { email ->
            val password = binding.viewModel?.passwordField?.value

            val isEmailNotEmpty = email?.isNotEmpty() ?: false
            val isPasswordNotEmpty = password?.isNotEmpty() ?: false

            binding.signInButton.isEnabled = isEmailNotEmpty && isPasswordNotEmpty
            binding.signUpButton.isEnabled = isEmailNotEmpty && isPasswordNotEmpty
        }
        binding.viewModel!!.passwordField.observe(this@SignInActivity) { password ->
            val email = binding.viewModel?.emailField?.value

            val isEmailNotEmpty = email?.isNotEmpty() ?: false
            val isPasswordNotEmpty = password?.isNotEmpty() ?: false

            binding.signInButton.isEnabled = isEmailNotEmpty && isPasswordNotEmpty
            binding.signUpButton.isEnabled = isEmailNotEmpty && isPasswordNotEmpty
            binding.viewModel?.updatePasswordStrength(password)
        }

        binding.viewModel!!.passwordStrengthLevel.observe(this@SignInActivity) { value ->
            binding.passwordWeekLevel.text = value
        }

        binding.viewModel!!.passwordStrengthColor.observe(this@SignInActivity) { value ->
            binding.passwordWeekLevel.setTextColor(getColor(value))
        }
    }

    override fun onClickSignInListener() {
        showLoading()
        binding.viewModel?.signIn()
    }

    override fun onClickSignUpListener() {
        showLoading()
        binding.viewModel?.signUp()
    }

    private fun showLoading() {
        binding.loadingBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.loadingBar.visibility = View.GONE
    }
}