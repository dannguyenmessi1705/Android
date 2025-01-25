package com.example.firstapp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _categoriesState =
        mutableStateOf(RecipeState())  // Dữ liệu trạng thái của Recipe, mặc định là rỗng

    // Sử dụng "_" để đánh dấu biến private, chỉ có thể truy cập từ bên trong class. Mục đích không cho phép truy cập từ bên ngoài class
    val categoryState: State<RecipeState> =
        _categoriesState // Trạng thái của Recipe, có thể truy cập từ bên ngoài class

    init {
        fetchCategories() // Gọi hàm fetchCategories khi khởi tạo ViewModel
    } // Hàm khởi tạo, chạy khi class được khởi tạo (Giống @PostConstruct trong Spring)

    private fun fetchCategories() {
        viewModelScope.launch {
            try {
                val response = recipeService.getCategories() // Gọi API từ ApiService
                _categoriesState.value = _categoriesState.value.copy(
                    loading = false,
                    list = response.categories,
                    error = null
                ) // Copy dữ liệu từ _categoriesState sang _categoriesState, thay đổi trạng thái loading và list
            } catch (e: Exception) {
                _categoriesState.value = _categoriesState.value.copy(
                    loading = false,
                    error = "An error occurred while fetching categories"
                ) // Copy dữ liệu từ _categoriesState sang _categoriesState, thay đổi trạng thái loading và error
            }
        } // Sử dụng viewModelScope để chạy một coroutine, giúp chương trình chạy liên tục mà không làm chương trình chờ đợi, giống như sử dụng async/await trong JavaScript
    }

    data class RecipeState(
        val loading: Boolean = false, // Trạng thái loading, mặc định là false, khi nào đang call API thì sẽ là true
        val list: List<Category> = emptyList(), // Danh sách Category, mặc định là rỗng
        val error: String? = null // Lỗi khi call API, mặc định là null
    ) // Dữ liệu trạng thái của Recipe
}

/*
ViewModel là một class dùng để lưu trữ dữ liệu và xử lý logic, giúp chia nhỏ logic ra khỏi Activity hoặc Fragment

Hoạt động giống như sử dụng useContext trong React hoặc sử dụng Redux
 */