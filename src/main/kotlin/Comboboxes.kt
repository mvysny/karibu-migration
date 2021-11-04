package com.github.mvysny.karibumigration

import com.vaadin.flow.component.ItemLabelGenerator
import com.vaadin.flow.component.combobox.ComboBox

@Deprecated("use setItemLabelGenerator()")
public fun <T> ComboBox<T>.setItemCaptionGenerator(generator: ItemLabelGenerator<T>) {
    itemLabelGenerator = generator
}
