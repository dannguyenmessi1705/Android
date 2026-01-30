package com.didan.android.di.hilt.ecommerceapp.di

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Lớp Application chính của toàn ứng dụng.
 * Được sử dụng để khởi tạo các thành phần toàn cục như Dependency Injection với Hilt.
 * @HiltAndroidApp annotation kích hoạt Hilt và tạo ra các thành phần cần thiết cho DI như:
 * * Hilt components.
 * * DI containers.
 * Với @HiltAndroidApp, cần phải khai báo lớp Application trong AndroidManifest.xml với thuộc tính android:name = ".di.EcommerceApp"
 */
@HiltAndroidApp // Annotation @HiltAndroidApp dùng để đánh dấu lớp Application chính của toàn ứng dụng (global), kích hoạt Hilt.
class EcommerceApp : Application() {
}