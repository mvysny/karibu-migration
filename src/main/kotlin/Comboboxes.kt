package com.github.mvysny.karibumigration

import com.vaadin.flow.component.ItemLabelGenerator
import com.vaadin.flow.component.checkbox.CheckboxGroup
import com.vaadin.flow.component.combobox.ComboBox
import com.vaadin.flow.component.select.Select

@Deprecated("use setItemLabelGenerator()")
public fun <T> ComboBox<T>.setItemCaptionGenerator(generator: ItemLabelGenerator<T>) {
    itemLabelGenerator = generator
}

@Deprecated("use setItemLabelGenerator()")
public fun <T> Select<T>.setItemCaptionGenerator(generator: ItemLabelGenerator<T>) {
    itemLabelGenerator = generator
}

@Deprecated("use setItemLabelGenerator()")
public fun <T> CheckboxGroup<T>.setItemCaptionGenerator(generator: ItemLabelGenerator<T>) {
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
