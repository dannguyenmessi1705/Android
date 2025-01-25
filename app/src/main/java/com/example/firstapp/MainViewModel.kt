package com.example.firstapp

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _categoriesState = mutableStateOf(Recip)
}

/*
ViewModel là một class dùng để lưu trữ dữ liệu và xử lý logic, giúp chia nhỏ logic ra khỏi Activity hoặc Fragment

Hoạt động giống như sử dụng useContext trong React hoặc sử dụng Redux
 */