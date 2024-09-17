import java.util.Properties

plugins {
    id("com.devluque.android.application")
    id("com.devluque.android.application.compose")
    id("com.devluque.di.library.compose")
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.devluque.dictionaryai"

    defaultConfig {
        applicationId = "com.devluque.dictionaryai"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        val properties = Properties()
        properties.load(project.rootProject.file("local.properties").readText().byteInputStream())

        val apiKey = properties.getProperty("API_KEY", "")
        buildConfigField("String", "API_KEY", "\"$apiKey\"")
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
    buildFeatures {
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":feature:wordDetail:entities"))
    implementation(project(":feature:wordDetail"))
    implementation(project(":feature:common"))
    implementation(project(":feature:wordDetail:data"))
    implementation(project(":feature:wordDetail:usecases"))
    implementation(project(":framework:core"))
    implementation(project(":feature:search:data"))
    implementation(project(":feature:search"))
    implementation(project(":feature:search:usecases"))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.room.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.system.ui.controller)
}