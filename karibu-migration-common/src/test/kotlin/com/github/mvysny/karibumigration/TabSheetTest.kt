package com.github.mvysny.karibumigration

import com.github.mvysny.kaributesting.v10.*
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.html.Label
import com.vaadin.flow.component.html.Span
import com.vaadin.flow.component.tabs.Tab
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.expect
import kotlin.test.fail

class TabSheetTest {
    @BeforeEach fun fakeVaadin() { MockVaadin.setup() }
    @AfterEach fun tearDownVaadin() { MockVaadin.tearDown() }

    @Test fun smoke() {
        UI.getCurrent().add(TabSheet())
        _expectOne<TabSheet>()
    }

    @Nested inner class `content population` {
        @Test fun `Initially empty`() {
            val ts = TabSheet()
            ts._expectNone<Tab>()
        }
        @Test fun `Adding a tab to an empty TabSheet shows it immediately`() {
            val ts = TabSheet()
            ts.addTab("foo", Span("it works!"))
            ts._expectOne<Span> { text = "it works!" }
        }
        @Test fun `Adding a tab to a non-empty TabSheet doesn't change the currently shown tab`() {
            val ts = TabSheet()
            ts.addTab("foo", Span("it works!"))
            ts.addTab("bar", Span("it works 2!"))
            ts._expectOne<Span> { text = "it works!" }
            ts._expectNone<Span>() { text = "it works 2!" }
        }
        @Test fun `Removing last tab clears the TabSheet contents`() {
            val ts = TabSheet()
            val tab = ts.addTab("foo", Span("it works!"))
            ts.remove(tab)
            ts._expectNone<Span> { text = "it works!" }
        }
        @Test fun `Removing all tabs clears the TabSheet contents`() {
            val ts = TabSheet()
            ts.addTab("foo", Span("it works!"))
            ts.removeAll()

            ts._expectNone<Span> { text = "it works!" }
        }
    }

    @Nested inner class tabCount {
        @Test fun `zero when empty`() {
            expect(0) { TabSheet().tabCount }
        }
        @Test fun `adding 1 tab`() {
            val ts = TabSheet()
            ts.addTab("foo", Span("it works!"))
            expect(1) { ts.tabCount }
        }
        @Test fun `two tabs`() {
            val ts = TabSheet()
            ts.addTab("foo", Span("it works!"))
            ts.addTab("bar", Span("it works 2!"))
            expect(2) { ts.tabCount }
        }
        @Test fun `10 tabs`() {
            val ts = TabSheet()
            (0..9).map { ts.addTab("tab $it") }
            expect(10) { ts.tabCount }
        }
        @Test fun `Removing last tab brings count to 0`() {
            val ts = TabSheet()
            val tab = ts.addTab("foo", Span("it works!"))
            ts.remove(tab)
            expect(0) { ts.tabCount }
        }
        @Test fun `Removing all tabs brings count to 0`() {
            val ts = TabSheet()
            val tab = ts.addTab("foo", Span("it works!"))
            ts.removeAll()
            expect(0) { ts.tabCount }
        }
        @Test fun `Adding a tab with null contents works`() {
            val ts = TabSheet()
            ts.addTab("foo")
            expect(1) { ts.tabCount }
        }
        @Test fun `Adding a tab with null label+contents works`() {
            val ts = TabSheet()
            ts.addTab()
            expect(1) { ts.tabCount }
        }
        @Test fun `Add a lazy tab`() {
            val ts = TabSheet()
            ts.addLazyTab("foo") { Span("foo") }
            expect(1) { ts.tabCount }
        }
    }

    @Nested inner class selectedIndex {
        @Test fun `-1 when empty`() {
            expect(-1) { TabSheet().selectedIndex }
        }
        @Test fun `Adding a tab to an empty TabSheet selects it immediately`() {
            val ts = TabSheet()
            ts.addTab("foo", Span("it works!"))
            expect(0) { ts.selectedIndex }
        }
        @Test fun `Adding a tab to a non-empty TabSheet doesn't change the selection`() {
            val ts = TabSheet()
            ts.addTab("foo", Span("it works!"))
            ts.addTab("bar", Span("it works 2!"))
            expect(0) { ts.selectedIndex }
        }
        @Test fun `Selecting a tab will properly display it`() {
            val ts = TabSheet()
            ts.addTab("foo", Span("it works!"))
            ts.addTab("bar", Span("it works 2!"))
            ts.selectedIndex = 1
            expect(1) { ts.selectedIndex }
            ts._expectOne<Span> { text = "it works 2!" }
            ts._expectNone<Span> { text = "it works!" }
            ts.selectedIndex = 0
            expect(0) { ts.selectedIndex }
            ts._expectNone<Span> { text = "it works 2!" }
            ts._expectOne<Span> { text = "it works!" }
        }
        @Test fun `Removing last tab clears selection`() {
            val ts = TabSheet()
            val tab = ts.addTab("foo", Span("it works!"))
            ts.remove(tab)
            expect(-1) { ts.selectedIndex }
        }
        @Test fun `Removing all tabs clears selection`() {
            val ts = TabSheet()
            ts.addTab("foo", Span("it works!"))
            ts.removeAll()
            expect(-1) { ts.selectedIndex }
        }
        @Test fun `Adding a tab with null contents works`() {
            val ts = TabSheet()
            ts.addTab("foo")
            expect(0) { ts.selectedIndex }
        }
        @Test fun `Selecting no tab`() {
            val ts = TabSheet()
            ts.addTab("foo", Span("it works!"))
            ts.selectedIndex = -1
            expect(-1) { ts.selectedIndex }
            ts._expectNone<Span> { text = "it works!" }
        }
    }

