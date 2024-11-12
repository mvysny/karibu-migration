package com.github.mvysny.karibumigration

import com.github.mvysny.kaributesting.v10.getRenderedItems
import org.junit.jupiter.api.Test
import kotlin.test.expect

class ListSelectTest {
    @Test fun smoke() {
        ListSelect<String>()
        ListSelect<String>("foo")
        ListSelect("bar", listOf(2, 5))
    }

    @Test fun setItemCaptionGenerator() {
        val ls = ListSelect("bar", listOf(2, 5))
        ls.setItemCaptionGenerator { "item $it" }
        // https://github.com/mvysny/karibu-testing/issues/98
        expect(listOf("item 2", "item 5")) { ls.getRenderedItems() }
    }
}
