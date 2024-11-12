package com.github.mvysny.karibumigration

import com.github.mvysny.kaributesting.v10.MockVaadin
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.combobox.ComboBox
import com.vaadin.flow.component.html.Span
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.expect

class LabelWrapperTest {
    @BeforeEach fun fakeVaadin() { MockVaadin.setup() }
    @AfterEach fun tearDownVaadin() { MockVaadin.tearDown() }


    @Test fun smoke() {
        val labelWrapper = LabelWrapper("hello")
        UI.getCurrent().add(labelWrapper)
        expect("hello") { labelWrapper.label }
    }

    @Test fun children() {
        LabelWrapper("Interwebz").add(Span("Foo"), Span("Bar"))
    }

    @Nested inner class `width adjustment` {
        @Test fun `wraps content by default`() {
            expect(null) { LabelWrapper("hi").width }
            expect(null) { LabelWrapper("hi", Span("foo")).width }
            expect(null) { LabelWrapper("hi", ComboBox<String>()).width }
        }
        @Test fun `fills parent if a child with 100percent width is placed inside`() {
            expect("100%") { LabelWrapper("hi", VerticalLayout()).width }
            expect("100%") { LabelWrapper("hi", VerticalLayout().apply { width = "50%" }).width }
            expect("100%") { LabelWrapper("hi").apply { add(VerticalLayout()) }.width }
            expect("100%") { LabelWrapper("hi").apply { add(VerticalLayout().apply { width = "50%" }) }.width }
        }
        @Test fun `children set to fill parent`() {
            val content = Span("foo")
            val lw1 = LabelWrapper("Foo", content)
            lw1.setWidthFull()
            expect("100%") { content.width }
        }
        @Test fun `children set to wrap content`() {
            val content = VerticalLayout()
            val lw1 = LabelWrapper("Foo", content)
            lw1.width = null
            expect(null) { content.width }
        }
    }
}
