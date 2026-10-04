plugins { id("com.android.application") }

android {
    namespace = "com.podolsk.resourcecollector"
    compileSdk = 35
    defaultConfig {
        applicationId = "com.podolsk.resourcecollector"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }
}

dependencies { implementation("androidx.documentfile:documentfile:1.0.1") }
