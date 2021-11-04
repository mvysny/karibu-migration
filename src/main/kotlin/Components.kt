package com.github.mvysny.karibumigration

import com.vaadin.flow.component.Component
import com.vaadin.flow.component.HasSize
import com.vaadin.flow.component.html.Div

@Deprecated("use setWidth(null)", replaceWith = ReplaceWith("setWidth(null)"))
public fun HasSize.setWidthUndefined() {
    width = null
}

@Deprecated("use setHeight(null)", replaceWith = ReplaceWith("setHeight(null)"))
public fun HasSize.setHeightUndefined() {
    height = null
}

@Deprecated("use Div")
public class CssLayout(vararg components: Component) : Div(*components)
