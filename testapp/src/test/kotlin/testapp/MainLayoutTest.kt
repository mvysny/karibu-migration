package testapp

import com.github.mvysny.karibumigration.RadioButtonGroupCompat
import com.github.mvysny.kaributesting.v10.MockVaadin
import com.github.mvysny.kaributesting.v10.Routes
import com.github.mvysny.kaributesting.v10._expectOne
import com.github.mvysny.kaributools.navigateTo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

val routes = Routes().autoDiscoverViews("testapp")

class MainLayoutTest {
    @BeforeEach fun fakeVaadin() { MockVaadin.setup(routes) }
    @AfterEach fun tearDownVaadin() { MockVaadin.tearDown() }

    @Test fun smoke() {
        navigateTo<MainLayout>()
        _expectOne<MainLayout>()
        _expectOne<RadioButtonGroupCompat<*>>()
    }
}
