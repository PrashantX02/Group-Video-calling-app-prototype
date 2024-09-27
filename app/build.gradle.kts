plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.videocalling_prototype"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.videocalling_prototype"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    viewBinding{
        enable = true
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    implementation("com.google.firebase:firebase-bom:32.2.0")
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-database-ktx")
    implementation("com.google.firebase:firebase-storage-ktx")


    implementation("com.github.ZEGOCLOUD:zego_uikit_prebuilt_call_android:+")


    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)

    androidTestImplementation(libs.androidx.espresso.core)


    // Firebase dependencies
    implementation("com.google.firebase:firebase-auth-ktx:23.0.0")  // Replace with the latest version if necessary
    implementation("com.google.firebase:firebase-database-ktx:21.0.0")  // Replace with the latest version
    implementation("com.google.firebase:firebase-storage-ktx:21.0.0")  // Replace with the latest version


    implementation ("com.github.ZEGOCLOUD:zego_uikit_prebuilt_call_android:+")
// Networking dependencies (Optional for signaling server)
    implementation("com.squareup.okhttp3:okhttp:4.9.1")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")

}