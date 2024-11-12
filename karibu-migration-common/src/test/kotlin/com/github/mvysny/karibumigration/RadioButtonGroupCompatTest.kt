package com.github.mvysny.karibumigration

import com.github.mvysny.kaributesting.v10.getItemLabels
import com.vaadin.flow.component.radiobutton.RadioGroupVariant
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.expect

class RadioButtonGroupCompatTest {
    @Test fun smoke() {
        RadioButtonGroupCompat<String>()
    }
    @Test fun `items not escaped`() {
        val r = RadioButtonGroupCompat<String>(
            null, listOf(
                "<strong>strong</strong>",
                "<i>italics</i>",
                "<u>underline</u>"
            )
        )
        expect(listOf(
            "<strong>strong</strong>",
            "<i>italics</i>",
            "<u>underline</u>")
        ) { r.getItemLabels() }
    }
    @Test fun `items escaped`() {
        val r = RadioButtonGroupCompat<String>(
            null, listOf(
                "<strong>strong</strong>",
                "<i>italics</i>",
                "<u>underline</u>"
            )
        )
        r.isHtmlContentAllowed = true
        expect(listOf(
            "HtmlSpan[innerHTML='<strong>strong</strong>']",
            "HtmlSpan[innerHTML='<i>italics</i>']",
            "HtmlSpan[innerHTML='<u>underline</u>']")
        ) { r.getItemLabels() }
    }
    @Test fun `items not escaped with tooltips`() {
        val r = RadioButtonGroupCompat<String>(
            listOf(
                "<strong>strong</strong>",
                "<i>italics</i>",
                "<u>underline</u>"
            )
        )
        r.setItemDescriptionGenerator { it }
        expect(listOf(
            "Span[text='<strong>strong</strong>', @title='<strong>strong</strong>']",
            "Span[text='<i>italics</i>', @title='<i>italics</i>']",
            "Span[text='<u>underline</u>', @title='<u>underline</u>']")
        ) { r.getItemLabels() }
    }
    @Test fun `items escaped with tooltips`() {
        val r = RadioButtonGroupCompat<String>(
            listOf(
                "<strong>strong</strong>",
                "<i>italics</i>",
                "<u>underline</u>"
            )
        )
        r.isHtmlContentAllowed = true
        r.setItemDescriptionGenerator { it }
        expect(listOf(
            "HtmlSpan[@title='<strong>strong</strong>', innerHTML='<strong>strong</strong>']",
            "HtmlSpan[@title='<i>italics</i>', innerHTML='<i>italics</i>']",
            "HtmlSpan[@title='<u>underline</u>', innerHTML='<u>underline</u>']")
        ) { r.getItemLabels() }
    }
    @Nested inner class vertical {
        @Test fun `true by default`() {
            expect(true) { RadioButtonGroupCompat<String>().isVertical }
            expect(true) { RadioButtonGroupCompat<String>(listOf("a")).isVertical }
        }
        @Test fun `setting the value`() {
            val r = RadioButtonGroupCompat<String>()
            r.isVertical = false
            expect(false) { r.isVertical }
            expect(false) { r.themeNames.contains(RadioGroupVariant.LUMO_VERTICAL.variantName )}
            r.isVertical = true
            expect(true) { r.isVertical }
            expect(true) { r.themeNames.contains(RadioGroupVariant.LUMO_VERTICAL.variantName )}
        }
    }
}
