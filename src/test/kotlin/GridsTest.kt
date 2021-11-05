package com.github.mvysny.karibumigration

import com.github.mvysny.dynatest.DynaTest
import com.vaadin.flow.component.checkbox.CheckboxGroup
import com.vaadin.flow.component.combobox.ComboBox
import com.vaadin.flow.component.grid.Grid

class GridsTest : DynaTest({
    test("api") {
        val r = Grid<String>()
        val c: Grid.Column<String> = r.addColumn { it }
        c.setDescriptionGenerator { it }
    }
})
