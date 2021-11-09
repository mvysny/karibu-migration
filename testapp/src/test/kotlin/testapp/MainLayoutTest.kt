package testapp

import com.github.mvysny.dynatest.DynaTest
import com.github.mvysny.karibumigration.RadioButtonGroupCompat
import com.github.mvysny.kaributesting.v10.MockVaadin
import com.github.mvysny.kaributesting.v10.Routes
import com.github.mvysny.kaributesting.v10._expectOne
import com.github.mvysny.kaributools.navigateTo

val routes = Routes().autoDiscoverViews("testapp")

class MainLayoutTest : DynaTest({
    beforeEach { MockVaadin.setup(routes) }
    afterEach { MockVaadin.tearDown() }

    test("smoke") {
        navigateTo<MainLayout>()
        _expectOne<MainLayout>()
        _expectOne<RadioButtonGroupCompat<*>>()
    }
})
