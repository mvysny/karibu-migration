package com.github.mvysny.karibumigration;

import com.vaadin.flow.component.Component;

/**
 * Replacement for Vaadin 8 HorizontalSplitPanel.
 */
@Deprecated
public class HorizontalSplitPanel extends SplitLayoutCompat {
    public HorizontalSplitPanel() {
        setOrientation(Orientation.HORIZONTAL);
    }

    public HorizontalSplitPanel(Component firstComponent, Component secondComponent) {
        this();
        setFirstComponent(firstComponent);
        setSecondComponent(secondComponent);
    }
}
