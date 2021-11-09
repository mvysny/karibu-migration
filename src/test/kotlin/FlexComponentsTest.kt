package com.github.mvysny.karibumigration

import com.github.mvysny.dynatest.DynaTest
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout

class FlexComponentsTest : DynaTest({
    test("api") {
        VerticalLayout().setExpandRatio(Button("Foo"), 1f)
        HorizontalLayout().setExpandRatio(Button("Foo"), 1f)
        VerticalLayout().addComponentsAndExpand(Button("Foo"))
        HorizontalLayout().addComponentsAndExpand(Button("Foo"))
    }
})
