package com.github.mvysny.karibumigration;

import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.data.provider.DataProvider;

import java.util.Collection;

/**
 * Mimics Vaadin 8's constructors. Not marked deprecated since having those
 * constructors is quite useful.
 * @param <T> the type of the items for the select
 */
public class NativeSelect<T> extends Select<T> {
    /**
     * Creates a new {@code NativeSelect} with an empty caption and no items.
     *
     * Primary constructor: all other constructors ultimately delegate to this one.
     */
    public NativeSelect() {
    }

    /**
     * Constructor from super.
     * @param items the items for the select
     */
    public NativeSelect(T... items) {
        this();
        setItems(items);
    }

    /**
     * Creates a new {@code NativeSelect} with the given caption and no items.
     *
     * @param caption
     *            the component caption to set, null for no caption
     */
    public NativeSelect(String caption) {
        this();
        setLabel(caption);
    }

    /**
     * Creates a new {@code NativeSelect} with the given caption, containing the
     * data items in the given collection.
     *
     * @param caption
     *            the component caption to set, null for no caption
     * @param items
     *            the data items to use, not null
     */
    public NativeSelect(String caption, Collection<T> items) {
        this(caption);
        setItems(items);
    }

    /**
     * Creates a new {@code NativeSelect} with the given caption, using the
     * given {@code DataProvider} as the source of data items.
     *
     * @param caption
     *            the component caption to set, null for no caption
     * @param dataProvider
     *            the source of data items to use, not null
     */
    public NativeSelect(String caption, DataProvider<T, ?> dataProvider) {
        this(caption);
        setDataProvider(dataProvider);
    }

    /**
     * @deprecated Use {@link #setItemLabelGenerator(ItemLabelGenerator)}
     */
    @Deprecated
    public void setItemCaptionGenerator(ItemLabelGenerator<T> generator) {
        setItemLabelGenerator(generator);
    }
}
