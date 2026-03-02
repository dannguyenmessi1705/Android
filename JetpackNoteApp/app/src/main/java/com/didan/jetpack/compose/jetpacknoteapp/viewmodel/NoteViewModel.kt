package com.didan.jetpack.compose.jetpacknoteapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.didan.jetpack.compose.jetpacknoteapp.repository.NoteRepository
import com.didan.jetpack.compose.jetpacknoteapp.roomdb.Note
import kotlinx.coroutines.launch

// ViewModel: Lưu trữ và quản lý dữ liệu liên quan đến giao diện người dùng một cách bền vững qua các thay đổi cấu hình như xoay màn hình. NoteViewModel sẽ tương tác với NoteRepository để lấy dữ liệu và cung cấp dữ liệu đó cho giao diện người dùng thông qua LiveData hoặc StateFlow. Bạn sẽ thêm các phương thức cần thiết trong NoteViewModel để phục vụ cho các yêu cầu của giao diện người dùng, như lấy danh sách ghi chú, chèn ghi chú mới, v.v.
class NoteViewModel(private val repository: NoteRepository) : ViewModel() {

    val allNotes: LiveData<List<Note>> =
        repository.allNotes // Lấy tất cả ghi chú từ NoteRepository và lưu trữ trong một LiveData để có thể quan sát sự thay đổi dữ liệu.

    fun insert(note: Note) = viewModelScope.launch {
        repository.insert(note) // Phương thức này sẽ được gọi từ giao diện người dùng để chèn một ghi chú mới vào cơ sở dữ liệu thông qua NoteRepository. Nó được gọi trong viewModelScope để đảm bảo rằng nó sẽ được hủy bỏ nếu ViewModel bị hủy, tránh rò rỉ bộ nhớ và đảm bảo rằng các thao tác bất đồng bộ được quản lý đúng cách.
    }
}