import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android);
    id("kotlin-kapt")
    id("com.google.gms.google-services")
}

val properties = Properties()
properties.load(project.rootProject.file("local.properties").inputStream())

val googleMapsKey = properties["google_maps_key"]?.toString()
    ?: throw GradleException("google_maps_key is missing in local.properties")

val admobAppId = properties["admob_app_id"]?.toString()
    ?: throw GradleException("admob_app_id is missing in local.properties")

val admobBannerId = properties["admob_banner_id"]?.toString()
    ?: throw GradleException("admob_banner_id is missing in local.properties")

val admobFullScreenId = properties["admob_fullscreen_id"]?.toString()
    ?: throw GradleException("admob_fullscreen_id is missing in local.properties")

android {
    namespace = "com.kuki.airquality"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.kuki.airquality"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "IQAIR_KEY", properties["iqair_key"].toString())
        resValue("string", "admob_app_id", admobAppId)
        resValue("string", "admob_banner_id", admobBannerId)
        resValue("string", "admob_fullscreen_id", admobFullScreenId)
//        buildConfigField("String", "GOOGLE_MAPS_KEY", properties["google_maps_key"].toString())
        manifestPlaceholders["GOOGLE_MAPS_KEY"] = googleMapsKey
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    viewBinding {
        enable = true
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    //Google Map
    implementation("com.google.android.gms:play-services-maps:19.2.0")
    implementation("com.google.android.gms:play-services-location:21.3.0")

    //firebase
    implementation(platform("com.google.firebase:firebase-bom:33.14.0"))
    implementation("com.google.firebase:firebase-analytics")

    //admob
    implementation("com.google.android.gms:play-services-ads:24.3.0")
}