package com.github.mvysny.karibumigration;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * A poor man's replacement for Vaadin 8 GridLayout.
 */
public class GridLayout extends FlexLayout {
    @Nullable
    private FlexComponent.Alignment alignment = null;

    @Nullable
    private FlexComponent.JustifyContentMode justifyContentMode = null;

    @NotNull
    private final List<Integer> expandColumns;

    public enum Space {
        XS, S, M, L, XL;

        public final String styleVar;

        Space() {
            styleVar = "var(--lumo-space-" + name().toLowerCase() + ")";
        }
    }

    /**
     * Compatibility constructor with the Vaadin 8 GridLayout.
     *
     * @param columns the number of columns.
     * @param rows    ignored.
     * @deprecated use the {@link #GridLayout(int)} constructor.
     */
    @Deprecated
    public GridLayout(int columns, int rows) {
        this(columns);
    }

    /**
     * Constructs an empty (1x1) grid layout that is extended as needed.
     */
    public GridLayout() {
        this(1);
    }

    /**
     * Constructs a GridLayout of given size (number of columns and rows) and
     * adds the given components in order to the grid.
     *
     * @param columns  Number of columns in the grid.
     * @param rows     ignored
     * @param children Components to add to the grid.
     * @see #add(Component...)
     * @deprecated use {@link #GridLayout(int, Component...)}.
     */
    public GridLayout(int columns, int rows, Component... children) {
        this(columns, children);
    }

    public GridLayout(int columns) {
        if (columns < 1) {
            throw new IllegalArgumentException("Parameter columns: invalid value " + columns + ": must be 1 or greater");
        }
        expandColumns = new ArrayList<>(columns);
        expandColumns.addAll(Collections.nCopies(columns, 1));
        updateColumns();
        getStyle().set("display", "grid")
                .set("box-sizing", "border-box");
    }

    private void updateColumns() {
        final String columns = expandColumns
                .stream()
                .map(it -> it == 0 ? "max-content" : it + "fr")
                .collect(Collectors.joining(" "));
        getStyle().set("grid-template-columns", columns);
    }

    public void setColumnExpandRatio(int column, int ratio) {
        if (ratio < 0) {
            throw new IllegalArgumentException("Parameter ratio: invalid value " + ratio + ": must be 0 or greater");
        }
        expandColumns.set(column, ratio);
        updateColumns();
    }

    public GridLayout(int columns, Component... children) {
        this(columns);
        add(children);
    }

    @Override
    public void setAlignItems(@Nullable FlexComponent.Alignment alignment) {
        getChildren().forEach(c -> setAlignSelf(alignment, c));
        this.alignment = alignment;
    }

    @Override
    @Nullable
    public Alignment getAlignItems() {
        return alignment;
    }

    @Override
    public void setJustifyContentMode(@Nullable JustifyContentMode justifyContentMode) {
        getChildren().forEach(c -> {
            if (justifyContentMode == null) {
                c.getElement().getStyle().remove("justify-self");
            } else {
                c.getElement().getStyle().set("justify-self", getFlexValue(justifyContentMode));
            }
        });
        this.justifyContentMode = justifyContentMode;
    }

