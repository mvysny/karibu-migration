dependencies {
    api("com.github.mvysny.karibu-tools:karibu-tools:0.7")
    // Vaadin 14
    compileOnly("com.vaadin:vaadin-core:${properties["vaadin14_version"]}") {
        // Webjars are only needed when running in Vaadin 13 compatibility mode
        listOf("com.vaadin.webjar", "org.webjars.bowergithub.insites",
                "org.webjars.bowergithub.polymer", "org.webjars.bowergithub.polymerelements",
                "org.webjars.bowergithub.vaadin", "org.webjars.bowergithub.webcomponents")
                .forEach { exclude(group = it) }
    }
    compileOnly("javax.servlet:javax.servlet-api:3.1.0")
    // IDEA language injections
    api("org.jetbrains:annotations:22.0.0")

    testImplementation("com.vaadin:vaadin-core:${properties["vaadin14_version"]}") {
        // Webjars are only needed when running in Vaadin 13 compatibility mode
        listOf("com.vaadin.webjar", "org.webjars.bowergithub.insites",
                "org.webjars.bowergithub.polymer", "org.webjars.bowergithub.polymerelements",
                "org.webjars.bowergithub.vaadin", "org.webjars.bowergithub.webcomponents")
                .forEach { exclude(group = it) }
    }
    testImplementation("com.github.mvysny.dynatest:dynatest:${properties["dynatest_version"]}")
    testImplementation("org.slf4j:slf4j-simple:${properties["slf4j_version"]}")
    testImplementation("com.github.mvysny.kaributesting:karibu-testing-v10:${properties["kaributesting_version"]}")
}

val configureMavenCentral = ext["configureMavenCentral"] as (artifactId: String) -> Unit
configureMavenCentral("karibu-migration-common")
