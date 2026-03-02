package com.didan.jetpack.compose.jetpacknoteapp.roomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

// Đây là interface DAO (Data Access Object) để định nghĩa các phương thức truy cập dữ liệu cho bảng "notes_table" trong cơ sở dữ liệu Room. Bạn sẽ thêm các phương thức như insert, update, delete, và query ở đây để tương tác với bảng ghi chú.
@Dao
interface NoteDAO {

    @Insert // Phương thức này sẽ được sử dụng để chèn một ghi chú mới vào bảng "notes_table". Room sẽ tự động tạo mã SQL để thực hiện thao tác này dựa trên kiểu dữ liệu của Note.
    suspend fun insert(note: Note) // Phương thức này được đánh dấu là suspend vì nó sẽ được gọi từ một coroutine để thực hiện thao tác chèn dữ liệu một cách bất đồng bộ, tránh làm gián đoạn giao diện người dùng.

    @Query("SELECT * FROM notes_table") // Phương thức này sẽ được sử dụng để truy vấn tất cả các ghi chú từ bảng "notes_table". Room sẽ tự động tạo mã SQL để thực hiện truy vấn này.
    fun getAllNotes(): LiveData<List<Note>> // Phương thức này sẽ trả về một LiveData chứa danh sách tất cả các ghi chú trong bảng "notes_table". LiveData sẽ tự động cập nhật giao diện người dùng khi dữ liệu thay đổi, giúp bạn dễ dàng hiển thị danh sách ghi chú trong ứng dụng của mình.


}