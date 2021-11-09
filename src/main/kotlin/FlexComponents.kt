package com.github.mvysny.karibumigration

import com.vaadin.flow.component.Component
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout

@Deprecated("Use either expand() or setFlexGrow()")
public fun FlexComponent<*>.setExpandRatio(child: Component, expandRatio: Float) {
    if (expandRatio == 1f) {
        expand(child)
    } else {
        setFlexGrow(expandRatio.toDouble(), child)
    }
}

@Deprecated("Use addAndExpand()", replaceWith = ReplaceWith("addAndExpand(children)"))
public fun HorizontalLayout.addComponentsAndExpand(vararg children: Component) {
    addAndExpand(*children)
}

@Deprecated("Use addAndExpand()", replaceWith = ReplaceWith("addAndExpand(children)"))
public fun VerticalLayout.addComponentsAndExpand(vararg children: Component) {
    addAndExpand(*children)
}
