package com.github.mvysny.karibumigration

import com.vaadin.flow.component.ItemLabelGenerator
import com.vaadin.flow.component.combobox.ComboBox

@Deprecated("use setItemLabelGenerator()")
public fun <T> ComboBox<T>.setItemCaptionGenerator(generator: ItemLabelGenerator<T>) {
    itemLabelGenerator = generator
}

/**
 * When [allowed] was set to true, the Vaadin 8 ComboBox used to show an additional item
 * representing the `null` value. This is hard to emulate via DataProvider; the
 * easiest replacement is to simply show or hide the clear button.
 *
 * Also see [Adding support for null values to Vaadin ComboBox](https://mvysny.github.io/vaadin-combobox-null-value/)
 * for more details.
 */
@Deprecated("use setClearButtonVisible()")
public fun ComboBox<*>.setEmptySelectionAllowed(allowed: Boolean) {
    isClearButtonVisible = allowed
}

@Deprecated("use placeholder")
public fun ComboBox<*>.setEmptySelectionCaption(caption: String) {
    placeholder = caption
}

@Deprecated("Cannot disable text input on ComboBox, you have to use Select instead")
public fun ComboBox<*>.setTextInputAllowed(allowed: Boolean) {
    if (!allowed) {
        throw RuntimeException("Cannot disable text input on ComboBox, you have to use Select instead")
    }
}
