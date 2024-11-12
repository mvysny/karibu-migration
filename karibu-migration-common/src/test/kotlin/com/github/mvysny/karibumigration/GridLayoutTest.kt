package com.github.mvysny.karibumigration

import org.junit.jupiter.api.Test

class GridLayoutTest {
    @Test fun smoke() {
        GridLayout()
        GridLayout(1)
        GridLayout(3, 5)
    }
}
