package com.github.mvysny.karibumigration

import com.github.mvysny.dynatest.DynaTest
import com.github.mvysny.kaributesting.v10.MockVaadin
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.combobox.ComboBox
import com.vaadin.flow.component.html.Span
import com.vaadin.flow.component.orderedlayout.VerticalLayout
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

    group("width adjustment") {
        test("wraps content by default") {
            expect(null) { LabelWrapper("hi").width }
            expect(null) { LabelWrapper("hi", Span("foo")).width }
            expect(null) { LabelWrapper("hi", ComboBox<String>()).width }
        }
        test("fills parent if a child with 100% width is placed inside") {
            expect("100%") { LabelWrapper("hi", VerticalLayout()).width }
            expect("100%") { LabelWrapper("hi", VerticalLayout().apply { width = "50%" }).width }
            expect("100%") { LabelWrapper("hi").apply { add(VerticalLayout()) }.width }
            expect("100%") { LabelWrapper("hi").apply { add(VerticalLayout().apply { width = "50%" }) }.width }
        }
        test("children set to fill parent") {
            val content = Span("foo")
            val lw1 = LabelWrapper("Foo", content)
            lw1.setWidthFull()
            expect("100%") { content.width }
        }
        test("children set to wrap content") {
            val content = VerticalLayout()
            val lw1 = LabelWrapper("Foo", content)
            lw1.width = null
            expect(null) { content.width }
        }
    }
})
