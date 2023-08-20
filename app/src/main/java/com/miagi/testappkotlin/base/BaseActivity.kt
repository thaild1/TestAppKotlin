package com.miagi.testappkotlin.base

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.miagi.testappkotlin.api.RetrofitClient

abstract class BaseActivity<VM : BaseViewModel>(
    private val layoutResId: Int,
    private val viewModelClass: Class<VM>
) : AppCompatActivity() {

    protected lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResId)

        viewModel = ViewModelProvider(this)[viewModelClass]
        RetrofitClient.initialize(this)

        setupUI()
        observeViewModel()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val view = currentFocus
        if (view != null && ev?.action == MotionEvent.ACTION_DOWN) {
            val rect = Rect()
            view.getGlobalVisibleRect(rect)
            if (!rect.contains(ev.rawX.toInt(), ev.rawY.toInt())) {
                view.clearFocus()
                val inputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    abstract fun setupUI()

    abstract fun observeViewModel()
}