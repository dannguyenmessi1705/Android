package com.didan.android.di.hilt.ecommerceapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items") // Đánh dấu lớp này là một thực thể trong Room database với tên bảng là "cart_items".
data class Product(
    @PrimaryKey // Đánh dấu thuộc tính này là khóa chính
    val id: String = "",
    val title: String = "",
    val price: Double = 0.0,
    val imageUrl: String = ""
)
