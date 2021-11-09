package com.github.mvysny.karibumigration

import com.vaadin.flow.component.Component
import com.vaadin.flow.component.orderedlayout.FlexComponent

@Deprecated("Use either expand() or setFlexGrow()")
public fun FlexComponent<*>.setExpandRatio(child: Component, expandRatio: Float) {
    if (expandRatio == 1f) {
        expand(child)
    } else {
        setFlexGrow(expandRatio.toDouble(), child)
    }
}
