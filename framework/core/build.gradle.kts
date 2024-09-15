plugins {
    id("com.devluque.android.library")
    id("com.devluque.android.room")
    id("com.devluque.jvm.retrofit")
}

android {
    namespace = "com.devluque.core"
}

dependencies {
    implementation(project(":domain"))
}