    private static String getFlexValue(@Nullable JustifyContentMode justifyContentMode) {
        try {
            Method method = JustifyContentMode.class.getDeclaredMethod("getFlexValue");
            method.setAccessible(true);
            return justifyContentMode == null ? null : method.invoke(justifyContentMode).toString();
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Nullable
    public JustifyContentMode getJustifyContentMode() {
        return justifyContentMode;
    }

    @Override
    public void add(Component... component) {
        super.add(component);
        setAlignItems(alignment);
        setJustifyContentMode(justifyContentMode);
    }

    public void setSpacing(boolean spacing) {
        setSpacing(spacing ? Space.M : null);
    }

    public void setSpacing(@Nullable Space spacing) {
        getStyle().set("grid-gap", spacing == null ? "0" : spacing.styleVar);
    }

    public void setPadding(boolean padding) {
        setPadding(padding ? Space.M : null);
    }

    public void setPadding(@Nullable Space padding) {
        getStyle().set("padding", padding == null ? "0" : padding.styleVar);
    }

    public void setMargin(boolean margin) {
        setMargin(margin ? Space.M : null);
    }

    public void setMargin(@Nullable Space margin) {
        getStyle().set("margin", margin == null ? "0" : margin.styleVar);
    }

    public void spanColumns(@NotNull Component component, int span) {
        Objects.requireNonNull(component);
        if (span < 1) {
            throw new IllegalArgumentException("Parameter span: invalid value " + span + ": must be 1 or greater");
        }
        if (span == 1) {
            component.getElement().getStyle().remove("grid-column-end");
        } else {
            component.getElement().getStyle().set("grid-column-end", "span " + span);
        }
    }

    public void spanRows(@NotNull Component component, int span) {
        Objects.requireNonNull(component);
        if (span < 1) {
            throw new IllegalArgumentException("Parameter span: invalid value " + span + ": must be 1 or greater");
        }
        if (span == 1) {
            component.getElement().getStyle().remove("grid-row-end");
        } else {
            component.getElement().getStyle().set("grid-row-end", "span " + span);
        }
    }

    public void startColumn(@NotNull Component component, int start) {
        component.getElement().getStyle().set("grid-column-start", "span " + start);
    }

    public void startRow(@NotNull Component component, int start) {
        component.getElement().getStyle().set("grid-row-start", "span " + start);
    }

    /**
     * <p>
     * Adds a component to the grid in the specified area. The area is defined
     * by specifying the upper left corner (column1, row1) and the lower right
     * corner (column2, row2) of the area. The coordinates are zero-based.
     * </p>
     *
     * <p>
     * If the area overlaps with any of the existing components already present
     * in the grid, the operation will succeed - no exceptions are thrown.
     * </p>
     *
     * @param component the component to be added, not <code>null</code>.
     * @param column1   the column of the upper left corner of the area <code>component</code>
     *                  is supposed to occupy. The leftmost column has index 0.
     * @param row1      the row of the upper left corner of the area <code>c</code> is
     *                  supposed to occupy. The topmost row has index 0.
     * @param column2   the column of the lower right corner of the area
     *                  <code>component</code> is supposed to occupy.
     * @param row2      the row of the lower right corner of the area <code>c</code>
     *                  is supposed to occupy.
     */
    public void addComponent(@NotNull Component component, int column1, int row1,
                             int column2, int row2) {
        Objects.requireNonNull(component);
        final int spanColumns = column2 - column1 + 1;
        final int spanRows = column2 - column1 + 1;
        if (spanColumns < 1) {
            throw new IllegalArgumentException("Parameter column2: invalid value " + column2 + ": must be greater than column1 " + column1);
        }
        if (spanRows < 1) {
            throw new IllegalArgumentException("Parameter column2: invalid value " + column2 + ": must be greater than column1 " + column1);
        }
        add(component);
        startColumn(component, column1);
        startRow(component, row1);
        spanColumns(component, spanColumns);
        spanRows(component, spanRows);
    }

    /**
     * Adds the component to the grid in cells column1,row1 (NortWest corner of
     * the area.) End coordinates (SouthEast corner of the area) are the same as
     * column1,row1. The coordinates are zero-based. Component width and height
     * is 1.
     *
     * @param component
     *            the component to be added, not <code>null</code>.
     * @param column
     *            the column index, starting from 0.
     * @param row
     *            the row index, starting from 0.
     */
    public void addComponent(@NotNull Component component, int column, int row) {
        addComponent(component, column, row, column, row);
    }

    public void removeComponent(@Nullable Component component) {
        if (component != null) {
            remove(component);
        }
    }
}
