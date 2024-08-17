plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.jetbrainsKotlinKsp)
    alias(libs.plugins.hiltPlugin)
}

android {
    namespace = "com.example.roleapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.roleapp"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.coil.compose)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation (libs.androidx.runtime.livedata)
    implementation (libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation (libs.androidx.paging.runtime.ktx)
    implementation (libs.paging.compose)
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    ksp(libs.dagger.compiler)
    ksp(libs.hilt.compiler)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation (libs.androidx.room.runtime)
    ksp (libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    implementation ("androidx.security:security-crypto:1.1.0-alpha03")


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0")
    testImplementation ("androidx.paging:paging-common:3.1.0")
    testImplementation ("org.mockito:mockito-core:3.9.0")
    testImplementation ("org.mockito.kotlin:mockito-kotlin:3.2.0")
    testImplementation ("androidx.security:security-crypto:1.0.0")
    testImplementation ("androidx.room:room-testing:2.3.0")
    testImplementation ("androidx.room:room-testing:2.3.0")
    testImplementation ("androidx.arch.core:core-testing:2.1.0")
    testImplementation ("org.mockito:mockito-inline:4.0.0")
    testImplementation ("com.squareup.okhttp3:mockwebserver:4.9.3")
    testImplementation ("androidx.paging:paging-common-ktx:3.1.1")
    testImplementation ("org.robolectric:robolectric:4.10")
}