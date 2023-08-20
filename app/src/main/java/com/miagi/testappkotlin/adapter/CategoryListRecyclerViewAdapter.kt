package com.miagi.testappkotlin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.miagi.testappkotlin.base.BaseSharedPreferences
import com.miagi.testappkotlin.databinding.ItemCategoryBinding
import com.miagi.testappkotlin.model.Category

class CategoryListRecyclerViewAdapter(private val categories: List<Category>) :
    RecyclerView.Adapter<CategoryListRecyclerViewAdapter.CategoryViewHolder>() {

    var selectedItems = HashSet<Int>()
    private lateinit var sharedPreferences: BaseSharedPreferences

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        sharedPreferences = BaseSharedPreferences(parent.context)
        selectedItems = sharedPreferences.getSelectedItems()
        return CategoryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.bind(category, selectedItems.contains(position))
    }

    inner class CategoryViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    if (selectedItems.contains(position)) {
                        selectedItems.remove(position)
                    } else {
                        selectedItems.add(position)
                    }
                    binding.root.isSelected = !binding.root.isSelected
                }
            }
        }

        fun bind(category: Category, isSelected: Boolean) {
            binding.model = category
            binding.root.isSelected = isSelected
            binding.executePendingBindings()
        }
    }
}