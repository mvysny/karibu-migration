package com.github.mvysny.karibumigration

import com.github.mvysny.dynatest.DynaTest
import com.github.mvysny.kaributesting.v10.MockVaadin
import com.github.mvysny.kaributools.LabelWrapper
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.html.Span
import kotlin.test.expect

class LabelWrapperTest : DynaTest({
    beforeEach { MockVaadin.setup() }
    afterEach { MockVaadin.tearDown() }


    test("smoke") {
        val labelWrapper = LabelWrapper("hello")
        UI.getCurrent().add(labelWrapper)
        expect("hello") { labelWrapper.label }
    }

    test("children") {
        LabelWrapper("Interwebz").add(Span("Foo"), Span("Bar"))
    }
})
