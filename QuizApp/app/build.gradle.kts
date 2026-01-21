import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.didan.android.retrofit.quizapp"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.didan.android.retrofit.quizapp"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
            freeCompilerArgs.add("-opt-in=kotlin.RequiresOptIn")
        }
    }
    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Retrofit
    implementation(libs.retrofit)
    // Converter Gson
    implementation(libs.retrofit.converter.gson)
    // Gson
    implementation(libs.gson)

    // Coroutine dùng để bất đồng bộ
    implementation(libs.kotlinx.coroutines.android)
    // OkHttp Logging Interceptor để log dữ liệu gửi và nhận
    implementation(libs.logging.interceptor)

    // ViewModel và LiveData
    implementation(libs.androidx.viewmodel)
    implementation(libs.androidx.livedata)
    // Annotation processor
    kapt(libs.androidx.lifecycle.compiler)
}