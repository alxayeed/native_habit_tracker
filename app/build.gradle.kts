import java.util.Properties
import java.io.FileInputStream
import java.io.File


plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

fun loadProperties(projectRootDir: File): Properties {
    val properties = Properties()
    val localPropertiesFile = File(projectRootDir, "local.properties")
    if (localPropertiesFile.isFile) {
        try {
            FileInputStream(localPropertiesFile).use { properties.load(it) }
        } catch (e: Exception) {
            println("Warning: Could not load local.properties file: ${e.message}")
        }
    } else {
        println("Warning: local.properties file not found at ${localPropertiesFile.absolutePath}")
    }
    return properties
}


val localProperties = loadProperties(rootProject.rootDir)
println("Loaded properties: ${localProperties.keys}")

android {
    namespace = "com.example.habittracker"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.habittracker"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true

        val supabaseUrl = localProperties.getProperty("supabase.url", "")
        val supabaseAnonKey = localProperties.getProperty("supabase.anon.key", "")

        if (supabaseUrl.isEmpty() || supabaseAnonKey.isEmpty()) {
            println("Warning: Supabase URL or Anon Key not found in local.properties. BuildConfig fields will be empty.")
        }

        buildConfigField("String", "SUPABASE_URL", "\"$supabaseUrl\"")
        buildConfigField("String", "SUPABASE_ANON_KEY", "\"$supabaseAnonKey\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

        }
        debug {
        }
    }
    compileOptions {

        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {

        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
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
}