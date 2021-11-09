package com.github.mvysny.karibumigration

import com.vaadin.flow.component.ItemLabelGenerator
import com.vaadin.flow.component.checkbox.CheckboxGroup

@Deprecated("use setItemLabelGenerator()")
public fun <T> CheckboxGroup<T>.setItemCaptionGenerator(generator: ItemLabelGenerator<T>) {
    itemLabelGenerator = generator
}
