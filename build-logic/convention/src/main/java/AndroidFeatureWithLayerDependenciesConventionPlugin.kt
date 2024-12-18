import com.android.build.gradle.LibraryExtension
import com.devluque.convention.libs
import com.devluque.convention.toAddLayerDependencies
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

class AndroidFeatureWithLayerDependenciesConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.devluque.android.library.compose")
            pluginManager.apply("org.jetbrains.kotlin.plugin.serialization")

            extensions.configure<LibraryExtension> {
                defaultConfig {
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }
            }

            toAddLayerDependencies(
                listOf("data", "entities", "usecases"),
                project.path.substringAfter(":feature:")
            )

            // Añade dependencias comunes
            dependencies {
                add("implementation", project(":feature:common"))
                add("implementation", libs.findLibrary("androidx.lifecycle.viewmodel.compose").get())
                add("implementation", project(":domain"))
                add("implementation", project(":framework:core"))
                add("implementation", libs.findLibrary("retrofit.converter.kotlinx.serialization").get())
                add("implementation", libs.findLibrary("kotlinx.serialization.json").get())

                add("androidTestImplementation", project(":test:unit"))
                add("androidTestImplementation", libs.findLibrary("androidx.compose.bom").get())
                add("androidTestImplementation", libs.findLibrary("androidx.junit").get())
                add("androidTestImplementation", libs.findLibrary("androidx.espresso.core").get())
                add("androidTestImplementation", libs.findLibrary("androidx.ui.test.junit4").get())
                add("androidTestImplementation", libs.findLibrary("androidx.test.core").get())
                add("androidTestImplementation", libs.findLibrary("androidx.test.rules").get())
                add("debugImplementation", libs.findLibrary("androidx.ui.test.manifest").get())
            }
        }
    }
}