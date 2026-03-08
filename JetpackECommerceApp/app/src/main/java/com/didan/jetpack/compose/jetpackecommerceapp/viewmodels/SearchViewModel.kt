package com.didan.jetpack.compose.jetpackecommerceapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.didan.jetpack.compose.jetpackecommerceapp.model.Product
import com.didan.jetpack.compose.jetpackecommerceapp.repository.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: FirebaseRepository
) : ViewModel() {
    private val _searchResults = MutableStateFlow<List<Product>>(emptyList())
    val searchResults: StateFlow<List<Product>> get() = _searchResults

    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> get() = _isSearching

    fun searchProducts(query: String) {
        if (query.isBlank()) {
            _searchResults.value = emptyList()
            _isSearching.value = false
            return
        }
        _isSearching.value = true
        viewModelScope.launch {
            _searchResults.value = repository.searchProducts(query.lowercase())
            _isSearching.value = false
        }
    }
}