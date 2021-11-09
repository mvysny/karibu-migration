package com.github.mvysny.karibumigration

import com.github.mvysny.kaributools.addClassNames2
import com.github.mvysny.kaributools.label
import com.github.mvysny.kaributools.tooltip
import com.vaadin.flow.component.Component
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.HasSize
import com.vaadin.flow.component.HasStyle
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

/**
 * Warning: in order for the caption to be visible, the component must be nested in `VerticalLayout`/`HorizontalLayout`/`FlexLayout`.
 */
@Deprecated("use setLabel", replaceWith = ReplaceWith("label", "com.github.mvysny.kaributools.label"))
public fun Component.setCaption(caption: String?) {
    label = caption ?: ""
}

/**
 * Warning: in order for the caption to be visible, the component must be nested in `VerticalLayout`/`HorizontalLayout`/`FlexLayout`.
 */
@Deprecated("use setLabel", replaceWith = ReplaceWith("label", "com.github.mvysny.kaributools.label"))
public fun Component.getCaption(): String = label

@Deprecated("use tooltip", replaceWith = ReplaceWith("tooltip", "com.github.mvysny.kaributools.tooltip"))
public var Component.description: String?
    get() = tooltip
    set(value) {
        tooltip = value
    }

@Deprecated("use addClassNames2()", replaceWith = ReplaceWith("addClassNames2(style)", "com.github.mvysny.kaributools.addClassNames2"))
public fun HasStyle.addStyleName(style: String) {
    addClassNames2(style)
}

@Deprecated("use addClassNames()", replaceWith = ReplaceWith("addClassNames(style)"))
public fun HasStyle.addStyleNames(vararg style: String) {
    style.forEach { addClassNames2(it) }
}

@Deprecated("use addClassNames()")
public fun HasStyle.setStyleName(style: String) {
    this.style.clear()
    addClassNames2(style)
}

@Deprecated("use removeClassName()", replaceWith = ReplaceWith("removeClassName(style)"))
public fun HasStyle.removeStyleName(style: String) {
    removeClassName(style)
}

@Deprecated("use removeClassNames()", replaceWith = ReplaceWith("removeClassNames(style)"))
public fun HasStyle.removeStyleNames(vararg style: String) {
    removeClassNames(*style)
}

@Deprecated("use removeAll()", replaceWith = ReplaceWith("removeAll()"))
public fun HasComponents.removeAllComponents() {
    removeAll()
}

@Deprecated("use add()", replaceWith = ReplaceWith("add()"))
public fun HasComponents.addComponent(child: Component) {
    add(child)
}

@Deprecated("use remove()", replaceWith = ReplaceWith("remove()"))
public fun HasComponents.removeComponent(child: Component) {
    remove(child)
}
