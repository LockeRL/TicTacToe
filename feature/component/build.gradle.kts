import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
	alias(libs.plugins.kotlinMultiplatform)
	alias(libs.plugins.androidMultiplatformLibrary)
	alias(libs.plugins.composeMultiplatform)
	alias(libs.plugins.composeCompiler)
}

kotlin {
	android {
		namespace = "com.locker.feature.component"
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
			baseName = "Component"
			isStatic = true
		}
	}

	sourceSets {
		commonMain.dependencies {
			implementation(projects.resources)
			implementation(projects.core.navigation)

			// Feature
			implementation(projects.feature.core)
			implementation(projects.core.gamelogic)
			implementation(projects.core.models)

			// Compose
			implementation(libs.compose.runtime)
			implementation(libs.compose.foundation)
			implementation(libs.compose.material3)
			implementation(libs.compose.ui)
			implementation(libs.compose.components.resources)
			implementation(libs.compose.uiToolingPreview)

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

dependencies {
	androidRuntimeClasspath(libs.compose.uiTooling)
}
