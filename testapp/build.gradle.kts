// The Beverage Buddy sample project ported to Kotlin.
// Original project: https://github.com/vaadin/beverage-starter-flow

plugins {
    id("org.gretty")
    war
    id("com.vaadin")
}

gretty {
    contextPath = "/"
    servletContainer = "jetty9.4"
}

dependencies {
    implementation(project(":karibu-migration-kotlin"))
    // Vaadin 14
    implementation(libs.vaadin.v14.core) {
        // Webjars are only needed when running in Vaadin 13 compatibility mode
        listOf("com.vaadin.webjar", "org.webjars.bowergithub.insites",
                "org.webjars.bowergithub.polymer", "org.webjars.bowergithub.polymerelements",
                "org.webjars.bowergithub.vaadin", "org.webjars.bowergithub.webcomponents")
                .forEach { exclude(group = it) }
    }
    providedCompile(libs.javax.servletapi)
    implementation(libs.slf4j.simple)

    testImplementation(libs.karibu.testing)
    testImplementation(libs.dynatest)
}

