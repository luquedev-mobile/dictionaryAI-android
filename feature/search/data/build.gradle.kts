plugins {
    id("com.devluque.jvm.library.layer")
    id("com.devluque.di.library")
}

dependencies {
    testImplementation(project(":test:unit"))
}

