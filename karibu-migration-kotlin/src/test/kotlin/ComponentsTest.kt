package com.github.mvysny.karibumigration

import com.vaadin.flow.component.html.Span
import org.junit.jupiter.api.Test
import kotlin.test.expect

class ComponentsTest {
    @Test fun setWidthUndefined() {
        val s = Span()
        s.setWidthFull()
        s.setWidthUndefined()
        expect(null) { s.width }
    }
    @Test fun setHeightUndefined() {
        val s = Span()
        s.setHeightFull()
        s.setHeightUndefined()
        expect(null) { s.height }
    }
    @Test fun caption() {
        val s = Span()
        s.setCaption("foo")
        expect("foo") { s.getCaption() }
    }
    @Test fun description() {
        val s = Span()
        s.description = "foo"
        expect("foo") { s.description }
    }
    @Test fun `remove all`() {
        Span().removeAllComponents()
    }
    @Test fun addComponent() {
        Span().addComponent(Span())
    }
    @Test fun removeComponent() {
        Span().removeComponent(Span())
    }
}
