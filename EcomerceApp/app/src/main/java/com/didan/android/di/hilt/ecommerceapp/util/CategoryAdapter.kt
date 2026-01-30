package com.didan.android.di.hilt.ecommerceapp.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.didan.android.di.hilt.ecommerceapp.databinding.ItemCategoryBinding
import com.didan.android.di.hilt.ecommerceapp.model.Category

class CategoryAdapter(
    private val onCategoryClick: (String) -> Unit // Lambda function được gọi khi một danh mục được nhấp, truyền vào tên danh mục.
) : ListAdapter<Category, CategoryAdapter.CategoryViewHolder>(CategoryDiffCallback()) {

    inner class CategoryViewHolder(val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val categoryImg = binding.imageViewCategory
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CategoryViewHolder,
        position: Int
    ) {
        val category = getItem(position) // Lấy đối tượng Category tại vị trí hiện tại, sử dụng phương thức getItem của ListAdapter.

        holder.binding.categoryName.text = category.name

        // Dùng Glide để tải hình ảnh vào ImageView 1 cách hiệu quả và tối ưu bộ nhớ.
        Glide.with(holder.itemView.context) // Lấy context từ itemView của ViewHolder.
            .load(category.catImg) // Sử dụng Glide để tải hình ảnh từ URL hoặc tài nguyên.
            .into(holder.categoryImg) // Hiển thị hình ảnh vào ImageView.

        holder.itemView.setOnClickListener {
            onCategoryClick(category.name) // Gọi hàm khi danh mục được nhấp, truyền vào tên danh mục.
        }
    }

}