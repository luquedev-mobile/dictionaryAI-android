plugins {
    id("com.devluque.jvm.library")
    alias(libs.plugins.kotlinxSerialization)
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
}