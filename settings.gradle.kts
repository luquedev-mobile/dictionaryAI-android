pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "dictionaryAI"
include(":app")
include(":domain")
include(":feature")
include(":feature:search")
include(":feature:wordDetail")
include(":feature:common")
include(":framework")
include(":feature:wordDetail:entities")
include(":feature:wordDetail:data")
include(":feature:wordDetail:usecases")
include(":framework:core")
include(":feature:search:data")
include(":feature:search:entities")
include(":feature:search:usecases")
