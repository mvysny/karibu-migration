package com.github.mvysny.karibumigration

import com.github.mvysny.dynatest.DynaTest
import com.vaadin.flow.component.checkbox.CheckboxGroup

class CheckboxGroupsTest : DynaTest({
    test("api") {
        val r = CheckboxGroup<String>()
        r.setItemCaptionGenerator { it }
    }
})
