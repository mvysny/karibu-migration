package testapp

import com.github.mvysny.karibumigration.LabelWrapper
import com.vaadin.flow.component.combobox.ComboBox
import com.vaadin.flow.component.html.Hr
import com.vaadin.flow.component.orderedlayout.VerticalLayout

class LabelWrapperDemo : VerticalLayout() {
    init {
        add(LabelWrapper("Wrap Content", ComboBox<String>()))
        add(LabelWrapper("Fill Parent", VerticalLayout(Hr())))
        val cbw = LabelWrapper("Fill Parent 2", ComboBox<String>())
        cbw.setWidthFull()
        add(cbw)
    }
}