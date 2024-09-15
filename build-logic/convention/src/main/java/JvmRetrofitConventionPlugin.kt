
import com.devluque.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class JvmRetrofitConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            dependencies {
                add("implementation", libs.findLibrary("okhttp").get())
                add("implementation", libs.findLibrary("retrofit").get())
                add("implementation", libs.findLibrary("retrofit.converter.kotlinx.serialization").get())
                add("implementation", libs.findLibrary("kotlinx.serialization.json").get())
            }
        }
    }
}