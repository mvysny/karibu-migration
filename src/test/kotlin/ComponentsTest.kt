@file:Suppress("DEPRECATION")

package com.github.mvysny.karibumigration

import com.github.mvysny.dynatest.DynaTest
import com.vaadin.flow.component.html.Span
import kotlin.test.expect

class ComponentsTest : DynaTest({
    test("setWidthUndefined") {
        val s = Span()
        s.setWidthFull()
        s.setWidthUndefined()
        expect(null) { s.width }
    }
    test("setHeightUndefined") {
        val s = Span()
        s.setHeightFull()
        s.setHeightUndefined()
        expect(null) { s.height }
    }
})
