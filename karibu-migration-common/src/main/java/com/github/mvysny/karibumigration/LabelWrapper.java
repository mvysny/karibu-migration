package com.github.mvysny.karibumigration;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.customfield.CustomField;

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
 */
public class LabelWrapper extends CustomField<Void> implements HasComponents, HasSize {
    public LabelWrapper() {
        this(null);
    }

    public LabelWrapper(String label) {
        setLabel(label);
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
    }

    @Override
    public void remove(Component... components) {
        super.remove(components);
    }
}
