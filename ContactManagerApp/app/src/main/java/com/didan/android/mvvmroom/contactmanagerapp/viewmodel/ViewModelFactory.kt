package com.didan.android.mvvmroom.contactmanagerapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.didan.android.mvvmroom.contactmanagerapp.repository.ContactRepository

// Nếu viewModel của bạn cần các tham số trong constructor, bạn không thể sử dụng constructor mặc định mà viewModel cung cấp.
// Thay vào đó, bạn cần tạo một ViewModelFactory để cung cấp các tham số cần thiết khi tạo instance của ViewModel.
class ViewModelFactory(private val repository: ContactRepository) : ViewModelProvider.Factory {

    /**
     * Tạo một instance của ViewModel với các tham số cần thiết.
     * Kiểm tra kiểu của modelClass và trả về instance tương ứng.
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Kiểm tra nếu modelClass là kiểu của ContactViewModel thì trả về instance của nó với repository.
        if (modelClass.isAssignableFrom(ContactViewModel::class.java)) {
            return ContactViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}