package com.github.mvysny.karibumigration

import com.github.mvysny.dynatest.DynaTest
import com.github.mvysny.dynatest.expectList
import com.github.mvysny.kaributesting.v10.getItemLabels

class RadioButtonGroupCompatTest : DynaTest({
    test("smoke") {
        RadioButtonGroupCompat<String>()
    }
    test("items not escaped") {
        val r = RadioButtonGroupCompat<String>(
            null, listOf(
                "<strong>strong</strong>",
                "<i>italics</i>",
                "<u>underline</u>"
            )
        )
        expectList(
            "<strong>strong</strong>",
            "<i>italics</i>",
            "<u>underline</u>"
        ) { r.getItemLabels() }
    }
    test("items escaped") {
        val r = RadioButtonGroupCompat<String>(
            null, listOf(
                "<strong>strong</strong>",
                "<i>italics</i>",
                "<u>underline</u>"
            )
        )
        r.isHtmlContentAllowed = true
        expectList(
            "HtmlSpan[innerHTML='<strong>strong</strong>']",
            "HtmlSpan[innerHTML='<i>italics</i>']",
            "HtmlSpan[innerHTML='<u>underline</u>']"
        ) { r.getItemLabels() }
    }
    test("items not escaped with tooltips") {
        val r = RadioButtonGroupCompat<String>(
            listOf(
                "<strong>strong</strong>",
                "<i>italics</i>",
                "<u>underline</u>"
            )
        )
        r.setItemDescriptionGenerator { it }
        expectList(
            "Span[text='<strong>strong</strong>', @title='<strong>strong</strong>']",
            "Span[text='<i>italics</i>', @title='<i>italics</i>']",
            "Span[text='<u>underline</u>', @title='<u>underline</u>']"
        ) { r.getItemLabels() }
    }
    test("items escaped with tooltips") {
        val r = RadioButtonGroupCompat<String>(
            listOf(
                "<strong>strong</strong>",
                "<i>italics</i>",
                "<u>underline</u>"
            )
        )
        r.isHtmlContentAllowed = true
        r.setItemDescriptionGenerator { it }
        expectList(
            "HtmlSpan[@title='<strong>strong</strong>', innerHTML='<strong>strong</strong>']",
            "HtmlSpan[@title='<i>italics</i>', innerHTML='<i>italics</i>']",
            "HtmlSpan[@title='<u>underline</u>', innerHTML='<u>underline</u>']"
        ) { r.getItemLabels() }
    }
})
