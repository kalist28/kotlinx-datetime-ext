rootProject.name = "kotlinx-datetime-ext"

pluginManagement {
    repositories {
        google {
            content { 
              	includeGroupByRegex("com\\.android.*")
              	includeGroupByRegex("com\\.google.*")
              	includeGroupByRegex("androidx.*")
              	includeGroupByRegex("android.*")
            }
        }
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            content { 
              	includeGroupByRegex("com\\.android.*")
              	includeGroupByRegex("com\\.google.*")
              	includeGroupByRegex("androidx.*")
              	includeGroupByRegex("android.*")
            }
        }
        mavenCentral()
    }
}
includeBuild("build-logic")

include(":kotlinx-datetime-ext")
include(":kotlinx-datetime-ext-translater")
include(":kotlinx-datetime-ext-translater:core")
include(":kotlinx-datetime-ext-translater:ru")
include(":sample:composeApp")
include(":sample:terminalApp")

