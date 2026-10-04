plugins { id("com.android.application") }

android {
    namespace = "com.podolsk.resourcecollector"
    compileSdk = 35
    defaultConfig { applicationId = "com.podolsk.resourcecollector"; minSdk = 26; targetSdk = 35; versionCode = 2; versionName = "2.0" }
}
dependencies {
    implementation("dev.rikka.shizuku:api:13.1.5")
    implementation("dev.rikka.shizuku:provider:13.1.5")
}
