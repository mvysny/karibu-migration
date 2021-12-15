package com.github.mvysny.karibumigration

import com.github.mvysny.dynatest.DynaTest

class GridLayoutTest : DynaTest({
    test("smoke") {
        GridLayout()
        GridLayout(1)
        GridLayout(3, 5)
    }
})
