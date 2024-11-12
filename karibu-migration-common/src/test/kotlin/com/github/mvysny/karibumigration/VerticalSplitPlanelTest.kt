@file:Suppress("DEPRECATION")

package com.github.mvysny.karibumigration

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class VerticalSplitPlanelTest {
    @Test fun smoke() {
        VerticalSplitPanel()
    }
    @Nested inner class VerticalSplitPanelTests : AbstractSplitLayoutCompatTests({ VerticalSplitPanel() })
}
