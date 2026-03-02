package com.didan.jetpack.compose.jetpacknoteapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.didan.jetpack.compose.jetpacknoteapp.repository.NoteRepository

class NoteViewModelFactory(private val noteRepository: NoteRepository) : ViewModelProvider.Factory {

    // Nếu ViewModel của bạn có một constructor với tham số, bạn cần phải tạo một Factory để cung cấp các tham số đó khi tạo ViewModel. Trong trường hợp này, NoteViewModelFactory sẽ nhận một instance của NoteRepository và sử dụng nó để tạo NoteViewModel.

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") // Suppress cảnh báo về kiểu không an toàn khi ép kiểu.
            return NoteViewModel(noteRepository) as T // Nếu modelClass là NoteViewModel, tạo một instance của NoteViewModel bằng cách truyền noteRepository vào constructor của nó và trả về instance đó.
        }
        throw IllegalArgumentException("Unknown ViewModel class") // Nếu modelClass không phải là NoteViewModel, ném một IllegalArgumentException để thông báo rằng lớp ViewModel không được biết đến.
    }
}