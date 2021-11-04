package com.github.mvysny.karibumigration

import com.vaadin.flow.component.grid.Grid

/**
 * No available replacement, see https://github.com/vaadin/flow-components/issues/2315
 */
@Deprecated("No replacement as of now")
public fun <T> Grid.Column<T>.setDescriptionGenerator(generator: (T) -> String) {
    // do nothing
}
