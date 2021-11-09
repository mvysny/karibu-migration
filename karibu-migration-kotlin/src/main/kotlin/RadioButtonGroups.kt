package com.github.mvysny.karibumigration

import com.github.mvysny.kaributools.setItemLabelGenerator
import com.vaadin.flow.component.ItemLabelGenerator
import com.vaadin.flow.component.radiobutton.RadioButtonGroup

// workaround for https://github.com/vaadin/flow-components/issues/1681
@Deprecated("use setItemLabelGenerator() from karibu-tools", replaceWith = ReplaceWith("setItemLabelGenerator(generator)", "com.github.mvysny.kaributools.setItemLabelGenerator"))
public fun <T> RadioButtonGroup<T>.setItemCaptionGenerator(generator: ItemLabelGenerator<T>) {
    if (this is RadioButtonGroupCompat) {
        setItemLabelGenerator(generator)
    } else {
        setItemLabelGenerator(generator)
    }
}

@Deprecated("Use RadioButtonGroupCompat directly")
public fun <T> RadioButtonGroup<T>.setItemDescriptionGenerator(generator: ItemLabelGenerator<T>) {
    (this as RadioButtonGroupCompat<T>).setItemDescriptionGenerator(generator)
}

/**
 * Mimicks the RadioButtonGroup(String, Collection) constructor, but creates the [RadioButtonGroupCompat] class which offers
 * better compatibility.
 */
public fun <T> RadioButtonGroup(caption: String?, items: Collection<T>): RadioButtonGroupCompat<T> = RadioButtonGroupCompat(caption, items)
