package com.github.mvysny.karibumigration

import com.github.mvysny.dynatest.DynaTest
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.html.Span
import kotlin.test.expect

class VerticalSplitPlanelTest : DynaTest({
    test("smoke") {
        VerticalSplitPanel()
    }
    splitLayoutCompatTests { VerticalSplitPanel() }
})
