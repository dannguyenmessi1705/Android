package com.didan.android.di.hilt.ecommerceapp.di

import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module // Annotation @Module đánh dấu lớp này là một Hilt module, dùng để cung cấp các dependencies.
@InstallIn(SingletonComponent::class) // Annotation @InstallIn chỉ định phạm vi của module, ở đây là SingletonComponent (toàn ứng dụng chỉ có một instance).
object FirebaseModule {

    @Provides // Annotation @Provides đánh dấu phương thức này sẽ cung cấp một dependency.
    @Singleton // Annotation @Singleton chỉ định rằng dependency này sẽ có phạm vi singleton (chỉ có một instance trong toàn ứng dụng).
    fun provideFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    } // Hàm này cung cấp một instance của FirebaseFirestore.
}