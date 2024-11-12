package com.example.karibudsl

import com.github.mvysny.vaadinboot.VaadinBoot
import com.vaadin.flow.component.page.AppShellConfigurator

class AppShell: AppShellConfigurator

/**
 * Run this function to launch your app in Embedded Jetty.
 */
fun main() {
    VaadinBoot().run()
}
