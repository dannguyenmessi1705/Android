package com.didan.jetpack.compose.jetpackecommerceapp.di

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        // Khởi tạo firebase
        FirebaseApp.initializeApp(this)
    }
}