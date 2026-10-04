plugins { id("com.android.application") }
android {
 namespace = "com.podolsk.resourcecollector"; compileSdk = 35
 defaultConfig { applicationId = "com.podolsk.resourcecollector"; minSdk = 26; targetSdk = 35; versionCode = 3; versionName = "3.0" }
 buildFeatures { aidl = true }
}
dependencies {
 implementation("dev.rikka.shizuku:api:13.1.5")
 implementation("dev.rikka.shizuku:provider:13.1.5")
 implementation("androidx.annotation:annotation:1.9.1")
}
