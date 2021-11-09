package com.github.mvysny.karibumigration;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.function.SerializableFunction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * Mimicks the Vaadin 8 RadioButtonGroup. Supports {@link #setItemDescriptionGenerator(ItemLabelGenerator)}
 * and {@link #setHtmlContentAllowed(boolean)}.
 * @param <T>
 */
public class RadioButtonGroupCompat<T> extends RadioButtonGroup<T> {
    @NotNull
    private ItemLabelGenerator<T> generator = Object::toString;
    @Nullable
    private ItemLabelGenerator<T> tooltipGenerator = null;
    private boolean htmlContentAllowed = false;

    public RadioButtonGroupCompat()
    {
    }

    /**
     * Mimicks a Vaadin 8 constructor.
     * @param caption ignored - In Vaadin 14, RadioButtonGroup doesn't support a caption.
     * @param items the items to show.
     */
    public RadioButtonGroupCompat(@Nullable String caption, Collection<T> items)
    {
        // In Vaadin 14, RadioButtonGroup doesn't support a caption.
        setItems(items);
    }

    @Deprecated
    public void setItemCaptionGenerator(ItemLabelGenerator<T> generator) {
        setItemLabelGenerator(generator);
    }

    public void setItemLabelGenerator(ItemLabelGenerator<T> generator) {
        this.generator = generator;
        updateRenderer();
    }

    private void updateRenderer() {
        if (tooltipGenerator == null && !htmlContentAllowed) {
            // https://github.com/vaadin/flow-components/issues/1681
            setRenderer(new TextRenderer<>(generator));
        } else {
            setRenderer(new ComponentRenderer<>(new SerializableFunction<T, Component>() {
                @Override
                public Component apply(T t) {
                    final String label = generator.apply(t);
                    final Span span = htmlContentAllowed ? new com.github.mvysny.kaributools.HtmlSpan(label) : new Span(label);
                    if (tooltipGenerator != null) {
                        span.getElement().setAttribute("title", tooltipGenerator.apply(t));
                    }
                    return span;
                }
            }));
        }
    }

    public void setItemDescriptionGenerator(ItemLabelGenerator<T> generator)
    {
        tooltipGenerator = generator;
        updateRenderer();
    }

    public void setHtmlContentAllowed(boolean htmlContentAllowed) {
        this.htmlContentAllowed = htmlContentAllowed;
        updateRenderer();
    }

    public boolean isHtmlContentAllowed() {
        return htmlContentAllowed;
    }
}
