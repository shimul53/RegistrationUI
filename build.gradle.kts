plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.google.devtools.ksp) apply false
}

buildscript {
    dependencies {
        classpath(libs.compose.compiler.gradle.plugin)
        classpath(libs.hilt.android.gradle.plugin)
    }
}