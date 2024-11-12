@file:Suppress("DEPRECATION")

package com.github.mvysny.karibumigration

import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.html.Span
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.expect

class HorizontalSplitPlanelTest {
    @Test fun smoke() {
        HorizontalSplitPanel()
    }
    @Nested inner class HorizontalSplitPanelTests : AbstractSplitLayoutCompatTests({ HorizontalSplitPanel() })
}

abstract class AbstractSplitLayoutCompatTests(val factory: () -> SplitLayoutCompat) {
    @Test fun `first-second component`() {
        val p = factory()
        expect(null) { p.firstComponent }
        expect(null) { p.secondComponent }
        val fc = Div()
        p.firstComponent = fc
        expect(fc) { p.firstComponent }
        val sc = Span()
        p.secondComponent = sc
        expect(sc) { p.secondComponent }
    }

    @Test fun `add component`() {
        val p = factory()
        expect(null) { p.firstComponent }
        expect(null) { p.secondComponent }
        val fc = Div()
        p.addComponent(fc)
        expect(fc) { p.firstComponent }
        expect(null) { p.secondComponent }
        val sc = Span()
        p.addComponent(sc)
        expect(fc) { p.firstComponent }
        expect(sc) { p.secondComponent }
        assertThrows<UnsupportedOperationException> {
            p.addComponent(Span())
        }
    }

    @Test fun `remove component`() {
        val p = factory()
        val fc = Div()
        p.addComponent(fc)
        val sc = Span()
        p.addComponent(sc)
        p.removeComponent(sc)
        expect(fc) { p.firstComponent }
        expect(null) { p.secondComponent }
        p.removeComponent(sc)
        expect(fc) { p.firstComponent }
        expect(null) { p.secondComponent }
        p.removeComponent(fc)
        expect(null) { p.firstComponent }
        expect(null) { p.secondComponent }
    }

    @Test fun `component count`() {
        val p = factory()
        expect(0) { p.componentCount }
        val fc = Div()
        p.addComponent(fc)
        expect(1) { p.componentCount }
        val sc = Span()
        p.addComponent(sc)
        expect(2) { p.componentCount }
        p.removeComponent(sc)
        expect(1) { p.componentCount }
        p.removeComponent(sc)
        expect(1) { p.componentCount }
        p.removeComponent(fc)
        expect(0) { p.componentCount }
    }

    @Test fun `setting firstComponent multiple times removes the previous one`() {
        val p = factory()
        expect(null) { p.firstComponent }
        val fc = Span("first")
        p.firstComponent = fc
        expect(fc) { p.firstComponent }
        val fc2 = Span("first 2")
        p.firstComponent = fc2
        expect(fc2) { p.firstComponent }
        expect(null) { fc.parent.orElse(null) }
    }

    @Test fun `setting secondComponent multiple times removes the previous one`() {
        val p = factory()
        expect(null) { p.secondComponent }
        val fc = Span("first")
        p.secondComponent = fc
        expect(fc) { p.secondComponent }
        val fc2 = Span("first 2")
        p.secondComponent = fc2
        expect(fc2) { p.secondComponent }
        expect(null) { fc.parent.orElse(null) }
    }
}
