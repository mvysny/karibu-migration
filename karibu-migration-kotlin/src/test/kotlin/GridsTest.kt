package com.github.mvysny.karibumigration

import com.github.mvysny.dynatest.DynaTest
import com.vaadin.flow.component.grid.Grid

class GridsTest : DynaTest({
    test("api") {
        val g = Grid<String>()
        val c: Grid.Column<String> = g.addColumn { it }
        c.setDescriptionGenerator { it }
        c.setExpandRatio(3)
        g.addFooterRowAt(0)
        g.getHeaderRowCount()
        g.getFooterRowCount()
    }
    test("fluent api") {
        val g = Grid<String>()
        val c: Grid.Column<String> = g.addColumn { it }
            .setDescriptionGenerator { it }
            .setExpandRatio(3)
            .setCaption("Foo")
    }
})
