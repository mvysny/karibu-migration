package com.github.mvysny.karibumigration;

import com.vaadin.flow.component.Component;

@Deprecated
public class VerticalSplitPanel extends SplitLayoutCompat {
    public VerticalSplitPanel() {
        setSizeFull();
        setOrientation(Orientation.VERTICAL);
    }

    public VerticalSplitPanel(Component firstComponent, Component secondComponent) {
        this();
        setFirstComponent(firstComponent);
        setSecondComponent(secondComponent);
    }
}
