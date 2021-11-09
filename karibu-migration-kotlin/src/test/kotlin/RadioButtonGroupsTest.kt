package com.github.mvysny.karibumigration

import com.github.mvysny.dynatest.DynaTest
import com.vaadin.flow.component.radiobutton.RadioButtonGroup

class RadioButtonGroupsTest : DynaTest({
    test("api") {
        val r = RadioButtonGroup<String>()
        r.setItemCaptionGenerator { it }
    }
})
