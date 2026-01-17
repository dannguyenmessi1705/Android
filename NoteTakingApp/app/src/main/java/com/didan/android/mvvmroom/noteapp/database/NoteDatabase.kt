package com.didan.android.mvvmroom.noteapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.didan.android.mvvmroom.noteapp.model.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun getNoteDao(): NoteDAO

    companion object {
        @Volatile // Đảm bảo rằng instance được đồng bộ hóa giữa các luồng
        private var instance: NoteDatabase? = null // Biến lưu trữ thể hiện của cơ sở dữ liệu
        private val LOCK = Any() // Đối tượng khóa để đồng bộ hóa (Any() tạo một đối tượng mới trống)

        // operator là một từ khóa trong Kotlin cho phép bạn định nghĩa hành vi của các toán tử
        // trong các lớp của bạn. Ở đây, nó được sử dụng để định nghĩa hành vi khi gọi
        // NoteDatabase(context) như một hàm.
        operator fun invoke(context: Context) = instance ?:  // Kiểm tra nếu instance đã được khởi tạo, nếu có thì trả về nó
            synchronized(LOCK) { // Đồng bộ hóa để tránh xung đột khi nhiều luồng cùng truy cập
                instance ?: // Kiểm tra lại nếu instance vẫn chưa được khởi tạo
                createDatabase(context).also { // Tạo cơ sở dữ liệu mới và gán nó cho instance
                    instance = it // Gán thể hiện mới tạo cho biến instance
                }
            }

        // Hàm tạo cơ sở dữ liệu Room
        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            NoteDatabase::class.java,
            "note_db"
        ).build()
    }
}