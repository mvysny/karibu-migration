package testapp

import com.github.mvysny.karibumigration.*
import com.vaadin.flow.router.Route

@Route("")
class MainLayout : TabSheet() {
    init {
        addLazyTab("RadioButtonGroupCompat") { RadioButtonGroupCompatDemo() }
        addLazyTab("HtmlSpan") { HtmlSpan("This text is <b>bold</b>, <i>italic</i> and <u>underlined</u>") }
        addLazyTab("LabelWrapper") { LabelWrapperDemo() }
    }
}
