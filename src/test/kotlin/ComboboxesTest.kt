package com.github.mvysny.karibumigration

import com.github.mvysny.dynatest.DynaTest
import com.vaadin.flow.component.checkbox.CheckboxGroup
import com.vaadin.flow.component.combobox.ComboBox

class ComboboxesTest : DynaTest({
    test("api") {
        val r = ComboBox<String>()
        r.setItemCaptionGenerator { it }
        r.setEmptySelectionAllowed(true)
        r.setEmptySelectionCaption("null")
    }
})
