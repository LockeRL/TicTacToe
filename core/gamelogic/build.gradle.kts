import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    android {
        namespace = "com.locker.core.gamelogic"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        compilerOptions {
            jvmTarget = JvmTarget.JVM_11
        }
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "GameLogic"
            isStatic = true
        }

        iosTarget.compilations.getByName("main") {
            val nativeBot by cinterops.creating {
                definitionFile.set(project.file("src/nativeInterop/cinterop/native_bot.def"))
                packageName("com.locker.core.gamelogic.native")
                includeDirs(project.file("../nativebot/src/main/cpp"))
            }
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.resources)
            implementation(projects.core.models)

            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.kotlinx.coroutines.core)
        }
        
        androidMain.dependencies {
            implementation(projects.core.nativebot)
        }
    }
}
