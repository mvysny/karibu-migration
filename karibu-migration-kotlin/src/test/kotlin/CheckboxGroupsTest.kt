package com.github.mvysny.karibumigration

import com.vaadin.flow.component.checkbox.CheckboxGroup
import org.junit.jupiter.api.Test

class CheckboxGroupsTest {
    @Test fun api() {
        val r = CheckboxGroup<String>()
        r.setItemCaptionGenerator { it }
    }
}
