package com.github.mvysny.karibumigration;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.function.SerializableSupplier;
import com.vaadin.flow.shared.Registration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class TabSheet extends Composite<Component> implements HasStyle, HasSize {
    /**
     * Maps {@link Tab} to the contents of the tab.
     */
    @NotNull
    private final Map<Tab, Component> tabsToContents = new HashMap<>();

    /**
     * Maps {@link Tab} to the provider of the contents of the tab.
     */
    @NotNull
    private final Map<Tab, SerializableSupplier<? extends Component>> tabsToContentProvider = new HashMap<>();
    @NotNull
    private final VerticalLayout content = new VerticalLayout();
    @NotNull
    private final Tabs tabsComponent = new Tabs();
    @NotNull
    private final Div tabsContainer = new Div();

    public TabSheet() {
        content.setPadding(false);
        content.setSpacing(false);
        content.setWidthFull();
        content.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.STRETCH);
        content.setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        content.addClassName("tabsheet");

        tabsContainer.setWidthFull();
        // when TabSheet's height is defined, the following rules allow the container to grow or shrink as necessary.
        tabsContainer.getElement().getStyle().set("flexGrow", "1");
        tabsContainer.getElement().getStyle().set("flexShrink", "1");
        tabsContainer.setMinHeight("0");
        tabsContainer.addClassName("tabsheet-container");

        content.add(tabsComponent, tabsContainer);

        tabsComponent.addSelectedChangeListener(e -> update());
    }

    @Override
    @NotNull
    protected Component initContent() {
        return content;
    }

    /**
     * Adds a new tab to the tab host, with optional <code>label</code> and optional <code>contents</code>.
     * <p></p>
     * You can either provide the tab contents eagerly, or you can populate the tab
     * later on, by calling {@link #setTabContents}. To make the tab populate itself automatically when it's shown
     * for the first time, use {@link #addLazyTab(String, SerializableSupplier)}.
     */
    @NotNull
    public Tab addTab(@Nullable String label, @Nullable Component contents) {
        final Tab tab = new Tab(label);
        tabsComponent.add(tab);
        tabsToContents.put(tab, contents);
        update();
        return tab;
    }

    /**
     * Adds a new tab to the tab host, with optional <code>label</code> and no <code>contents</code>.
     * <p></p>
     * You can either provide the tab contents eagerly, or you can populate the tab
     * later on, by calling {@link #setTabContents}. To make the tab populate itself automatically when it's shown
     * for the first time, use {@link #addLazyTab(String, SerializableSupplier)}.
     */
    @NotNull
    public Tab addTab(@Nullable String label) {
        return addTab(label, null);
    }

    /**
     * Adds a new tab to the tab host, with no <code>label</code> and optional <code>contents</code>.
     * <p></p>
     * You can either provide the tab contents eagerly, or you can populate the tab
     * later on, by calling {@link #setTabContents}. To make the tab populate itself automatically when it's shown
     * for the first time, use {@link #addLazyTab(String, SerializableSupplier)}.
     */
    @NotNull
    public Tab addTab(@Nullable Component contents) {
        return addTab(null, contents);
    }

    /**
     * Adds a new tab to the tab host, with no <code>label</code> and no <code>contents</code>.
     * <p></p>
     * You can either provide the tab contents eagerly, or you can populate the tab
     * later on, by calling {@link #setTabContents}. To make the tab populate itself automatically when it's shown
     * for the first time, use {@link #addLazyTab(String, SerializableSupplier)}.
     */
    @NotNull
    public Tab addTab() {
        return addTab(null, null);
    }

    /**
     * Adds a new tab to the tab host, with optional <code>label</code>. The tab contents is
     * constructed lazily when the tab is selected for the first time.
     */
    @NotNull
    public Tab addLazyTab(@Nullable String label, @NotNull SerializableSupplier<? extends Component> contentsProvider) {
        Objects.requireNonNull(contentsProvider);
        final Tab tab = new Tab(label);
        tabsComponent.add(tab);
        tabsToContents.put(tab, null);
        tabsToContentProvider.put(tab, contentsProvider);
        update();
        return tab;
    }


    /**
     * Adds a new tab to the tab host, with no <code>label</code>. The tab contents is
     * constructed lazily when the tab is selected for the first time.
     */
    @NotNull
    public Tab addLazyTab(@NotNull SerializableSupplier<? extends Component> contentsProvider) {
        return addLazyTab(null, contentsProvider);
    }

    /**
     * Sets the contents of given tab to newContents.
     */
    public void setTabContents(@NotNull Tab tab, @Nullable Component newContents) {
        checkOurTab(tab);
        tabsToContents.put(tab, newContents);
        tabsToContentProvider.remove(tab);
        update();
    }

    /**
     * Finds a tab containing given contents. Returns null if there is no
     * such tab.
     */
    @Nullable
    public Tab findTabWithContents(@NotNull Component contents) {
        Objects.requireNonNull(contents);
        return tabsToContents.entrySet().stream()
                .filter(it -> it.getValue().equals(contents))
                .map(it -> it.getKey())
                .findAny()
                .orElse(null);
    }

    /**
     * Returns the contents of given tab. May return null if the tab has no contents,
     * or the tab has lazy contents and hasn't been displayed yet.
     */
    @Nullable
    public Component getTabContents(@NotNull Tab tab) {
        checkOurTab(tab);
        return tabsToContents.get(tab);
    }

    private void checkOurTab(@NotNull Tab tab) {
        Objects.requireNonNull(tab);
        if (!tabsToContents.containsKey(tab)) {
            throw new IllegalArgumentException("Parameter tab: invalid value " + tab + ": not hosted in this TabSheet");
        }
    }

    /**
     * Removes a tab. If the tab is selected, another tab is selected automatically (if possible).
     */
    public void remove(@NotNull Tab tab) {
        tabsToContents.remove(tab);
        tabsComponent.remove(tab);
        update();
    }

    /**
     * Currently selected tab. Defaults to null since by default there are no tabs.
     */
    @Nullable
    public Tab getSelectedTab() {
        return tabsComponent.getSelectedTab();
    }

    /**
     * Currently selected tab. Defaults to null since by default there are no tabs.
     */
    public void setSelectedTab(@Nullable Tab tab) {
        tabsComponent.setSelectedTab(tab);
    }

    /**
     * Returns the 0-based index of the currently selected tab. -1 if no tab is selected.
     */
    public int getSelectedIndex() {
        return tabsComponent.getSelectedIndex();
    }

    /**
     * Returns the 0-based index of the currently selected tab. -1 if no tab is selected.
     */
    public void setSelectedIndex(int index) {
        tabsComponent.setSelectedIndex(index);
    }

    /**
     * Returns the current number of tabs.
     */
    public int getTabCount() {
        return tabsToContents.keySet().size();
    }

    /**
     * Gets the orientation of this tab sheet.
     *
     * @return the orientation
     */
    @NotNull
    public Tabs.Orientation getOrientation() {
        return tabsComponent.getOrientation();
    }

    /**
     * Sets the orientation of this tab sheet.
     *
     * @param orientation
     *            the orientation
     */
    public void setOrientation(@NotNull Tabs.Orientation orientation) {
        tabsComponent.setOrientation(orientation);
    }

    @Nullable
    private Component getSelectedTabContent() {
        return tabsContainer.getChildren().findFirst().orElse(null);
    }

    private void update() {
        final Component currentTabContent = getSelectedTabContent();
        final Tab selectedTab1 = getSelectedTab();

        Component newTabContent;
        if (selectedTab1 == null) {
            newTabContent = null;
        } else {
            newTabContent = tabsToContents.get(selectedTab1);
            if (newTabContent == null) {
                SerializableSupplier<? extends Component> provider = tabsToContentProvider.get(selectedTab1);
                if (provider != null) {
                    newTabContent = provider.get();
                    Objects.requireNonNull(newTabContent, "content provider for tab " + selectedTab1 + " provided null contents: " + provider);
                    tabsToContentProvider.remove(selectedTab1);
                    tabsToContents.put(selectedTab1, newTabContent);
                }
            }
        }

        if (!Objects.equals(currentTabContent, newTabContent)) {
            tabsContainer.removeAll();
            if (newTabContent != null) {
                tabsContainer.add(newTabContent);
            }
        }
    }

    /**
     * Removes all tabs.
     */
    public void removeAll() {
        tabsToContents.clear();
        tabsComponent.removeAll();
        update();
    }

    /**
     * A live list of all tabs. The list is read-only but live: it reflects changes when tabs are added or removed.
     */
    @NotNull
    private final List<Tab> tabs = new AbstractList<Tab>() {
        @Override
        public Tab get(int index) {
            return (Tab) tabsComponent.getComponentAt(index);
        }

        @Override
        public int size() {
            return getTabCount();
        }
    };

    /**
     * Returns a live list of all tabs. The list is read-only but live: it reflects changes when tabs are added or removed.
     */
    @NotNull
    public List<Tab> getTabs() {
        return tabs;
    }

    @NotNull
    public Registration addSelectedChangeListener(@NotNull ComponentEventListener<Tabs.SelectedChangeEvent> listener) {
        return tabsComponent.addSelectedChangeListener(listener);
    }
}
