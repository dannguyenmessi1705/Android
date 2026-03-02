package com.didan.jetpack.compose.jetpacknoteapp.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Note::class],
    version = 1,
    exportSchema = false
) // Chỉ định các thực thể (entities) và phiên bản của cơ sở dữ liệu. exportSchema = false để không tạo file schema khi biên dịch.
abstract class NotesDB : RoomDatabase() {
    abstract val notesDAO: NoteDAO // Định nghĩa một phương thức trừu tượng để lấy đối tượng DAO (Data Access Object) cho bảng "notes_table". Room sẽ tự động tạo mã để triển khai phương thức này.
    // Singleton pattern để đảm bảo chỉ có một instance của cơ sở dữ liệu được tạo ra trong toàn bộ ứng dụng.

    // companion object định nghĩa 1 singleton instance của cơ sở dữ liệu, giúp đảm bảo rằng chỉ có một instance được tạo ra và sử dụng trong toàn bộ ứng dụng. Điều này giúp tiết kiệm tài nguyên và tránh lỗi khi có nhiều instance của cơ sở dữ liệu cùng tồn tại.
    // @Volatile để đảm bảo rằng instance của cơ sở dữ liệu được cập nhật ngay lập tức trên tất cả các luồng khi có sự thay đổi, tránh tình trạng nhiều luồng cùng tạo ra nhiều instance của cơ sở dữ liệu.

    companion object {
        @Volatile
        private var INSTANCE: NotesDB? =
            null // Biến INSTANCE được đánh dấu là @Volatile để đảm bảo rằng khi một luồng cập nhật giá trị của INSTANCE, tất cả các luồng khác sẽ thấy sự thay đổi này ngay lập tức. Điều này giúp tránh tình trạng nhiều luồng cùng tạo ra nhiều instance của cơ sở dữ liệu, đảm bảo rằng chỉ có một instance duy nhất được sử dụng trong toàn bộ ứng dụng.

        fun getInstance(context: Context): NotesDB {
            // Đảm bảo rằng chỉ có một instance của cơ sở dữ liệu được tạo ra bằng cách sử dụng synchronized để đồng bộ hóa truy cập vào đoạn mã tạo instance. Nếu INSTANCE đã tồn tại, nó sẽ được trả về ngay lập tức. Nếu chưa tồn tại, một instance mới sẽ được tạo ra và gán cho INSTANCE trước khi trả về.
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    // Taọ đối tượng DB
                    instance = Room.databaseBuilder(
                        context = context, // Context của ứng dụng, được sử dụng để truy cập vào hệ thống file và các tài nguyên khác.
                        NotesDB::class.java, // Lớp cơ sở dữ liệu mà chúng ta đã định nghĩa.
                        "notes_db" // Tên của cơ sở dữ liệu, có thể tùy chỉnh theo ý muốn.
                    )
                        .build() // Phương thức build() sẽ tạo ra một instance của cơ sở dữ liệu dựa trên các tham số đã cung cấp. Room sẽ tự động tạo mã để triển khai cơ sở dữ liệu và quản lý kết nối đến nó.
                }
                INSTANCE =
                    instance // Gán instance mới tạo ra cho biến INSTANCE để đảm bảo rằng lần sau khi getInstance được gọi, nó sẽ trả về instance đã tồn tại thay vì tạo một instance mới.
                return instance
            }
        }
    }
}