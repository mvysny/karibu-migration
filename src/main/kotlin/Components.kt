package com.github.mvysny.karibumigration

import com.vaadin.flow.component.HasSize

@Deprecated("use setWidth(null)", replaceWith = ReplaceWith("setWidth(null)"))
public fun HasSize.setWidthUndefined() {
    width = null
}

@Deprecated("use setHeight(null)", replaceWith = ReplaceWith("setHeight(null)"))
public fun HasSize.setHeightUndefined() {
    height = null
}
