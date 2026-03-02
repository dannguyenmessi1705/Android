package com.didan.jetpack.compose.jetpacknoteapp.repository

import androidx.lifecycle.LiveData
import com.didan.jetpack.compose.jetpacknoteapp.roomdb.Note
import com.didan.jetpack.compose.jetpacknoteapp.roomdb.NoteDAO

// Repository là một lớp trung gian giữa ViewModel và Data Source (như Room Database, API, v.v.). Nó chịu trách nhiệm quản lý dữ liệu và cung cấp các phương thức để truy cập và thao tác dữ liệu. Trong trường hợp của ứng dụng ghi chú, NoteRepository sẽ tương tác với NoteDAO để thực hiện các thao tác như chèn, truy vấn, cập nhật và xóa ghi chú từ cơ sở dữ liệu Room. Bạn sẽ thêm các phương thức cần thiết trong NoteRepository để phục vụ cho các yêu cầu của ViewModel và giao diện người dùng.
class NoteRepository(private val noteDAO: NoteDAO) {
    val allNotes: LiveData<List<Note>> =
        noteDAO.getAllNotes() // Lấy tất cả ghi chú từ NoteDAO và lưu trữ trong một LiveData để có thể quan sát sự thay đổi dữ liệu.

    suspend fun insert(note: Note) {
        noteDAO.insert(note) // Phương thức này sẽ được gọi từ ViewModel để chèn một ghi chú mới vào cơ sở dữ liệu thông qua NoteDAO. Nó được đánh dấu là suspend vì nó sẽ được gọi từ một coroutine để thực hiện thao tác chèn dữ liệu một cách bất đồng bộ, tránh làm gián đoạn giao diện người dùng.
    }
}