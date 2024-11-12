plugins {
    application
    alias(libs.plugins.vaadin)
}

dependencies {
    implementation(project(":karibu-migration-kotlin"))
    // Vaadin
    implementation(libs.vaadin.core)
    implementation(libs.slf4j.simple)
    implementation(libs.vaadin.boot)

    testImplementation(libs.karibu.testing)
    testImplementation(libs.dynatest)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
