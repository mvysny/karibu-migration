import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.31"
    `maven-publish`
    signing
}

defaultTasks("clean", "build")

allprojects {
    group = "com.github.mvysny.karibu-migration"
    version = "0.1-SNAPSHOT"

    apply {
        plugin("kotlin")
    }

    repositories {
        mavenCentral()
        maven(url = "https://maven.vaadin.com/vaadin-prereleases/")
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    tasks.withType<Test> {
        useJUnitPlatform()
        testLogging {
            // to see the exceptions of failed tests in Travis-CI console.
            exceptionFormat = TestExceptionFormat.FULL
        }
    }
}

kotlin {
    explicitApi()
}

dependencies {
    api(kotlin("stdlib-jdk8"))
    api("com.github.mvysny.karibu-tools:karibu-tools:0.7")
    // Vaadin 14
    compileOnly("com.vaadin:vaadin-core:14.7.4") {
        // Webjars are only needed when running in Vaadin 13 compatibility mode
        listOf("com.vaadin.webjar", "org.webjars.bowergithub.insites",
                "org.webjars.bowergithub.polymer", "org.webjars.bowergithub.polymerelements",
                "org.webjars.bowergithub.vaadin", "org.webjars.bowergithub.webcomponents")
                .forEach { exclude(group = it) }
    }
    compileOnly("javax.servlet:javax.servlet-api:3.1.0")
    // IDEA language injections
    api("org.jetbrains:annotations:22.0.0")

    testImplementation("com.vaadin:vaadin-core:14.7.4") {
        // Webjars are only needed when running in Vaadin 13 compatibility mode
        listOf("com.vaadin.webjar", "org.webjars.bowergithub.insites",
                "org.webjars.bowergithub.polymer", "org.webjars.bowergithub.polymerelements",
                "org.webjars.bowergithub.vaadin", "org.webjars.bowergithub.webcomponents")
                .forEach { exclude(group = it) }
    }
    testImplementation("com.github.mvysny.dynatest:dynatest:0.22")
    testImplementation("org.slf4j:slf4j-simple:1.7.32")
}

// following https://dev.to/kengotoda/deploying-to-ossrh-with-gradle-in-2020-1lhi
java {
    withJavadocJar()
    withSourcesJar()
}

tasks.withType<Javadoc> {
    isFailOnError = false
}

publishing {
    repositories {
        maven {
            setUrl("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = project.properties["ossrhUsername"] as String? ?: "Unknown user"
                password = project.properties["ossrhPassword"] as String? ?: "Unknown user"
            }
        }
    }
    publications {
        create("mavenJava", MavenPublication::class.java).apply {
            groupId = project.group.toString()
            this.artifactId = "karibu-migration"
            version = project.version.toString()
            pom {
                description.set("Karibu-Migration: Aids migration from Vaadin 8 to Vaadin 14+")
                name.set("Karibu-Migration")
                url.set("https://github.com/mvysny/karibu-migration")
                licenses {
                    license {
                        name.set("The Apache Software License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                        distribution.set("repo")
                    }
                }
                developers {
                    developer {
                        id.set("mavi")
                        name.set("Martin Vysny")
                        email.set("martin@vysny.me")
                    }
                }
                scm {
                    url.set("https://github.com/mvysny/karibu-migration")
                }
            }
            from(components["java"])
        }
    }
}

signing {
    sign(publishing.publications["mavenJava"])
}
