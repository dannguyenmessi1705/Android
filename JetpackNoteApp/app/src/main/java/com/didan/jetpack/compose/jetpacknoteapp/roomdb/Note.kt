package com.didan.jetpack.compose.jetpacknoteapp.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table") // Tên bảng trong cơ sở dữ liệu
data class Note(
    @PrimaryKey(autoGenerate = true) // Tự động tăng ID cho mỗi ghi chú mới
    val id: Int, // ID của ghi chú, có thể là khóa chính
    val title: String, // Tiêu đề của ghi chú
    val description: String, // Mô tả của ghi chú
    val color: Int // Màu sắc của ghi chú, lưu dưới dạng ARGB số nguyên (Room không hỗ trợ trực tiếp kiểu Color, nên chúng ta sẽ lưu dưới dạng Int)
)