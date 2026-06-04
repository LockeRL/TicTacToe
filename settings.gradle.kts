rootProject.name = "TicTacToe"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/patch")
    }
}

include(":androidApp")
include(":shared")
include(":resources")
include(":models")

// Core
include(":core:navigation")

// Feature
include(":feature:core")
include(":feature:component")
include(":feature:colorpicker")
include(":feature:gamescreen")
include(":feature:mainscreen")

