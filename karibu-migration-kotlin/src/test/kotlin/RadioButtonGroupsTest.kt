package com.github.mvysny.karibumigration

import com.vaadin.flow.component.radiobutton.RadioButtonGroup
import org.junit.jupiter.api.Test

class RadioButtonGroupsTest {
    @Test fun api() {
        val r = RadioButtonGroup<String>()
        r.setItemCaptionGenerator { it }
    }
}
