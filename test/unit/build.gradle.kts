plugins {
    id("com.devluque.jvm.library.layer")
}

dependencies {
    implementation(project(":feature:wordDetail:entities"))
    implementation(project(":feature:search:entities"))
    implementation(libs.junit)
    implementation(libs.kotlinx.coroutines.test)
}