package com.devluque.convention

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

val Project.libs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

fun Project.toAddLayerDependencies(
    modules: List<String>,
    featureName: String
) {
    modules.forEach { module ->
        val moduleProject = project.findProject(":feature:$featureName:$module")
        if (moduleProject != null) {
            dependencies.add("implementation", moduleProject)
        } else {
            throw IllegalStateException("Error: El módulo ':feature:$featureName:$module' no existe.")
        }
    }
}