package com.github.mvysny.karibumigration

import com.github.mvysny.dynatest.DynaTest
import com.vaadin.flow.component.select.Select

class SelectUtilsTest : DynaTest({
    test("api") {
        val r = Select<String>()
        r.setItemCaptionGenerator { it }
    }
})
