import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinMultiplatform
import java.util.Properties

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.maven.publish)
    signing
}

kotlin {
    jvmToolchain(17)

    androidTarget { publishLibraryVariants("release") }
    jvm()
    js { browser() }
    wasmJs { browser() }
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    macosX64()
    macosArm64()
    linuxX64()
    mingwX64()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.datetime)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }

    }

    //https://kotlinlang.org/docs/native-objc-interop.html#export-of-kdoc-comments-to-generated-objective-c-headers
    targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget> {
        compilations["main"].compileTaskProvider.configure {
            compilerOptions {
                freeCompilerArgs.add("-Xexport-kdoc")
            }
        }
    }

}

android {
    namespace = "io.github.kalist28.datetime.ext"
    compileSdk = 35

    defaultConfig {
        minSdk = 21
    }
}

val publishProperties = Properties().apply {
    load(file("publish.properties").inputStream())
}

val isGithubActions = System.getenv("GITHUB_ACTIONS") == "true"

version = System.getenv("VERSION") ?: run {
    if (isGithubActions) error("VERSION must be set for GitHub Actions")
    else "0.0.1"
}

//Publishing your Kotlin Multiplatform library to Maven Central
//https://www.jetbrains.com/help/kotlin-multiplatform-dev/multiplatform-publish-libraries.html
mavenPublishing {
    publishToMavenCentral(automaticRelease = true)
    coordinates(
        groupId = "io.github.kalist28.datetime.ext",
        artifactId = "kotlinx-datetime-ext",
        version = project.version as String
    )
    configure(
        KotlinMultiplatform(
            javadocJar = JavadocJar.Empty(),
            sourcesJar = true,
        )
    )
    if (isGithubActions) signAllPublications()

    pom {
        name = "kotlinx-datetime-ext"
        description = publishProperties.getProperty("description")
        url = "https://github.com/kalist28/kotlinx-datetime-ext"

        licenses {
            license {
                name = "MIT"
                url = "https://opensource.org/licenses/MIT"
            }
        }

        developers {
            developer {
                id = "kalist28"
                name = "Dmitry Kalistratov"
                email = "kalistratov.d.m@gmail.com"
            }
        }

        scm {
            connection.set("scm:git:https://github.com/kalist28/kotlinx-datetime-ext.git")
            developerConnection.set("scm:git:ssh://github.com/kalist28/kotlinx-datetime-ext.git")
            url.set("https://github.com/kalist28/kotlinx-datetime-ext")
        }
    }
}
