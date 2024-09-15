import com.devluque.convention.libs
import com.devluque.convention.toAddLayerDependencies
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

class AndroidFeatureWithLayerDependenciesConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.devluque.android.library.compose")
            pluginManager.apply("org.jetbrains.kotlin.plugin.serialization")

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
            }
        }
    }
}