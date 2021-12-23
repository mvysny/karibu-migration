package com.github.mvysny.karibumigration

import com.github.mvysny.dynatest.DynaTest
import com.github.mvysny.kaributesting.v10.*
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.html.Label
import com.vaadin.flow.component.html.Span
import com.vaadin.flow.component.tabs.Tab
import kotlin.test.expect
import kotlin.test.fail

class TabSheetTest : DynaTest({
    beforeEach { MockVaadin.setup() }
    afterEach { MockVaadin.tearDown() }

    test("smoke") {
        UI.getCurrent().add(TabSheet())
        _expectOne<TabSheet>()
    }

    group("content population") {
        test("Initially empty") {
            val ts = TabSheet()
            ts._expectNone<Tab>()
        }
        test("Adding a tab to an empty TabSheet shows it immediately") {
            val ts = TabSheet()
            ts.addTab("foo", Span("it works!"))
            ts._expectOne<Span> { text = "it works!" }
        }
        test("Adding a tab to a non-empty TabSheet doesn't change the currently shown tab") {
            val ts = TabSheet()
            ts.addTab("foo", Span("it works!"))
            ts.addTab("bar", Span("it works 2!"))
            ts._expectOne<Span> { text = "it works!" }
            ts._expectNone<Span>() { text = "it works 2!" }
        }
        test("Removing last tab clears the TabSheet contents") {
            val ts = TabSheet()
            val tab = ts.addTab("foo", Span("it works!"))
            ts.remove(tab)
            ts._expectNone<Span> { text = "it works!" }
        }
        test("Removing all tabs clears the TabSheet contents") {
            val ts = TabSheet()
            ts.addTab("foo", Span("it works!"))
            ts.removeAll()

            ts._expectNone<Span> { text = "it works!" }
        }
    }

    group("tabCount") {
        test("zero when empty") {
            expect(0) { TabSheet().tabCount }
        }
        test("adding 1 tab") {
            val ts = TabSheet()
            ts.addTab("foo", Span("it works!"))
            expect(1) { ts.tabCount }
        }
        test("two tabs") {
            val ts = TabSheet()
            ts.addTab("foo", Span("it works!"))
            ts.addTab("bar", Span("it works 2!"))
            expect(2) { ts.tabCount }
        }
        test("10 tabs") {
            val ts = TabSheet()
            (0..9).map { ts.addTab("tab $it") }
            expect(10) { ts.tabCount }
        }
        test("Removing last tab brings count to 0") {
            val ts = TabSheet()
            val tab = ts.addTab("foo", Span("it works!"))
            ts.remove(tab)
            expect(0) { ts.tabCount }
        }
        test("Removing all tabs brings count to 0") {
            val ts = TabSheet()
            val tab = ts.addTab("foo", Span("it works!"))
            ts.removeAll()
            expect(0) { ts.tabCount }
        }
        test("Adding a tab with null contents works") {
            val ts = TabSheet()
            ts.addTab("foo")
            expect(1) { ts.tabCount }
        }
        test("Adding a tab with null label+contents works") {
            val ts = TabSheet()
            ts.addTab()
            expect(1) { ts.tabCount }
        }
        test("Add a lazy tab") {
            val ts = TabSheet()
            ts.addLazyTab("foo") { Span("foo") }
            expect(1) { ts.tabCount }
        }
    }

    group("selectedIndex") {
        test("-1 when empty") {
            expect(-1) { TabSheet().selectedIndex }
        }
        test("Adding a tab to an empty TabSheet selects it immediately") {
            val ts = TabSheet()
            ts.addTab("foo", Span("it works!"))
            expect(0) { ts.selectedIndex }
        }
        test("Adding a tab to a non-empty TabSheet doesn't change the selection") {
            val ts = TabSheet()
            ts.addTab("foo", Span("it works!"))
            ts.addTab("bar", Span("it works 2!"))
            expect(0) { ts.selectedIndex }
        }
        test("Removing last tab clears selection") {
            val ts = TabSheet()
            val tab = ts.addTab("foo", Span("it works!"))
            ts.remove(tab)
            expect(-1) { ts.selectedIndex }
        }
        test("Removing all tabs clears selection") {
            val ts = TabSheet()
            ts.addTab("foo", Span("it works!"))
            ts.removeAll()
            expect(-1) { ts.selectedIndex }
        }
        test("Adding a tab with null contents works") {
            val ts = TabSheet()
            ts.addTab("foo")
            expect(0) { ts.selectedIndex }
        }
    }

    group("selectedTab") {
        test("null when empty") {
            expect(null) { TabSheet().selectedTab }
        }
        test("Adding a tab to an empty TabSheet selects it immediately") {
            val ts = TabSheet()
            val tab = ts.addTab("foo", Span("it works!"))
            expect(tab) { ts.selectedTab }
        }
        test("Adding a tab to a non-empty TabSheet doesn't change the selection") {
            val ts = TabSheet()
            val tab = ts.addTab("foo", Span("it works!"))
            ts.addTab("bar", Span("it works 2!"))
            expect(tab) { ts.selectedTab }
        }
        test("Removing last tab clears selection") {
            val ts = TabSheet()
            val tab = ts.addTab("foo", Span("it works!"))
            ts.remove(tab)
            expect(null) { ts.selectedTab }
        }
        test("Removing all tabs clears selection") {
            val ts = TabSheet()
            ts.addTab("foo", Span("it works!"))
            ts.removeAll()
            expect(null) { ts.selectedTab }
        }
        test("Adding a tab with null contents works") {
            val ts = TabSheet()
            val tab = ts.addTab("foo")
            expect(tab) { ts.selectedTab }
        }
        test("Add lazy tab") {
            val ts = TabSheet()
            val tab = ts.addLazyTab("foo") { Span("it works!") }
            expect(tab) { ts.selectedTab }
        }
    }

    group("selectedTabContents") {
        test("null when empty") {
            expect(null) { TabSheet().selectedTabContents }
        }
        test("Adding a tab to an empty TabSheet selects it immediately") {
            val ts = TabSheet()
            val c = Span("it works!")
            val tab = ts.addTab("foo", c)
            expect(c) { ts.selectedTabContents }
        }
        test("Adding a tab to a non-empty TabSheet doesn't change the selection") {
            val ts = TabSheet()
            val c = Span("it works!")
            val tab = ts.addTab("foo", c)
            ts.addTab("bar", Span("it works 2!"))
            expect(c) { ts.selectedTabContents }
        }
        test("Removing last tab clears selection") {
            val ts = TabSheet()
            val tab = ts.addTab("foo", Span("it works!"))
            ts.remove(tab)
            expect(null) { ts.selectedTabContents }
        }
        test("Removing all tabs clears selection") {
            val ts = TabSheet()
            ts.addTab("foo", Span("it works!"))
            ts.removeAll()
            expect(null) { ts.selectedTabContents }
        }
        test("Adding a tab with null contents works") {
            val ts = TabSheet()
            val tab = ts.addTab("foo")
            expect(null) { ts.selectedTabContents }
        }
        test("Add lazy tab") {
            val ts = TabSheet()
            val c = Span("it works!")
            val tab = ts.addLazyTab("foo") { c }
            expect(c) { ts.selectedTabContents }
        }
    }

    group("tabs") {
        test("empty when no tabs") {
            expectList() { TabSheet().tabs }
        }
        test("adding 1 tab") {
            val ts = TabSheet()
            val tab = ts.addTab("foo", Span("it works"))
            expectList(tab) { ts.tabs }
        }
        test("two tabs") {
            val ts = TabSheet()
            val tab = ts.addTab("foo", Span("it works"))
            val tab2 = ts.addTab("bar", Span("it works 2"))
            expectList(tab, tab2) { ts.tabs }
        }
        test("10 tabs") {
            val ts = TabSheet()
            val tabs = (0..9).map { ts.addTab("tab $it") }
            expect(tabs) { ts.tabs }
        }
        test("Removing last tab clears the tab list") {
            val ts = TabSheet()
            val tab = ts.addTab("foo", Span("it works"))
            ts.remove(tab)
            expectList() { ts.tabs }
        }
        test("Removing all tabs clears the tab list") {
            val ts = TabSheet()
            val tab = ts.addTab("foo", Span("it works"))
            ts.removeAll()
            expectList() { ts.tabs }
        }
        test("Adding a tab with null contents adds the tab") {
            val ts = TabSheet()
            val tab = ts.addTab("foo")
            expectList(tab) { ts.tabs }
        }
        test("Adding lazy tabs") {
            val ts = TabSheet()
            val tabs = (0..9).map { ts.addLazyTab("lazy $it") { Span("foo") } }
            expect(tabs) { ts.tabs }
        }
    }

    group("tab contents") {
        test("non-empty contents") {
            val ts = TabSheet()
            val tab = ts.addTab("foo", Span("it works"))
            expect<Class<*>>(Span::class.java) { ts.getTabContents(tab)!!.javaClass }
        }

        test("clearing contents") {
            val ts = TabSheet()
            val tab = ts.addTab("foo", Span("it works"))
            expect<Class<*>>(Span::class.java) { ts.getTabContents(tab)!!.javaClass }
            ts.setTabContents(tab, null)
            expect(null) { ts.getTabContents(tab) }
            ts._expectNone<Span>()
        }
    }
    group("find contents") {
        test("empty tab") {
            val ts = TabSheet()
            val tab = ts.addTab("foo")
            expect(null) { ts.findTabWithContents(Span("bar")) }
        }
        
        test("simple test") {
            val ts = TabSheet()
            val c = Span("contents")
            val tab = ts.addTab("foo", c)
            expect(tab) { ts.findTabWithContents(c) }
        }
    }

    group("lazy tabs") {
        test("addFirstLazyTabImmediatelyInvokesClosure") {
            val ts = TabSheet()
            val producedLabel = Label("baz")
            ts.addLazyTab("foo") { producedLabel }
            expect(producedLabel) { ts.getTabContents(ts.selectedTab!!) }
            expect(producedLabel) { ts.selectedTabContents }
            expect(producedLabel) { ts._get<Label>() }
        }

        test("addSecondLazyTabDelaysClosure") {
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

        test("lazy tab computed exactly once") {
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

    group("indexOf") {
        test("0 for 1st tab") {
            val ts = TabSheet()
            val tab = ts.addTab("foo", Span("bar"))
            expect(0) { ts.indexOf(tab) }
        }
        test("two tabs") {
            val ts = TabSheet()
            val tab1 = ts.addTab("foo", Span("it works"))
            val tab2 = ts.addTab("bar", Span("it works 2"))
            expect(0) { ts.indexOf(tab1) }
            expect(1) { ts.indexOf(tab2) }
        }
    }
})
