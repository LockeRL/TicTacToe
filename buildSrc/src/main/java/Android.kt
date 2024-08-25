import org.gradle.api.JavaVersion

object Android {
    object CompileOptions {
        val sourceCompatibility = JavaVersion.VERSION_1_8
        val targetCompatibility = JavaVersion.VERSION_1_8
    }

    object DefaultConfig {
        const val targetSdk = 34
        const val compileSdk = 34
        const val minSdk = 24
        const val versionCode = 1
        const val versionName = "1.0"
        const val applicationId = "com.locker.tictactoe"
        const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        object VectorDrawables {
            const val useSupportLibrary = true
        }
    }

    object BuildTypes {
        const val isMinifyEnabled = false
    }

    object BuildFeatures {
        const val dataBindingState = false
        const val viewBindingState = false
        const val composeState = true
        const val buildConfig = true
    }

    object ComposeOptions {
        const val kotlinCompilerExtensionVersion = "1.5.1"
    }

    object KotlinOptions {
        const val jvmTarget = "1.8"
    }
}
