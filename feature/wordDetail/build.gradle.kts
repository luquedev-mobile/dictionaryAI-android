plugins {
    id("com.devluque.android.feature.layer.dependencies")
    id("com.devluque.di.library.compose")
}

android {
    namespace = "com.devluque.worddetail"
}

dependencies {
    testImplementation(project(":test:unit"))
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)
}
