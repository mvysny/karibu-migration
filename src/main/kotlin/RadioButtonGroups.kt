package com.github.mvysny.karibumigration

import com.github.mvysny.kaributools.setItemLabelGenerator
import com.vaadin.flow.component.ItemLabelGenerator
import com.vaadin.flow.component.radiobutton.RadioButtonGroup

// workaround for https://github.com/vaadin/flow-components/issues/1681
@Deprecated("use setItemLabelGenerator() from karibu-tools", replaceWith = ReplaceWith("setItemLabelGenerator(generator)", "com.github.mvysny.kaributools.setItemLabelGenerator"))
public fun <T> RadioButtonGroup<T>.setItemCaptionGenerator(generator: ItemLabelGenerator<T>) {
    setItemLabelGenerator(generator)
}

@Deprecated("No replacement as of now")
public fun <T> RadioButtonGroup<T>.setItemDescriptionGenerator(generator: ItemLabelGenerator<T>) {
    // todo implement in a Vaadin 8-compatible way.
}
