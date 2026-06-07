import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
	alias(libs.plugins.kotlinMultiplatform)
	alias(libs.plugins.androidMultiplatformLibrary)
	alias(libs.plugins.composeCompiler)
	alias(libs.plugins.composeMultiplatform)
}

kotlin {
	android {
		namespace = "com.locker.feature.colorpicker"
		compileSdk = libs.versions.android.compileSdk.get().toInt()
		minSdk = libs.versions.android.minSdk.get().toInt()

		compilerOptions {
			jvmTarget = JvmTarget.JVM_11
		}
		androidResources {
			enable = true
		}
	}

	listOf(
		iosArm64(),
		iosSimulatorArm64()
	).forEach { iosTarget ->
		iosTarget.binaries.framework {
			baseName = "FeatureColorpicker"
			isStatic = true
		}
	}

	sourceSets {
		commonMain.dependencies {
			implementation(projects.core.navigation)
			implementation(projects.core.database)

			// Feature
			implementation(projects.feature.core)
			implementation(projects.feature.component)

			// Compose
			implementation(libs.compose.runtime)
			implementation(libs.compose.foundation)
			implementation(libs.compose.material3)
			implementation(libs.compose.ui)
			implementation(libs.compose.uiToolingPreview)
			implementation(libs.compose.components.resources)

			// Koin
			implementation(libs.koin.core)
			implementation(libs.koin.compose.core)
			implementation(libs.koin.compose.viewmodel)
			implementation(libs.koin.compose.navigation)
		}

		commonTest.dependencies {
			implementation(libs.kotlin.test)
		}
	}
}

 compose.resources {
 	packageOfResClass = "com.locker.feature.colorpicker"
 }

dependencies {
	androidRuntimeClasspath(libs.compose.uiTooling)
}
