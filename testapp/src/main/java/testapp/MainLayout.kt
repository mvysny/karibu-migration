package testapp

import com.github.mvysny.karibumigration.HtmlSpan
import com.github.mvysny.karibumigration.RadioButtonGroup
import com.github.mvysny.karibumigration.RadioButtonGroupCompat
import com.github.mvysny.karibumigration.TabSheet
import com.vaadin.flow.component.checkbox.Checkbox
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.Route

@Route("")
class MainLayout : TabSheet() {
    init {
        addLazyTab("RadioButtonGroupCompat") { RadioButtonGroupCompatDemo() }
        addLazyTab("HtmlSpan") { HtmlSpan("This text is <b>bold</b>, <i>italic</i> and <u>underlined</u>") }
    }
}

private class RadioButtonGroupCompatDemo : VerticalLayout() {
    private val isHtmlContentAllowed = Checkbox("isHtmlContentAllowed")
    private val itemDescriptionGenerator = Checkbox("itemDescriptionGenerator")
    private val radioButtonGroupCompat: RadioButtonGroupCompat<String> =
        RadioButtonGroup(
            null,
            listOf(
                "<strong>strong</strong>",
                "<i>italics</i>",
                "<u>underline</u>"
            )
        )

    init {
        add(radioButtonGroupCompat, isHtmlContentAllowed, itemDescriptionGenerator)
        update()
        isHtmlContentAllowed.addValueChangeListener { update() }
        itemDescriptionGenerator.addValueChangeListener { update() }
    }

    private fun update() {
        radioButtonGroupCompat.isHtmlContentAllowed = isHtmlContentAllowed.value
        radioButtonGroupCompat.setItemDescriptionGenerator(if (itemDescriptionGenerator.value) { it -> it } else null )
    }
}
