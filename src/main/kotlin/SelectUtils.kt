package com.github.mvysny.karibumigration

import com.vaadin.flow.component.ItemLabelGenerator
import com.vaadin.flow.component.select.Select

@Deprecated("use setItemLabelGenerator()")
public fun <T> Select<T>.setItemCaptionGenerator(generator: ItemLabelGenerator<T>) {
    itemLabelGenerator = generator
}
