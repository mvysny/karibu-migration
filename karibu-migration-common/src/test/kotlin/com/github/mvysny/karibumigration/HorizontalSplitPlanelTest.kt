package com.github.mvysny.karibumigration

import com.github.mvysny.dynatest.DynaNodeGroup
import com.github.mvysny.dynatest.DynaTest
import com.github.mvysny.dynatest.expectThrows
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.html.Span
import kotlin.test.expect

class HorizontalSplitPlanelTest : DynaTest({
    test("smoke") {
        HorizontalSplitPanel()
    }
    splitLayoutCompatTests { HorizontalSplitPanel() }
})

fun DynaNodeGroup.splitLayoutCompatTests(factory: () -> SplitLayoutCompat) {
    test("first/second component") {
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

    test("add component") {
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
        expectThrows(UnsupportedOperationException::class) {
            p.addComponent(Span())
        }
    }

    test("remove component") {
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

    test("component count") {
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
}
