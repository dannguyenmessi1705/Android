package com.didan.android.di.hilt.ecommerceapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.didan.android.di.hilt.ecommerceapp.model.Product

@Database(entities = [Product::class], version = 1, exportSchema = false)
abstract class CartDatabase : RoomDatabase() {

    // Định nghĩa phương thức trừu tượng để truy cập CartDao.
    abstract fun cartDao(): CartDao

    // Tạo một companion object để giữ tham chiếu đến instance của CartDatabase.
    companion object {
        @Volatile // Đảm bảo rằng INSTANCE được đồng bộ hóa giữa các luồng.
        private var INSTANCE: CartDatabase? = null // Biến lưu trữ instance của CartDatabase.

        fun getDatabase(context: Context): CartDatabase {
            return INSTANCE ?: synchronized(this) { // Đồng bộ hóa để tránh tạo nhiều instance trong môi trường đa luồng.
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CartDatabase::class.java,
                    "cart_database"
                ).build() // Xây dựng instance của CartDatabase.
                INSTANCE = instance // Gán instance vào biến INSTANCE.
                instance // Trả về instance.
            }
        }

    }
}