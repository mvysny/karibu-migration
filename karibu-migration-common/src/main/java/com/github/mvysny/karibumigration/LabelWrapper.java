package com.github.mvysny.karibumigration;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.customfield.CustomField;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

/**
 * Used to show a label on top of a component which doesn't show a <code>Component.label</code> itself.
 * <p></p>
 * <strong>Used in highly specific situations only:</strong> for example if you need to add labeled
 * components into a <code>HorizontalLayout</code>.
 * <p></p>
 * Whenever possible, you should add your components into a <code>FormLayout</code>
 * instead, via the `FormLayout.addFormItem()` which
 * supports labels. Alternatively, you can emulate labels by wrapping labels in a `H2`/`H3`/`H4`/`H5`/`H6`
 * then styling them accordingly.
 * <p></p>
 * By default wraps content (width is null), but it attunes itself to the width of its first child:
 * if first child's width is set to percentage then the wrapper sets its width to 100%.
 * Vice versa, if this component is set to 100%, its children are updated accordingly.
 */
public class LabelWrapper extends CustomField<Void> implements HasComponents, HasSize {
    public LabelWrapper() {
        this(null);
    }

    public LabelWrapper(@Nullable String label) {
        this(label, null);
    }

    public LabelWrapper(@Nullable String label, @Nullable Component content) {
        setLabel(label);
        if (content != null) {
            add(content);
        }
    }

    @Override
    public void setWidth(String width) {
        super.setWidth(width);
        updateContentSize();
    }

    private void updateContentSize() {
        getChildren().forEach(child -> {
            if (child instanceof HasSize) {
                if (isFillParent(this) && !isFillParent(child)) {
                    ((HasSize) child).setWidthFull();
                } else if (!isFillParent(this) && isFillParent(child)) {
                    ((HasSize) child).setWidth(null);
                }
            }
        });
    }

    private static boolean isFillParent(@NotNull Component component) {
        if (component instanceof HasSize) {
            return ((HasSize) component).getWidthUnit().orElse(null) == Unit.PERCENTAGE;
        }
        return false;
    }

    @Override
    protected Void generateModelValue() {
        // ignore; this component only serves to add a label to the wrapped contents
        return null;
    }

    @Override
    protected void setPresentationValue(Void newPresentationValue) {
        // ignore; this component only serves to add a label to the wrapped contents
    }

    @Override
    public void add(Component... components) {
        super.add(components);
        if (Arrays.stream(components).anyMatch(LabelWrapper::isFillParent)) {
            setWidthFull();
        }
    }

    @Override
    public void remove(Component... components) {
        super.remove(components);
    }
}
