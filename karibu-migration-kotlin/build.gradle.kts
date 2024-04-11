kotlin {
    explicitApi()
}

dependencies {
    api(libs.karibu.tools)
    api(kotlin("stdlib-jdk8"))
    api(project(":karibu-migration-common"))
    // Vaadin 14
    compileOnly(libs.vaadin.v14.core) {
        // Webjars are only needed when running in Vaadin 13 compatibility mode
        listOf("com.vaadin.webjar", "org.webjars.bowergithub.insites",
                "org.webjars.bowergithub.polymer", "org.webjars.bowergithub.polymerelements",
                "org.webjars.bowergithub.vaadin", "org.webjars.bowergithub.webcomponents")
                .forEach { exclude(group = it) }
    }
    compileOnly(libs.javax.servletapi)
    // IDEA language injections
    api(libs.jetbrains.annotations)

    testImplementation(libs.vaadin.v14.core) {
        // Webjars are only needed when running in Vaadin 13 compatibility mode
        listOf("com.vaadin.webjar", "org.webjars.bowergithub.insites",
                "org.webjars.bowergithub.polymer", "org.webjars.bowergithub.polymerelements",
                "org.webjars.bowergithub.vaadin", "org.webjars.bowergithub.webcomponents")
                .forEach { exclude(group = it) }
    }
    testImplementation(libs.dynatest)
    testImplementation(libs.slf4j.simple)
    testImplementation(libs.karibu.testing)
}

val configureMavenCentral = ext["configureMavenCentral"] as (artifactId: String) -> Unit
configureMavenCentral("karibu-migration-kotlin")
