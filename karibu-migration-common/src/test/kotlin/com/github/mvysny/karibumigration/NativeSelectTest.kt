package com.github.mvysny.karibumigration

import org.junit.jupiter.api.Test
import kotlin.test.expect

class NativeSelectTest {
    @Test fun smoke() {
        NativeSelect<String>()
    }
    @Test fun label() {
        val select = NativeSelect<String>("foo")
        expect("foo") { select.label }
    }
    @Test fun items() {
        NativeSelect("foo", listOf("a", "b", "c"))
    }
}
