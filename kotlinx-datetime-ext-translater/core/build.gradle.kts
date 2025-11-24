
plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.android.library)
    id("publishing-convention")
    id("multiplatform-module-convention")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.kotlinx.datetime)
        }

        commonTest.dependencies {
            api(libs.kotlinx.datetime)
        }
    }
}


