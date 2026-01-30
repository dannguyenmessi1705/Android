// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    // Add the dependency for the Google services Gradle plugin
    id("com.google.gms.google-services") version "4.4.4" apply false

    // Ksp plugin
    id("com.google.devtools.ksp") version "2.3.0"

    kotlin("jvm") version "2.3.0" apply false

    // Hilt plugin
    id("com.google.dagger.hilt.android") version "2.57.1" apply false
}