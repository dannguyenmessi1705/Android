package com.didan.android.mvvmroom.contactmanagerapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Contact::class], version = 1, exportSchema = false)
abstract class ContactDatabase : RoomDatabase() {

    abstract val contactDAO: ContactDAO

    // Singleton pattern để đảm bảo chỉ có một instance của database được tạo ra, tránh việc tạo nhiều kết nối không cần thiết.
    // companion object: Định nghĩa các thành phần tĩnh trong lớp ContactDatabase.
    // @Volatile: Đảm bảo rằng không có việc tranh chấp dữ liệu khi nhiều luồng truy cập vào INSTANCE cùng lúc.
    companion object {
        @Volatile
        private var INSTANCE: ContactDatabase ?= null
        // Phương thức để lấy instance của ContactDatabase.
        fun getInstance(context: Context): ContactDatabase {
            // Synchronized để đảm bảo chỉ một luồng có thể truy cập vào block này tại một thời điểm.
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext, // Sử dụng context của ứng dụng để tránh rò rỉ bộ nhớ.
                        ContactDatabase::class.java, // Lớp database cần tạo.
                        "contacts_db" // Tên của file database.
                    ).build()
                }

                INSTANCE = instance // Lưu trữ instance đã tạo vào biến tĩnh.
                return instance // Trả về instance của ContactDatabase.
            }
        }
    }
}