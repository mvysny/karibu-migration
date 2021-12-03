package com.github.mvysny.karibumigration

import com.github.mvysny.dynatest.DynaTest
import kotlin.test.expect

class NativeSelectTest : DynaTest({
    test("smoke") {
        NativeSelect<String>()
    }
    test("label") {
        val select = NativeSelect<String>("foo")
        expect("foo") { select.label }
    }
    test("items") {
        NativeSelect("foo", listOf("a", "b", "c"))
    }
})
