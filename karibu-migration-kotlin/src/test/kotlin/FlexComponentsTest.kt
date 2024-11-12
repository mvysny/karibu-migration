package com.github.mvysny.karibumigration

import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import org.junit.jupiter.api.Test

class FlexComponentsTest {
    @Test fun api() {
        VerticalLayout().setExpandRatio(Button("Foo"), 1f)
        HorizontalLayout().setExpandRatio(Button("Foo"), 1f)
        VerticalLayout().addComponentsAndExpand(Button("Foo"))
        HorizontalLayout().addComponentsAndExpand(Button("Foo"))
    }
}
