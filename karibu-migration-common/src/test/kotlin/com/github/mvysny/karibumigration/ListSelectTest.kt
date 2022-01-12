package com.github.mvysny.karibumigration

import com.github.mvysny.dynatest.DynaTest
import com.github.mvysny.dynatest.expectList

class ListSelectTest : DynaTest({
    test("smoke") {
        ListSelect<String>()
        ListSelect<String>("foo")
        ListSelect("bar", listOf(2, 5))
    }

    test("setItemCaptionGenerator()") {
        val ls = ListSelect("bar", listOf(2, 5))
        ls.setItemCaptionGenerator { "item $it" }
        // https://github.com/mvysny/karibu-testing/issues/98
        expectList("item 2", "item 5") { ls.getRenderedItems() }
    }
})
