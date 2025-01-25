package com.example.firstapp

data class Category(
    val strCategory: String,
    val strCategoryThumb: String,
    val strCategoryDescription: String
) // Dữ liệu cần lấy từ API

data class CategoriesResponse(
    val categories: List<Category>
) // Dữ liệu trả về từ API

/*
data class là một class dùng để lưu trữ dữ liệu, nó sẽ tự động tạo ra các hàm getter và setter cho các thuộc tính của nó
giống như sử dụng các Annotation Lombok trong Java
*/