    @Nested inner class selectedTab {
        @Test fun `null when empty`() {
            expect(null) { TabSheet().selectedTab }
        }
        @Test fun `Adding a tab to an empty TabSheet selects it immediately`() {
            val ts = TabSheet()
            val tab = ts.addTab("foo", Span("it works!"))
            expect(tab) { ts.selectedTab }
        }
        @Test fun `Adding a tab to a non-empty TabSheet doesn't change the selection`() {
            val ts = TabSheet()
            val tab = ts.addTab("foo", Span("it works!"))
            ts.addTab("bar", Span("it works 2!"))
            expect(tab) { ts.selectedTab }
        }
        @Test fun `Removing last tab clears selection`() {
            val ts = TabSheet()
            val tab = ts.addTab("foo", Span("it works!"))
            ts.remove(tab)
            expect(null) { ts.selectedTab }
        }
        @Test fun `Removing all tabs clears selection`() {
            val ts = TabSheet()
            ts.addTab("foo", Span("it works!"))
            ts.removeAll()
            expect(null) { ts.selectedTab }
        }
        @Test fun `Adding a tab with null contents works`() {
            val ts = TabSheet()
            val tab = ts.addTab("foo")
            expect(tab) { ts.selectedTab }
        }
        @Test fun `Add lazy tab`() {
            val ts = TabSheet()
            val tab = ts.addLazyTab("foo") { Span("it works!") }
            expect(tab) { ts.selectedTab }
        }
        @Test fun `Selecting a tab will properly display it`() {
            val ts = TabSheet()
            val tab1 = ts.addTab("foo", Span("it works!"))
            val tab2 = ts.addTab("bar", Span("it works 2!"))
            ts.selectedTab = tab2
            expect(tab2) { ts.selectedTab }
            ts._expectOne<Span> { text = "it works 2!" }
            ts._expectNone<Span> { text = "it works!" }
            ts.selectedTab = tab1
            expect(tab1) { ts.selectedTab }
            ts._expectNone<Span> { text = "it works 2!" }
            ts._expectOne<Span> { text = "it works!" }
        }
        @Test fun `Selecting no tab`() {
            val ts = TabSheet()
            ts.addTab("foo", Span("it works!"))
            ts.selectedTab = null
            expect(null) { ts.selectedTab }
            ts._expectNone<Span> { text = "it works!" }
        }
    }

    @Nested inner class selectedTabContents {
        @Test fun `null when empty`() {
            expect(null) { TabSheet().selectedTabContents }
        }
        @Test fun `Adding a tab to an empty TabSheet selects it immediately`() {
            val ts = TabSheet()
            val c = Span("it works!")
            val tab = ts.addTab("foo", c)
            expect(c) { ts.selectedTabContents }
        }
        @Test fun `Adding a tab to a non-empty TabSheet doesn't change the selection`() {
            val ts = TabSheet()
            val c = Span("it works!")
            val tab = ts.addTab("foo", c)
            ts.addTab("bar", Span("it works 2!"))
            expect(c) { ts.selectedTabContents }
        }
        @Test fun `Removing last tab clears selection`() {
            val ts = TabSheet()
            val tab = ts.addTab("foo", Span("it works!"))
            ts.remove(tab)
            expect(null) { ts.selectedTabContents }
        }
        @Test fun `Removing all tabs clears selection`() {
            val ts = TabSheet()
            ts.addTab("foo", Span("it works!"))
            ts.removeAll()
            expect(null) { ts.selectedTabContents }
        }
        @Test fun `Adding a tab with null contents works`() {
            val ts = TabSheet()
            val tab = ts.addTab("foo")
            expect(null) { ts.selectedTabContents }
        }
        @Test fun `Add lazy tab`() {
            val ts = TabSheet()
            val c = Span("it works!")
            val tab = ts.addLazyTab("foo") { c }
            expect(c) { ts.selectedTabContents }
        }
    }

