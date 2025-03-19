import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

    id("kotlin-kapt")
}

android {
    namespace = "com.sougata.movieworld"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.sougata.movieworld"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables.useSupportLibrary = true

        val properties = Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())

        buildConfigField("String", "MOVIES_DATABASE_API_KEY", properties.getProperty("MOVIES_DATABASE_API_KEY"))
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
    buildFeatures {
        buildConfig = true // important to generate buildconfig (Used to hide api key)
        dataBinding = true
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

    val retrofitVersion = "2.11.0"

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")

    // Gson
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")

    // Ok HTTP logging interceptor
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // Coroutine for other tasks not for room
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")

    val lifecycleVersion = "2.8.7"

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")

    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")

    // Annotation processor for viewmodel, lifecycle and livedata
    kapt("androidx.lifecycle:lifecycle-compiler:$lifecycleVersion")

    val roomVersion = "2.6.1"

    // Room database
    implementation("androidx.room:room-runtime:$roomVersion")

    // Annotation processor for room
    kapt("androidx.room:room-compiler:$roomVersion")

    // Coroutine for room
    implementation("androidx.room:room-ktx:$roomVersion")

    val navVersion = "2.8.4"

    // This 2 are needed for fragment navigation
    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")

    // Circle iamage view
    implementation ("de.hdodenhof:circleimageview:3.1.0")

    // Glid
    implementation("com.github.bumptech.glide:glide:4.16.0")

    // Shimmer effect
    implementation("com.facebook.shimmer:shimmer:0.5.0")

    // Animated recycler view
    implementation("jp.wasabeef:recyclerview-animators:4.0.2")

    // Fake data generator
    implementation("com.github.javafaker:javafaker:1.0.2")

    // Material
    implementation("com.google.android.material:material:1.9.0")
}