dependencies {
    // Vaadin
    compileOnly(libs.vaadin.core)
    compileOnly(libs.javax.servletapi)
    // IDEA language injections
    api(libs.jetbrains.annotations)

    testImplementation(kotlin("stdlib-jdk8"))
    testImplementation(libs.vaadin.core)
    testImplementation(libs.dynatest)
    testImplementation(libs.slf4j.simple)
    testImplementation(libs.karibu.testing)
}

val configureMavenCentral = ext["configureMavenCentral"] as (artifactId: String) -> Unit
configureMavenCentral("karibu-migration-common")
