plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.devtools.ksp)
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.plugin.compose")



}


android {
    namespace = "com.example.registrationui"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.registrationui"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }



}




dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.accompanist.pager)
    implementation(libs.androidx.benchmark.macro)
    implementation(libs.androidx.fragment.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


    implementation ("com.google.accompanist:accompanist-pager-indicators:0.28.0") // Pager Indicators
    implementation ("androidx.navigation:navigation-compose:2.5.3")
    implementation("io.coil-kt:coil-compose:2.7.0")
    implementation ("com.google.accompanist:accompanist-insets:0.28.0")
    implementation ("com.google.accompanist:accompanist-insets-ui:0.28.0")
    implementation ("androidx.compose.foundation:foundation:1.4.0")

    implementation("androidx.biometric:biometric:1.2.0-alpha05")
    implementation ("androidx.datastore:datastore-preferences:1.0.0")

    implementation ("com.google.accompanist:accompanist-pager:0.30.0") // Latest version as of writing
    implementation ("com.google.accompanist:accompanist-pager-indicators:0.30.0") // For indicators
    implementation ("com.google.code.gson:gson:2.8.8")
    implementation(libs.play.services.location)
    implementation (libs.accompanist.permissions)

    //Dagger Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    //Datastore
    implementation (libs.androidx.datastore.preferences)
    implementation (libs.androidx.navigation.compose)

    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation ("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
    implementation ("androidx.compose.ui:ui-tooling-preview:1.0.0")



}