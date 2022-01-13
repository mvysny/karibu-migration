package com.github.mvysny.karibumigration;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.shared.Registration;

@Deprecated
public abstract class SplitLayoutCompat extends SplitLayout {
    public SplitLayoutCompat() {
        setSizeFull();
    }

    /**
     * Add a component into this container. The component is added to the right
     * or under the previous component.
     *
     * @param c
     *            the component to be added.
     */
    public void addComponent(Component c) {
        if (getFirstComponent() == null) {
            setFirstComponent(c);
        } else if (getSecondComponent() == null) {
            setSecondComponent(c);
        } else {
            throw new UnsupportedOperationException(
                    "Split panel can contain only two components");
        }
    }

    public void setSecondComponent(Component secondComponent) {
        addToSecondary(secondComponent);
    }

    public void setFirstComponent(Component firstComponent) {
        addToPrimary(firstComponent);
    }

    public Component getFirstComponent() {
        return getPrimaryComponent();
    }

    public Component getSecondComponent() {
        return getSecondaryComponent();
    }

    /**
     * Removes the component from this container.
     *
     * @param c
     *            the component to be removed.
     */
    public void removeComponent(Component c) {
        if (c == getFirstComponent()) {
            setFirstComponent(null);
        } else if (c == getSecondComponent()) {
            setSecondComponent(null);
        }
    }

    /**
     * Gets the number of contained components. Consistent with the iterator
     * returned by {@link #getComponentIterator()}.
     *
     * @return the number of contained components (zero, one or two)
     */
    public int getComponentCount() {
        int count = 0;
        if (getFirstComponent() != null) {
            count++;
        }
        if (getSecondComponent() != null) {
            count++;
        }
        return count;
    }

    public void replaceComponent(Component oldComponent,
                                 Component newComponent) {
        if (oldComponent == getFirstComponent()) {
            setFirstComponent(newComponent);
        } else if (oldComponent == getSecondComponent()) {
            setSecondComponent(newComponent);
        }
    }

    /**
     * Moves the position of the splitter.
     *
     * @param pos the new size of the first region in the unit that was last
     *            used (default is percentage). Fractions are only allowed when
     *            unit is percentage.
     */
    public void setSplitPosition(float pos) {
        setSplitPosition(pos, Unit.PERCENTAGE, false);
    }

    /**
     * Moves the position of the splitter.
     *
     * @param pos     the new size of the region in the unit that was last used
     *                (default is percentage). Fractions are only allowed when unit
     *                is percentage.
     * @param reverse if set to true the split splitter position is measured by the
     *                second region else it is measured by the first region
     */
    public void setSplitPosition(float pos, boolean reverse) {
        setSplitPosition(pos, Unit.PERCENTAGE, reverse);
    }

    /**
     * Moves the position of the splitter with given position and unit.
     *
     * @param pos  the new size of the first region. Fractions are only allowed
     *             when unit is percentage.
     * @param unit the unit in which the size is given.
     */
    public void setSplitPosition(float pos, Unit unit) {
        setSplitPosition(pos, unit, false);
    }

    private float pos = 50;

    /**
     * Moves the position of the splitter with given position and unit.
     *
     * @param pos     the new size of the first region. Fractions are only allowed
     *                when unit is percentage. 0..100
     * @param unit    the unit in which the size is given.
     * @param reverse if set to true the split splitter position is measured by the
     *                second region else it is measured by the first region
     */
    public void setSplitPosition(float pos, Unit unit, boolean reverse) {
        if (unit != Unit.PERCENTAGE) {
            throw new IllegalArgumentException(
                    "Only percentage units are allowed");
        }
        if (reverse) {
            pos = 100 - pos;
        }
        this.pos = pos;
        setSplitterPosition(pos);
    }

    /**
     * Returns the current position of the splitter, in
     * {@link #getSplitPositionUnit()} units.
     *
     * @return position of the splitter
     */
    public float getSplitPosition() {
        return pos;
    }

    /**
     * Always PERCENTAGE.
     *
     * @return unit of position of the splitter
     * @see #setSplitPosition(float, Unit)
     */
    public Unit getSplitPositionUnit() {
        return Unit.PERCENTAGE;
    }

    /**
     * Is the split position reversed. By default the split position is measured
     * by the first region, but if split position is reversed the measuring is
     * done by the second region instead.
     *
     * @return {@code true} if reversed, {@code false} otherwise.
     * @see #setSplitPosition(float, boolean)
     * @since 7.3.6
     */
    public boolean isSplitPositionReversed() {
        return false;
    }

    /**
     * Ignored
     */
    public void setMinSplitPosition(float pos, Unit unit) {
    }

    /**
     * Ignored
     */
    public float getMinSplitPosition() {
        return 0;
    }

    /**
     * Ignored
     */
    public Unit getMinSplitPositionUnit() {
        return Unit.PERCENTAGE;
    }

    /**
     * Ignored
     */
    public void setMaxSplitPosition(float pos, Unit unit) {
    }

    /**
     * Ignored
     */
    public float getMaxSplitPosition() {
        return 100;
    }

    /**
     * Ignored
     */
    public Unit getMaxSplitPositionUnit() {
        return Unit.PERCENTAGE;
    }

    /**
     * ignored
     */
    public void setLocked(boolean locked) {
    }

    /**
     * ignored
     */
    public boolean isLocked() {
        return false;
    }

    public Registration addSplitPositionChangeListener(
            ComponentEventListener<SplitterDragendEvent<SplitLayout>> listener) {
        return addSplitterDragendListener(listener);
    }
}
