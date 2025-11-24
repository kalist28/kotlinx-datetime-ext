import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinMultiplatform
import org.gradle.kotlin.dsl.*
import java.util.Properties

plugins {
    id("com.vanniktech.maven.publish")
    signing
}

val publishProperties = Properties().apply {
    load(file("publish.properties").inputStream())
}

val isGithubActions = System.getenv("GITHUB_ACTIONS") == "true"

version = System.getenv("VERSION") ?: run {
    if (isGithubActions) error("VERSION must be set for GitHub Actions")
    else "0.0.1"
}

mavenPublishing {
    publishToMavenCentral(automaticRelease = true)
    coordinates(
        groupId = "io.github.kalist28",
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
