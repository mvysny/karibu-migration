package com.github.mvysny.karibumigration

import com.vaadin.flow.component.UI
import com.vaadin.flow.server.VaadinRequest

/**
 * Gets the part of path (from browser's URL) that points to this UI.
 * @return the part of path (from browser's URL) that points to this UI,
 *         without possible view identifiers or path parameters
 */
public fun UI.getUiRootPath(): String = VaadinRequest.getCurrent().contextPath
