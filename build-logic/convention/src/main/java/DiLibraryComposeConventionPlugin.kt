import com.devluque.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project

class DiLibraryComposeConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.devluque.di.library")
                apply("dagger.hilt.android.plugin")
            }
            dependencies.add("implementation", libs.findLibrary("hilt.android").get())
            dependencies.add("implementation", libs.findLibrary("androidx.hilt.navigation.compose").get())
        }
    }
}