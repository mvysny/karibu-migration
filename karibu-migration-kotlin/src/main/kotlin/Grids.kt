package com.github.mvysny.karibumigration

import com.vaadin.flow.component.grid.FooterRow
import com.vaadin.flow.component.grid.Grid

/**
 * No available replacement, see https://github.com/vaadin/flow-components/issues/2315
 */
@Deprecated("No replacement as of now")
public fun <T> Grid.Column<T>.setDescriptionGenerator(generator: (T) -> String): Grid.Column<T> {
    // do nothing
    return this
}

@Deprecated("Use setFlexGrow()", replaceWith = ReplaceWith("setFlexGrow(ratio)"))
public fun <T> Grid.Column<T>.setExpandRatio(ratio: Int): Grid.Column<T> =
    setFlexGrow(ratio)

@Deprecated("Use getFlexGrow()", replaceWith = ReplaceWith("setFlexGrow(ratio)"))
public fun <T> Grid.Column<T>.getExpandRatio(): Int = flexGrow

@Deprecated("Use setHeader()", replaceWith = ReplaceWith("setHeader(caption)"))
public fun <T> Grid.Column<T>.setCaption(caption: String): Grid.Column<T> =
    setHeader(caption)

@Deprecated("no replacement, see+vote for https://github.com/vaadin/flow-components/issues/1496")
public fun <T> Grid.Column<T>.getCaption(caption: String): Grid.Column<T> = throw UnsupportedOperationException("unimplemented")

@Deprecated("No replacement as of now")
public fun <T> Grid<T>.addFooterRowAt(index: Int): FooterRow {
    require(index >= 0) { "index must be 0 or greater: $index" }
    return if (index == 0) {
        prependFooterRow()
    } else {
        // we could perhaps call insertColumnLayer() but it's private. Simply append a fotter row below.
        appendFooterRow()
    }
}

@Deprecated("getFooterRows().size()")
public fun Grid<*>.getFooterRowCount(): Int = footerRows.size
@Deprecated("getHeaderRows().size()")
public fun Grid<*>.getHeaderRowCount(): Int = headerRows.size

@Deprecated("No replacement as of now; see+vote for https://github.com/vaadin/flow-components/issues/1603")
public fun <T> Grid.Column<T>.setHidable(hidable: Boolean): Grid.Column<T> {
    // do nothing
    return this
}

@Deprecated("No replacement as of now; see+vote for https://github.com/vaadin/flow-components/issues/1603")
public fun <T> Grid.Column<T>.isHidable(hidable: Boolean): Boolean = false

@Deprecated("use setVisible()")
public fun <T> Grid.Column<T>.setHidden(hidden: Boolean): Grid.Column<T> {
    isVisible = !hidden
    return this
}

@Deprecated("use isVisible()")
public fun <T> Grid.Column<T>.isHidden(): Boolean = !isVisible
