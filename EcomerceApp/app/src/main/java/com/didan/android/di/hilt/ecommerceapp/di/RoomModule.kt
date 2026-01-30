package com.didan.android.di.hilt.ecommerceapp.di

import android.content.Context
import com.didan.android.di.hilt.ecommerceapp.room.CartDao
import com.didan.android.di.hilt.ecommerceapp.room.CartDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module // Đánh dấu lớp này là một Hilt module, dùng để cung cấp các dependencies.
@InstallIn(SingletonComponent::class) // Chỉ định phạm vi của module, ở đây là SingletonComponent (toàn ứng dụng chỉ có một instance).
object RoomModule {

    /**
     * Cung cấp một instance của CartDatabase.
     */
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): CartDatabase {
        return CartDatabase.getDatabase(context)
    }

    /**
     * Cung cấp một instance của CartDao từ CartDatabase.
     */
    @Provides
    @Singleton
    fun provideDao(database: CartDatabase): CartDao {
        return database.cartDao()
    }
}