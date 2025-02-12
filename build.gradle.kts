// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.1.0" apply false
    id("com.google.devtools.ksp") version "2.1.0-1.0.29" apply false
}


buildscript {
    extra.apply {
        set("room_version", "2.6.1")
        set("retrofit_version", "2.9.0")
        set("nav_version", "2.8.4")
        set("okhttp_version", "4.11.0")
    }
}