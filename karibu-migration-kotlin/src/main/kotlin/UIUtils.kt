package com.github.mvysny.karibumigration

import com.vaadin.flow.component.UI
import com.vaadin.flow.server.VaadinRequest

/**
 * Gets the part of path (from browser's URL) that points to this UI.
 * @return the part of path (from browser's URL) that points to this UI,
 *         without possible view identifiers or path parameters
 */
public fun UI.getUiRootPath(): String = VaadinRequest.getCurrent().contextPath

/**
 * No longer necessary: mobile html 5 Drag'n'drop is enabled automatically on Vaadin 14+.
 * Read [Drag and drop on mobile devices](https://vaadin.com/docs/latest/flow/dnd#drag-and-drop-on-mobile-devices)
 * for more info.
 */
public fun UI.setMobileHtml5DndEnabled(enabled: Boolean) {}