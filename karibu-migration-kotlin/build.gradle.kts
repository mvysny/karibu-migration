kotlin {
    explicitApi()
}

dependencies {
    api(libs.karibu.tools)
    api(kotlin("stdlib-jdk8"))
    api(project(":karibu-migration-common"))
    // Vaadin
    compileOnly(libs.vaadin.core)
    compileOnly(libs.javax.servletapi)
    // IDEA language injections
    api(libs.jetbrains.annotations)

    testImplementation(libs.vaadin.core)
    testImplementation(libs.junit)
    testImplementation(libs.slf4j.simple)
    testImplementation(libs.karibu.testing)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

val configureMavenCentral = ext["configureMavenCentral"] as (artifactId: String) -> Unit
configureMavenCentral("karibu-migration-kotlin")
