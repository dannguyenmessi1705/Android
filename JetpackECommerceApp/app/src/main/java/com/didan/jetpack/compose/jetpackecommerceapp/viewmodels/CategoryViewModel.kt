package com.didan.jetpack.compose.jetpackecommerceapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.didan.jetpack.compose.jetpackecommerceapp.model.Category
import com.didan.jetpack.compose.jetpackecommerceapp.repository.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {

    // Encapsulation: mutable only internally, read-only externally
    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> get() = _categories

    init {
        fetchCategories()
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            firebaseRepository.getCategoriesFlow()
                .catch {
                    println("Error in Flow")
                }
                .collect {
                    // Mỗi lần data được emit, hàm này sẽ được chạy
                    _categories.value = it
                    println("Categories updated in Viewmodel")
                }
        }
    }

}