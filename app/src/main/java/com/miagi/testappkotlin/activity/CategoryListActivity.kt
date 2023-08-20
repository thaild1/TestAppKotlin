package com.miagi.testappkotlin.activity

import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.miagi.testappkotlin.R
import com.miagi.testappkotlin.adapter.CategoryListRecyclerViewAdapter
import com.miagi.testappkotlin.base.BaseActivity
import com.miagi.testappkotlin.base.BaseSharedPreferences
import com.miagi.testappkotlin.common.ItemSpacingDecoration
import com.miagi.testappkotlin.databinding.ActivityCategoryListBinding
import com.miagi.testappkotlin.model.Category
import com.miagi.testappkotlin.viewmodel.CategoryListViewModel

class CategoryListActivity:
    BaseActivity<CategoryListViewModel>(R.layout.activity_category_list, CategoryListViewModel::class.java) {
    private lateinit var binding: ActivityCategoryListBinding
    private lateinit var sharedPreferences: BaseSharedPreferences
    override fun setupUI() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_category_list)
        binding.viewModel = viewModel
        sharedPreferences = BaseSharedPreferences(this)
        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        getCategoryList()
    }

    private fun getCategoryList() {
        showLoading()
        binding.viewModel?.getCategoryList()
    }

    private fun setCategoryList(categoryList :List<Category>) {
        val adapter = CategoryListRecyclerViewAdapter(categoryList)
        binding.categoryListRecyclerView.adapter = adapter

        binding.doneButton.setOnClickListener{
            sharedPreferences.saveSelectedItems(adapter.selectedItems)
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
        }

        val spanCount = 3
        val layoutManager = GridLayoutManager(this, spanCount)
        binding.categoryListRecyclerView.layoutManager = layoutManager

        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.item_spacing)
        binding.categoryListRecyclerView.addItemDecoration(ItemSpacingDecoration(spacingInPixels))
    }

    override fun observeViewModel() {
        binding.viewModel!!.categoryListData.observe(this@CategoryListActivity) { response ->
            if (response != null) {
                setCategoryList(response)
            }
            hideLoading()
        }
    }

    private fun showLoading() {
        binding.loadingBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.loadingBar.visibility = View.GONE
    }
}