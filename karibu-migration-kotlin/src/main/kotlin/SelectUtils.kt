package com.github.mvysny.karibumigration

import com.vaadin.flow.component.ItemLabelGenerator
import com.vaadin.flow.component.select.Select

@Deprecated("use setItemLabelGenerator()")
public fun <T> Select<T>.setItemCaptionGenerator(generator: ItemLabelGenerator<T>) {
    itemLabelGenerator = generator
}

@Deprecated("Cannot enable text input on Select, you have to use ComboBox instead")
public fun Select<*>.setTextInputAllowed(allowed: Boolean) {
    if (allowed) {
        throw RuntimeException("Cannot enable text input on Select, you have to use ComboBox instead")
    }
}
