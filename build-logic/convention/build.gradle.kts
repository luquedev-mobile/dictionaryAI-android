plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "com.devluque.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidApplicationCompose") {
            id = "com.devluque.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "com.devluque.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "com.devluque.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidFeatureWithLayerDependencies") {
            id = "com.devluque.android.feature.layer.dependencies"
            implementationClass = "AndroidFeatureWithLayerDependenciesConventionPlugin"
        }
        register("androidFeature") {
            id = "com.devluque.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("jvmLibrary") {
            id = "com.devluque.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }
        register("libraryLayer") {
            id = "com.devluque.jvm.library.layer"
            implementationClass = "LibraryLayerConventionPlugin"
        }
        register("androidRoom") {
            id = "com.devluque.android.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }
        register("jvmRetrofit") {
            id = "com.devluque.jvm.retrofit"
            implementationClass = "JvmRetrofitConventionPlugin"
        }
        register("diLibrary") {
            id = "com.devluque.di.library"
            implementationClass = "DiLibraryConventionPlugin"
        }
        register("diLibraryCompose") {
            id = "com.devluque.di.library.compose"
            implementationClass = "DiLibraryComposeConventionPlugin"
        }
    }
}