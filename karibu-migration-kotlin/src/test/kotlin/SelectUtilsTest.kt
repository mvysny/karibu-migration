package com.github.mvysny.karibumigration

import com.github.mvysny.kaributesting.v10.expectList
import com.github.mvysny.kaributesting.v10.getSuggestions
import com.vaadin.flow.component.select.Select
import org.junit.jupiter.api.Test

class SelectUtilsTest {
    @Test fun setItemCaptionGenerator() {
        val r = Select<String>()
        r.setItemCaptionGenerator { "$it foo" }
        r.setItems("1", "2", "3")
        expectList("1 foo", "2 foo", "3 foo") { r.getSuggestions() }
    }
}
