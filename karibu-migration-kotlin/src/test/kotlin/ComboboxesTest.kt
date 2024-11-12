package com.github.mvysny.karibumigration

import com.vaadin.flow.component.combobox.ComboBox
import org.junit.jupiter.api.Test

class ComboboxesTest {
    @Test fun api() {
        val r = ComboBox<String>()
        r.setItemCaptionGenerator { it }
        r.setEmptySelectionAllowed(true)
        r.setEmptySelectionCaption("null")
    }
}
