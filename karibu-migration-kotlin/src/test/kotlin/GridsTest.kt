package com.github.mvysny.karibumigration

import com.vaadin.flow.component.grid.Grid
import org.junit.jupiter.api.Test

class GridsTest {
    @Test fun api() {
        val g = Grid<String>()
        val c: Grid.Column<String> = g.addColumn { it }
        c.setDescriptionGenerator { it }
        c.setExpandRatio(3)
        g.addFooterRowAt(0)
        g.getHeaderRowCount()
        g.getFooterRowCount()
    }
    @Test fun `fluent api`() {
        val g = Grid<String>()
        val c: Grid.Column<String> = g.addColumn { it }
            .setDescriptionGenerator { it }
            .setExpandRatio(3)
            .setCaption("Foo")
    }
}