    @Nested inner class tabs {
        @Test fun `empty when no tabs`() {
            expectList() { TabSheet().tabs }
        }
        @Test fun `adding 1 tab`() {
            val ts = TabSheet()
            val tab = ts.addTab("foo", Span("it works"))
            expectList(tab) { ts.tabs }
        }
        @Test fun `two tabs`() {
            val ts = TabSheet()
            val tab = ts.addTab("foo", Span("it works"))
            val tab2 = ts.addTab("bar", Span("it works 2"))
            expectList(tab, tab2) { ts.tabs }
        }
        @Test fun `10 tabs`() {
            val ts = TabSheet()
            val tabs = (0..9).map { ts.addTab("tab $it") }
            expect(tabs) { ts.tabs }
        }
        @Test fun `Removing last tab clears the tab list`() {
            val ts = TabSheet()
            val tab = ts.addTab("foo", Span("it works"))
            ts.remove(tab)
            expectList() { ts.tabs }
        }
        @Test fun `Removing all tabs clears the tab list`() {
            val ts = TabSheet()
            val tab = ts.addTab("foo", Span("it works"))
            ts.removeAll()
            expectList() { ts.tabs }
        }
        @Test fun `Adding a tab with null contents adds the tab`() {
            val ts = TabSheet()
            val tab = ts.addTab("foo")
            expectList(tab) { ts.tabs }
        }
        @Test fun `Adding lazy tabs`() {
            val ts = TabSheet()
            val tabs = (0..9).map { ts.addLazyTab("lazy $it") { Span("foo") } }
            expect(tabs) { ts.tabs }
        }
    }

    @Nested inner class `tab contents` {
        @Test fun `non-empty contents`() {
            val ts = TabSheet()
            val tab = ts.addTab("foo", Span("it works"))
            expect<Class<*>>(Span::class.java) { ts.getTabContents(tab)!!.javaClass }
        }

        @Test fun `clearing contents`() {
            val ts = TabSheet()
            val tab = ts.addTab("foo", Span("it works"))
            expect<Class<*>>(Span::class.java) { ts.getTabContents(tab)!!.javaClass }
            ts.setTabContents(tab, null)
            expect(null) { ts.getTabContents(tab) }
            ts._expectNone<Span>()
        }
    }
    @Nested inner class `find contents` {
        @Test fun `empty tab`() {
            val ts = TabSheet()
            val tab = ts.addTab("foo")
            expect(null) { ts.findTabWithContents(Span("bar")) }
        }
        
        @Test fun `simple test`() {
            val ts = TabSheet()
            val c = Span("contents")
            val tab = ts.addTab("foo", c)
            expect(tab) { ts.findTabWithContents(c) }
        }
    }

    @Nested inner class `lazy tabs`() {
        @Test fun addFirstLazyTabImmediatelyInvokesClosure() {
            val ts = TabSheet()
            val producedLabel = Label("baz")
            ts.addLazyTab("foo") { producedLabel }
            expect(producedLabel) { ts.getTabContents(ts.selectedTab!!) }
            expect(producedLabel) { ts.selectedTabContents }
            expect(producedLabel) { ts._get<Label>() }
        }

        @Test fun addSecondLazyTabDelaysClosure() {
            val ts = TabSheet()
            val producedLabel = Label("baz")
            var allowInvoking = false
            val tab1 = ts.addTab("bar")
            val tab2 = ts.addLazyTab("foo") {
                if (!allowInvoking) fail("Should not invoke")
                producedLabel
            }
            expect(tab1) { ts.selectedTab }
            ts._expectNone<Label>()

            allowInvoking = true
            ts.selectedTab = tab2
            expect(tab2) { ts.selectedTab!! }
            expect(producedLabel) { ts.selectedTabContents }
            expect(producedLabel) { ts.getTabContents(ts.selectedTab!!) }
            ts._expectOne<Label>()
        }

        @Test fun `lazy tab computed exactly once`() {
            val ts = TabSheet()
            val tab1 = ts.addTab("bar")
            var callCount = 0
            val tab2 = ts.addLazyTab("foo") {
                if (callCount++ > 1) {
                    fail("Called 2 times")
                }
                Label("foo")
            }

            expect(0) { callCount }
            ts.selectedTab = tab2
            expect(1) { callCount }
            ts.selectedTab = tab1
            expect(1) { callCount }
            ts.selectedTab = tab2
            expect(1) { callCount }
        }
    }

    @Nested inner class indexOf {
        @Test fun `0 for 1st tab`() {
            val ts = TabSheet()
            val tab = ts.addTab("foo", Span("bar"))
            expect(0) { ts.indexOf(tab) }
        }
        @Test fun `two tabs`() {
            val ts = TabSheet()
            val tab1 = ts.addTab("foo", Span("it works"))
            val tab2 = ts.addTab("bar", Span("it works 2"))
            expect(0) { ts.indexOf(tab1) }
            expect(1) { ts.indexOf(tab2) }
        }
    }
}
