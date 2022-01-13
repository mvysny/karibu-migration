package com.github.mvysny.karibumigration;

import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.renderer.TextRenderer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Objects;

/**
 * Replacement for Vaadin 8's ListSelect. Doesn't support captions though - use {@link LabelWrapper}
 * if need be.
 */
public class ListSelect<T> extends MultiSelectListBox<T> {
    public ListSelect() {
    }

    /**
     * Constructs a new ListSelect with the given caption.
     *
     * @param caption
     *            the caption to set, can be {@code null}
     * @deprecated ListBox doesn't support caption.
     */
    @Deprecated
    public ListSelect(@Nullable String caption) {
        this();
        setCaption(caption);
    }

    /**
     * Constructs a new ListSelect with caption and data provider for options.
     *
     * @param caption
     *            the caption to set, can be {@code null}
     * @param dataProvider
     *            the data provider, not {@code null}
     * @since 8.0
     */
    public ListSelect(String caption, DataProvider<T, ?> dataProvider) {
        this(caption);
        setDataProvider(dataProvider);
    }

    /**
     * Constructs a new ListSelect with caption and the given options.
     *
     * @param caption
     *            the caption to set, can be {@code null}
     * @param options
     *            the options, cannot be {@code null}
     */
    public ListSelect(@Nullable String caption, @NotNull Collection<T> options) {
        this(caption, DataProvider.ofCollection(options));
    }

    /**
     * @deprecated ListBox doesn't support caption.
     */
    @Deprecated
    public void setCaption(@Nullable String caption) {
        // do nothing
    }

    @NotNull
    private ItemLabelGenerator<T> itemLabelGenerator = Object::toString;

    /**
     * @deprecated use {@link #setItemLabelGenerator(ItemLabelGenerator)}.
     */
    @Deprecated
    public void setItemCaptionGenerator(@NotNull ItemLabelGenerator<T> generator) {
        setItemLabelGenerator(generator);
    }

    public void setItemLabelGenerator(@NotNull ItemLabelGenerator<T> generator) {
        // see+vote for https://github.com/vaadin/platform/issues/2601
        this.itemLabelGenerator = Objects.requireNonNull(generator);
        setRenderer(new TextRenderer<>(itemLabelGenerator));
    }

    @NotNull
    public ItemLabelGenerator<T> getItemLabelGenerator() {
        return itemLabelGenerator;
    }
}
