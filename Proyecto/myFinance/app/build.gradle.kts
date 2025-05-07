plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.androidx.room)
}

android {
    namespace = "com.example.myfinance"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.myfinance"
        minSdk = 29
        targetSdk = 35
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

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }

    buildToolsVersion = "35.0.0"

    // If you need to explicitly set the Compose compiler extension, you can hardcode it here:
    // composeOptions {
    //     kotlinCompilerExtensionVersion = "1.4.0"
    // }
}

dependencies {
    // Coroutines
    implementation(libs.kotlinx.coroutines.android)

    // Material Icons
    implementation(libs.androidx.material.icons.extended)

    // Room / SQLite
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.benchmark.macro)
    ksp(libs.androidx.room.compiler)

    // Navigation Compose
    implementation(libs.navigation.compose)

    // Lifecycle (ViewModel, LiveData)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Data Binding adapters (if used)
    implementation(libs.androidx.databinding.adapters)

    // Coil for image loading
    implementation(libs.coil.compose)

    // Compose BOM and core dependencies
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.foundation)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.material3)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // Debug
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

room {
    schemaDirectory("$projectDir/schemas")
}
