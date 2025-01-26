package com.example.firstapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val strCategory: String,
    val strCategoryThumb: String,
    val strCategoryDescription: String
) : Parcelable // Dữ liệu cần lấy từ API, sử dụng Parcelable để deserialize dữ liệu từ Object sang dạng String để truyền dữ liệu qua các màn hình

data class CategoriesResponse(
    val categories: List<Category>
) // Dữ liệu trả về từ API

/*
data class là một class dùng để lưu trữ dữ liệu, nó sẽ tự động tạo ra các hàm getter và setter cho các thuộc tính của nó
giống như sử dụng các Annotation Lombok trong Java
*/