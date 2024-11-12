// The Beverage Buddy sample project ported to Kotlin.
// Original project: https://github.com/vaadin/beverage-starter-flow

plugins {
    id("org.gretty")
    war
    alias(libs.plugins.vaadin)
}

gretty {
    contextPath = "/"
    servletContainer = "jetty9.4"
}

dependencies {
    implementation(project(":karibu-migration-kotlin"))
    // Vaadin
    implementation(libs.vaadin.core)
    providedCompile(libs.javax.servletapi)
    implementation(libs.slf4j.simple)

    testImplementation(libs.karibu.testing)
    testImplementation(libs.dynatest)
}

