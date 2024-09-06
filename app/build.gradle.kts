plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.jetbrains.kotlin.android)
//    alias(libs.plugins.devtools.ksp)
//    alias(libs.plugins.room)
}

android {
    namespace = Android.DefaultConfig.applicationId
    compileSdk = Android.DefaultConfig.compileSdk

    defaultConfig {
        applicationId = Android.DefaultConfig.applicationId
        minSdk = Android.DefaultConfig.minSdk
        targetSdk = Android.DefaultConfig.targetSdk
        versionCode = Android.DefaultConfig.versionCode
        versionName = Android.DefaultConfig.versionName

        testInstrumentationRunner = Android.DefaultConfig.testInstrumentationRunner

//        room {
//            schemaDirectory("$projectDir/schemas")
//        }

        vectorDrawables {
            useSupportLibrary = Android.DefaultConfig.VectorDrawables.useSupportLibrary
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = Android.BuildTypes.isMinifyEnabled
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = Android.CompileOptions.sourceCompatibility
        targetCompatibility = Android.CompileOptions.targetCompatibility
    }
    kotlinOptions {
        jvmTarget = Android.KotlinOptions.jvmTarget
    }
    buildFeatures {
        compose = Android.BuildFeatures.composeState
        buildConfig = Android.BuildFeatures.buildConfig
        viewBinding = Android.BuildFeatures.viewBindingState
        dataBinding = Android.BuildFeatures.dataBindingState
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Android.ComposeOptions.kotlinCompilerExtensionVersion
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)

    // Koin DI
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.android.compose)

    // Room
//    implementation(libs.room)
//    implementation(libs.room.ktx)
//    ksp(libs.room.compiler)

    // Material
    implementation(libs.ui.compose.material)

    // Navigation
    implementation(libs.ui.compose.navigation)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
