package com.github.mvysny.karibumigration

import com.vaadin.flow.component.ItemLabelGenerator
import com.vaadin.flow.component.radiobutton.RadioButtonGroup

@Deprecated("use setItemLabelGenerator() from karibu-tools")
public fun <T> RadioButtonGroup<T>.setItemCaptionGenerator(generator: ItemLabelGenerator<T>) {
    // workaround for https://github.com/vaadin/flow-components/issues/1681
    setItemLabelGenerator(generator)
}
