import com.devluque.convention.libs
import com.devluque.convention.toAddLayerDependencies
import org.gradle.api.Plugin
import org.gradle.api.Project

class LibraryLayerConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.devluque.jvm.library")
            when {
                project.path.endsWith(":data") -> {
                    toAddLayerDependencies(
                        listOf("entities"),
                        project.path.substringAfter(":feature:").substringBefore(":")
                    )
                    dependencies.add("implementation", libs.findLibrary("kotlinx-coroutines-core").get())
                }
                project.path.endsWith(":usecases") -> {
                    toAddLayerDependencies(
                        listOf("entities", "data"),
                        project.path.substringAfter(":feature:").substringBefore(":")
                    )
                    dependencies.add("implementation", libs.findLibrary("kotlinx-coroutines-core").get())
                }
                project.path.endsWith(":entities") -> {
                    pluginManager.apply("org.jetbrains.kotlin.plugin.serialization")
                    dependencies.add("implementation", libs.findLibrary("kotlinx.serialization.json").get())
                }
            }
            dependencies.add("implementation", project(":domain"))
        }
    }
}
