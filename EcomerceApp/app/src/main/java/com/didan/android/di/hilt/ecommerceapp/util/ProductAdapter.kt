package com.didan.android.di.hilt.ecommerceapp.util


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.didan.android.di.hilt.ecommerceapp.databinding.ItemProductBinding
import com.didan.android.di.hilt.ecommerceapp.model.Product

class ProductAdapter(private val onProductClick: (Product) -> Unit) :
    ListAdapter<Product, ProductAdapter.ProductViewHolder>(ProductDiffCallback()) {

    inner class ProductViewHolder(val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val productImg = binding.productImage
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int
    ) {
        val product =
            getItem(position) // Lấy đối tượng Product tại vị trí hiện tại, sử dụng phương thức getItem của ListAdapter.
        holder.binding.productTitle.text = product.title // Hiển thị tiêu đề sản phẩm.

        Glide.with(holder.itemView.context) // Lấy context từ itemView của ViewHolder.
            .load(product.imageUrl) // Sử dụng Glide để tải hình ảnh từ URL hoặc tài nguyên.
            .into(holder.productImg) // Hiển thị hình ảnh vào ImageView.

        // Xử lý sự kiện khi một sản phẩm được nhấp.
        holder.itemView.setOnClickListener {
            onProductClick(product)
        }
    }
}