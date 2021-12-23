package com.github.mvysny.karibumigration

import com.github.mvysny.dynatest.DynaTest
import com.github.mvysny.kaributesting.v10.MockVaadin
import com.vaadin.flow.component.UI
import kotlin.test.expect

class HtmlSpanTest : DynaTest({
    beforeEach { MockVaadin.setup() }
    afterEach { MockVaadin.tearDown() }

    test("smoke") {
        UI.getCurrent().add(HtmlSpan())
        UI.getCurrent().add(HtmlSpan("foo"))
        UI.getCurrent().add(HtmlSpan("foo<p>bar</p>baz"))
    }

    test("innerhtml") {
        val s = HtmlSpan()
        s.innerHtml = "foo"
        expect("foo") { s.innerHtml }
        s.innerHtml = "foo<p>bar</p>baz"
        expect("foo<p>bar</p>baz") { s.innerHtml }
        s.innerHtml = null
        expect("") { s.innerHtml }
    }
})
