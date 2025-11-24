plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.android.library)
    id("publishing-convention")
    id("multiplatform-module-convention")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":kotlinx-datetime-ext-translater:core"))
        }

        commonTest.dependencies {
            //implementation(libs.kotlinx.datetime)
        }
    }
}
