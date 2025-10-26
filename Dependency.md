DEPENDENCIES TRONG ANDROID VỚI JETPACK COMPOSE

# 1. Dependencies là gì?

Dependencies là các thư viện, module, hoặc các tài nguyên khác mà ứng dụng cần để hoạt động. Trong
Android, chúng ta thường sử dụng dependencies để thêm các thư viện hỗ trợ, các module giúp việc phát
triển ứng dụng dễ dàng hơn.

# 2. Các dependencies phổ biến trong Android với Jetpack Compose

Dưới đây là một số dependencies phổ biến mà chúng ta thường sử dụng trong Android với Jetpack
Compose:

## 2.1. androidx.lifecycle:lifecycle-viewmodel-compose

`androidx.lifecycle:lifecycle-viewmodel-compose` là một dependencies giúp chúng ta sử dụng ViewModel
trong Compose. ViewModel giúp chúng ta lưu trữ và quản lý dữ liệu giữa các configuration changes.
Giống như sử dụng `useContext` trong React.

## 2.2. com.squareup.retrofit2:retrofit

`com.squareup.retrofit2:retrofit` là một dependencies giúp chúng ta thực hiện các HTTP requests một
cách dễ dàng. Retrofit giúp chúng ta tạo các API service để giao tiếp với server.

## 2.3. com.squareup.retrofit2:converter-gson

`com.squareup.retrofit2:converter-gson` là một dependencies giúp chúng ta chuyển đổi JSON sang
Java/Kotlin objects và ngược lại. Gson là một thư viện giúp chúng ta parse JSON dễ dàng.

## 2.4. io.coil-kt:coil-compose

`io.coil-kt:coil-compose` là một dependencies giúp chúng ta load ảnh từ Internet một cách dễ dàng.
Coil là một thư viện giúp chúng ta load ảnh hiệu quả và dễ dàng.

## 2.5. androidx.navigation:navigation-compose

`androidx.navigation:navigation-compose` là một dependencies giúp chúng ta sử dụng Navigation trong
Compose.

## 2.6. com.google.android.gms:play-services-location

`com.google.android.gms:play-services-location` là một dependencies giúp chúng ta sử dụng các dịch
vụ vị trí của Google Play Services.

## 2.7. com.google.android.gms:play-services-maps

`com.google.android.gms:play-services-maps` là một dependencies giúp chúng ta chuyển đổi từ địa
chỉ (lat, lng) sang tên địa điểm và ngược lại.

## 2.8. com.google.maps.android:maps-compose

`com.google.maps.android:maps-compose` là một dependencies giúp chúng ta sử dụng Google Maps trong
Compose.

# 3. Một số từ khóa trong file `build.gradle.kts`
Dưới đây là một số từ khóa phổ biến trong file `build.gradle.kts`:
- `implementation`: Dùng để thêm một dependencies vào project. Dependencies này sẽ được sử dụng
  trong quá trình biên dịch và chạy ứng dụng.
- `kapt`: Dùng để thêm một dependencies cho Kotlin Annotation Processing Tool (KAPT). Dependencies này
  sẽ được sử dụng trong quá trình biên dịch để xử lý các annotation. Để sử dụng KAPT, bạn cần thêm 
  plugin KAPT vào file `build.gradle.kts` của bạn:
  ```kotlin
    plugins {
        id("kotlin-kapt")
    }
    dependencies {
        kapt("com.google.dagger:dagger-compiler:2.40.5")
    }
    ```
- `testImplementation`: Dùng để thêm một dependencies chỉ sử dụng trong quá trình kiểm thử (testing). Dependencies này sẽ không được bao gồm trong ứng dụng cuối cùng.
- `androidTestImplementation`: Dùng để thêm một dependencies chỉ sử dụng trong quá trình kiểm thử trên thiết bị Android (instrumented tests). Dependencies này sẽ không được bao gồm trong ứng dụng cuối cùng.
- `debugImplementation`: Dùng để thêm một dependencies chỉ sử dụng trong bản build debug. Dependencies này sẽ không được bao gồm trong bản build release.
- `releaseImplementation`: Dùng để thêm một dependencies chỉ sử dụng trong bản build release. Dependencies này sẽ không được bao gồm trong bản build debug.
- `api`: Dùng để thêm một dependencies vào project, tương tự như `implementation`, nhưng khác ở chỗ
  dependencies này sẽ được xuất khẩu (exported) cho các module khác sử dụng nếu module hiện tại
  được sử dụng như một dependency.
- `plugin.alias`: Dùng để thêm một plugin vào project bằng cách sử dụng alias đã được định nghĩa trong
  `settings.gradle.kts` hoặc `buildSrc`.
- Các thuộc tính trong `android {}`: Đây là nơi chúng ta cấu hình các thiết lập liên quan đến Android, như phiên bản SDK,
  phiên bản build tools, cấu hình build types, v.v.
Ví dụ:
```kotlin
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}
android {
    compileSdk = 33 // Đặt phiên bản SDK để biên dịch ứng dụng

    defaultConfig { 
        applicationId = "com.example.myapp" // Đặt ID ứng dụng 
        minSdk = 21 // Đặt phiên bản SDK tối thiểu mà ứng dụng hỗ trợ
        targetSdk = 33 // Đặt phiên bản SDK mục tiêu
        versionCode = 1 // Đặt mã phiên bản ứng dụng
        versionName = "1.0" // Đặt tên phiên bản ứng dụng
    }

    buildTypes { 
        release { 
            isMinifyEnabled = false // Tắt minification cho bản release 
            proguardFiles( 
                getDefaultProguardFile("proguard-android-optimize.txt"),  // Sử dụng file ProGuard mặc định
                "proguard-rules.pro" // Sử dụng file ProGuard tùy chỉnh
            )
        }
    }
}
dependencies {
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1") // Thêm ViewModel Compose
    implementation("com.squareup.retrofit2:retrofit:2.9.0") // Thêm Retrofit
    implementation("com.squareup.retrofit2:converter-gson:2.9.0") // Thêm Gson Converter cho Retrofit
    implementation("io.coil-kt:coil-compose:2.1.0") // Thêm Coil Compose
    implementation("androidx.navigation:navigation-compose:2.5.1") // Thêm Navigation Compose
    implementation("com.google.android.gms:play-services-location:21.0.1") // Thêm Google Play Services Location
    implementation("com.google.android.gms:play-services-maps:18.0.2") // Thêm Google Play Services Maps
    implementation("com.google.maps.android:maps-compose:2.5.0") // Thêm Maps Compose
}
```
