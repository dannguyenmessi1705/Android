package com.didan.android.di.hilt.ecommerceapp.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.didan.android.di.hilt.ecommerceapp.databinding.ItemCartBinding
import com.didan.android.di.hilt.ecommerceapp.model.Product

class CartAdapter(
    private val onRemoveItem: (Product) -> Unit
) : ListAdapter<Product, CartAdapter.CartViewHolder>(ProductDiffCallback()) {

    // Implementation of CartAdapter goes here
    class CartViewHolder(val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root) {
        // ViewHolder implementation
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CartViewHolder {
        val binding = ItemCartBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int
    ) {
        val product =
            getItem(position) // Lấy đối tượng Product tại vị trí hiện tại, sử dụng phương thức getItem của ListAdapter.
        // Bind data to views and set up click listeners
        holder.binding.cartItemTitle.text = product.title
        holder.binding.cartItemPrice.text = product.price.toString()

        // Dùng Glide để tải hình ảnh vào ImageView
        Glide.with(holder.itemView.context) // Lấy context từ itemView của ViewHolder.
            .load(product.imageUrl) // Sử dụng Glide để tải hình ảnh từ URL hoặc tài nguyên.
            .into(holder.binding.cartItemImage) // Hiển thị hình ảnh vào ImageView.

        // Xử lý sự kiện khi nút "Remove" được nhấn.
        holder.binding.removeCartItemButton.setOnClickListener {
            onRemoveItem(product) // Gọi hàm khi sản phẩm được xóa khỏi giỏ hàng.
        }
    }
}