# Karibu-Migration: Aids migration from Vaadin 8 to Vaadin 14+

[![GitHub tag](https://img.shields.io/github/tag/mvysny/karibu-migration.svg)](https://github.com/mvysny/karibu-migration/tags)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.mvysny.karibu-migration/karibu-migration-common/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.mvysny.karibu-migration/karibu-migration-common)
[![CI](https://github.com/mvysny/karibu-migration/actions/workflows/gradle.yml/badge.svg)](https://github.com/mvysny/karibu-migration/actions/workflows/gradle.yml)

A collection of extension functions mimicking Vaadin 8 API but redirecting to Vaadin 14+ API where applicable.
This simplifies the process when rewriting Vaadin 8 components/layouts/view to Vaadin 14, since
you can continue using the same Vaadin 8 functions and get useful information and warnings.
For example,
there is `setWidthUndefined()` function marked as deprecated (since it's no longer in Vaadin 14), redirecting to a `setWidth(null)` call.

The jar is in Maven Central, so it's easy to add this library to your project.

Gradle:
```groovy
repositories {
  mavenCentral()
}
dependencies {
  api("com.github.mvysny.karibu-migration:karibu-migration-MODULE:x.y")
}
```

Maven:
```xml
<dependency>
    <groupId>com.github.mvysny.karibu-migration</groupId>
    <artifactId>karibu-migration-MODULE</artifactId>
    <version>x.y</version>
</dependency>
```

See the tag above for the latest version.

There are two modules available:

* The `karibu-migration-common` module is pure Java, doesn't depend on Kotlin and brings in a set of
  compatibility-helping components: `GridLayout`, `HtmlSpan`, `HorizontalSplitPanel`,
  `NativeSelect`, `RadioButtonGroupCompat`, `SplitLayoutCompat` and `VerticalSplitPanel`.
* The `karibu-migration-kotlin` builds on `karibu-migration-common` and adds lots
  of useful Kotlin extension methods to Vaadin 14+ components which emulate Vaadin 8 API.
  If you ever wanted to convert your Vaadin 8 project to Kotlin and clean it up,
  it's smart to do that *before* the migration - you'll gain access to these
  extension methods, and you will not have to convert obsolete crap code.

# Migration Documentation

The official Vaadin documentation on migration:

* [Upgrading from Vaadin 8](https://vaadin.com/docs/v14/guide/upgrading/v8) lists general strategies
* [Replacement components](https://vaadin.com/docs/v14/guide/upgrading/v8/5-components) - a list
   of all Vaadin 8 components and their replacement counterparts in Vaadin 14.

Usually, the upgrade is done one of the following ways:

* Employing the [Multiplatform Runtime](https://vaadin.com/docs/v14/tools/mpr/overview) which allows
   you to embed Vaadin 8 components into Vaadin 14 components (but not vice versa). The strategy
   is as follows:
  * Convert your app shell (the main route layout) to Vaadin 14, while nesting all Vaadin 8 views in Vaadin 14
    wrapper components.
  * Convert your views one-by-one to Vaadin 14.
  * For Vaadin8+14 MPR demo project see [Vaadin 14 MPR + Vaadin 8 Gradle Demo Project](https://gitlab.com/mvysny/vaadin14-mpr-gradle-demo);
    Maven version [Tatu Lund's mpr-demo](https://github.com/TatuLund/mpr-demo).
* Adding a secondary Vaadin 14 servlet on classpath, then running Vaadin 8 app alongside
   the new Vaadin 14 app.

In all cases, the application needs to be converted manually, view-by-view, to Vaadin 14.

## Styles

Vaadin 8 apps and components were styled by adding rules to the global SCSS file, then adding styles to individual components.
That is possible with Vaadin 14 as well, but there are very important distinctions:

* Many of the components styles are now applied via theme variants. For example, the `ValoTheme.COMBOBOX_SMALL` is now
  applied via `addThemeVariants(ComboBoxVariant.Small)`; adding the `ValoTheme.COMBOBOX_SMALL` via `addClassName()` would have no effect.
  * To see available theme variants for particular components, find the component at the [Vaadin Components](https://vaadin.com/components)
    page, then go to the "Examples" / "Theme Variants" tab.
* It's not possible to style component internals via a global CSS because of Web component mechanism called the Shadow DOM. Read more at
  [Vaadin official documentation on Style Scopes](https://vaadin.com/docs/v14/flow/styling/style-scopes).
* Other than that, you can still use the global styles if needed. You can't use SCSS though (well you can but then you have to figure out
  how to compile SCSS yourself - there's no direct support for SCSS in Vaadin 14+ anymore), and you have to apply classes via `addClassName()`
  instead of `addStyleName()`.

I really urge you to read the [Styling and Themes](https://vaadin.com/docs/v14/flow/styling/overview) chapter, to familiarize yourself
with the new concepts.

### Lumo VS Valo

Vaadin 8's Valo theme has been replaced by Vaadin 14's Lumo theme. Theme parametrization is done via CSS Variables rather than the SCSS mechanism.
Find more information here:

* The [Lumo documentation](https://vaadin.com/docs/v14/flow/styling/lumo/overview) lists all variables
* The [Lumo Editor](https://demo.vaadin.com/lumo-editor/) offers a great way to play with the variables and see the outcome in realtime.
* See [Vaadin 8 Sampler](https://demo.vaadin.com/sampler/) for the way how Vaadin 8 components looked like with Valo theme
* For a complete list of Vaadin 8 components, along with all ValoTheme variants, see the "UITest" project at
  [karibu-dsl](https://github.com/mvysny/karibu-dsl) page.

# Component-related replacements

## Component and generic

* `HasSize.setWidthUndefined()` is replaced by `setWidth(null)`
* `HasSize.setHeightUndefined()` is replaced by `setHeight(null)`
* `Component.setCaption()` is replaced by `Component.setLabel()` from karibu-tools.
   * Warning: in order for the caption to be visible, the component must be nested in `VerticalLayout`/`HorizontalLayout`/`FlexLayout`.
   * Alternatively, wrap the component in the `LabelWrapper` component decorator from `karibu-migration-common`.
   * See more at [Vaadin 8->14 Counterparts blogpost](https://mvysny.github.io/vaadin-8-14-api-counterparts/)
* `Component.getCaption()` is replaced by `Component.getLabel()` from karibu-tools. 
* `CssLayout` is replaced by a `Div`
* `Component.setDescription()` is replaced by `Component.setTooltip()` from karibu-tools, which
    sets the `tooltip` DOM attribute. However this doesn't support tooltips with HTML;
    you can alternatively use [vcf-tooltip](https://github.com/vaadin-component-factory/vcf-tooltip).
* `Component.getDescription()` is replaced by `Component.getTooltip()` from karibu-tools, which
    reads the `tooltip` DOM attribute.
* Styles:
  * `setStyleName()` replaced by `setClassName`; `addStyleName()` replaced by `addClassName()`;
     `removeStyleName()` replaced by `removeClassName()`
  * `addStyleName(ValoTheme.*)` is generally replaced by `addThemeVariants(XYZVariant.LUMO_*)`
  * **IMPORTANT**: The styles mechanism has been redesigned completely; read the Styles chapter for more information.
* Children:
  * `removeAllComponents()` - replace by `removeAll()`
  * `addComponent()` - replace by `add()`
  * `removeComponent()` - replace by `remove()`

## UI

It is discouraged to have a custom UI class. If this is necessary, it's better to
create a separate utility class and store it into the UI instance via `ComponentUtil.setData(UI.getCurrent(), Util.class, util)`.
Alternatively see [How to correctly extend a UI](https://vaadin.com/forum/thread/18184719/how-to-correctly-extend-a-ui).

* `UI.getUiRootPath()` - prints the context root with the leading slash, e.g. `/Gradle___karibu_helloworld_application_war` and so it’s perfect for creating navigational links. Replaced with `VaadinRequest.getCurrent().getContextPath()`.

## ComboBox

* `setItemCaptionGenerator()` is replaced by `setItemLabelGenerator()`
* use `addThemeVariants(ComboBoxVariant.Small)` instead of `ValoTheme.COMBOBOX_SMALL`.
* Use `addThemeVariants(ComboBoxVariant.AlignRight)` from karibu-tools instead of `ValoTheme.COMBOBOX_ALIGN_RIGHT`
* Use `addThemeVariants(ComboBoxVariant.AlignCenter)` from karibu-tools instead of `ValoTheme.COMBOBOX_ALIGN_CENTER`
* There are no direct replacements for `ValoTheme.COMBOBOX_TINY`, `ValoTheme.COMBOBOX_LARGE`, `ValoTheme.COMBOBOX_HUGE`
  and `ValoTheme.COMBOBOX_BORDERLESS`.
* `setEmptySelectionAllowed()` -  When `allowed` was set to true, the Vaadin 8 ComboBox used to show an additional item
   representing the `null` value. This is hard to emulate via DataProvider; the
   easiest replacement is to simply show or hide the clear button. Also see [Adding support for null values to Vaadin ComboBox](https://mvysny.github.io/vaadin-combobox-null-value/)
   for more details.
* Given that, replace `setEmptySelectionCaption()` with `setPlaceholder()`.
* `setTextInputAllowed()` - no replacement. ComboBox always allows text input. Use `Select` instead
   if you want to disable text input.
* `scrollToSelectedItem()` - no replacement as of today. Please open a feature request at [flow-components/issues](https://github.com/vaadin/flow-components).

Also take a look at the [ComboBox with Vaadin 8 behavior](https://vaadin.com/directory/component/combo-box-with-vaadin-8-behavior/overview)
which closely mimics Vaadin 8 ComboBox.

## NativeSelect -> Select

* `setItemCaptionGenerator()` is replaced by `setItemLabelGenerator()`

There's also `NativeSelect` Vaadin 14 component for your convenience, which simply
extends Vaadin 14's Select but provides all constructors from Vaadin 8's `NativeSelect`.

## CheckBoxGroup

* `setItemCaptionGenerator()` is replaced by `setItemLabelGenerator()`

## RadioButtonGroup

* `setItemCaptionGenerator()` is replaced by `setItemLabelGenerator()` from karibu-tools
* No replacement for `OPTIONGROUP_SMALL` and `OPTIONGROUP_LARGE`
* RadioButtonGroup is horizontal by default; use `addThemeVariants(RadioGroupVariant.LUMO_VERTICAL)`
  to lay out buttons horizontally in absence of `ValoTheme.OPTIONGROUP_HORIZONTAL`.

This project provides the `RadioButtonGroupCompat` class which introduces a better Vaadin 8 compatibility:

* `setItemDescriptionGenerator()`
* `setHtmlContentAllowed()`
* It is vertical by default; also adds `setVertical(boolean)` utility setter.

### Individual RadioButton

It's not possible to have a single RadioButton just as it's not possible with Vaadin 8. See+vote for [issue 1952](https://github.com/vaadin/flow-components/issues/1952).
Workaround is to have a RadioButtonGroup with just one item.

## Grid

Grid:

* `isSelectionAllowed()` - replace with `isSelectionAllowed` from karibu-tools. Essentially
   check if the selection model is either `SelectionModel.Multi` or `SelectionModel.Single`.
* `Grid.addFooterRowAt(index)` - no replacement; the extension function calls either `prependFooterRow()` or `appendFooterRow()`.
* `Grid.getFooterRowCount()` - replace with `getFooterRows().size()`.
* `Grid.getHeaderRowCount()` - replace with `getHeaderRows().size()`.
* `Grid.setExpandRatio()` - replace with `setFlexGrow()`.

Grid.Column:

* `Column.setDescriptionGenerator()` - no replacement. See [issue #2315](https://github.com/vaadin/flow-components/issues/2315)
* `Column.setExpandRatio()` - replace with `setFlexGrow()`
* `Column.getExpandRatio()` - replace with `getFlexGrow()`
* `Column.setCaption()` - replace with `setHeader()`
* `Column.getCaption()` - no replacement, see [issue #1496](https://github.com/vaadin/flow-components/issues/1496)
* *NOTE*: Grid.Column now expands by default; if you want to set it to a fixed width
   then you have to call `setWidth("50px").setFlexGrow(0)`.
* `Column.setHidden()` - replace with `setVisible()`
* `Column.isHidden()` - replace with `isVisible()`
* `Column.setHidable()` - No replacement as of now; see+vote for [issue #1603](https://github.com/vaadin/flow-components/issues/1603)
* `Column.isHidable()` - no replacement as of now

## GridContextMenu

* The `addContextMenuOpenListener()` has been replaced by `addGridContextMenuOpenedListener()`.
  **WARNING**: weird bugs will happen if you try to dynamically populate menu contents from this listener -
  see [grid #575](https://github.com/vaadin/vaadin-grid-flow/issues/575). Make sure to use `setDynamicContentHandler()` instead.

## FlexComponent/HorizontalLayout/VerticalLayout

The way how HorizontalLayout and VerticalLayout works has been changed completely. There is no
slot mechanism anymore, and the layouting is now based on CSS Flexbox rather than on
a Vaadin-specific JavaScript code. You need to relearn how CSS Flexbox works, then
rework your layouts to work on top of CSS Flexbox. Docs to read:

* [Vaadin docs: Layouts in Platform](https://vaadin.com/docs/v14/guide/upgrading/v8/5-components/#layouts-in-platform)
* [Vaadin 10 server-side layouting for Vaadin 8 and Android developers](https://mvysny.github.io/Vaadin-10-server-side-layouting-for-Vaadin-8-and-Android-developers/)

API replacements:

* `setExpandRatio()`: replace with either `expand()` (if the expand ratio is 1) or `setFlexGrow()`
* `addComponentsAndExpand()`: replace with `addAndExpand()`

A compatibility replacement components are coming, as a Vaadin Pro feature (TBA).

## GridLayout

Please see the `GridLayout` class providing Vaadin 8 API. Note that the new GridLayout
doesn't use slots and therefore uses different layouting engine. It's merely provided
as a best-effort.

Alternatively see the [GridLayout from Vaadin Directory](https://vaadin.com/directory/component/gridlayout) -
it supports slots (which means positioning of children within allotted cells) but
no expand ratios at the moment.

## Label

`Label` is no longer a `Div` but an actual `<label>` element. Usually the best approach is
to replace label by `Div` or `Span`.

* `new Label("", ContentMode.HTML)` is replaced by `new HtmlSpan()` from `common`
* `Label` with `ValoTheme.LABEL_H1` is replaced by `new H1()`; similar for `H2`/`H3`/`H4`/`H5`/`H6`.
* `ValoTheme.LABEL_HUGE`, `ValoTheme.LABEL_LARGE`, `ValoTheme.LABEL_SMALL`, `ValoTheme.LABEL_TINY` - no replacements.
* `ValoTheme.LABEL_BOLD` -> replace with `getStyle().set("font-weight", "500")`
* `ValoTheme.LABEL_LIGHT` -> used to set lighter text color and a font weight of 200, no replacement.
* `ValoTheme.LABEL_COLORED` -> sets a blue text color `#197de1`, no replacement.
* `ValoTheme.LABEL_SUCCESS`/`LABEL_FAILURE` used a green/red text color and a border, no replacement.
* `ValoTheme.LABEL_SPINNER` -> use an indeterminate [Progress Bar](https://vaadin.com/docs/latest/ds/components/progress-bar).

## CustomField

Vaadin 8's `CustomField` sets its width to 100% by default while Vaadin 10+'s `CustomField` wraps its children by default.
Make sure to have your `CustomField` implement `HasSize` and call `this.setWidthFull()`.

## TabSheet

Not available directly. You can either build your own component out of Vaadin 14 `Tabs`+`Tab`
(but that's just the tab header area), or try to use:

* `TabSheet` from `karibu-migration-common` (a direct replacement)
* `TabSheet` from [karibu-dsl](https://github.com/mvysny/karibu-dsl) (the same thing but in Kotlin with DSL support)
* [paged-tabs](https://vaadin.com/directory/component/paged-tabs) Vaadin Directory extension
  - kinda works but the API is not that rich compared to Vaadin 8's TabSheet.

## VerticalSplitPanel / HorizontalSplitPanel

Use the `VerticalSplitPanel` and `HorizontalSplitPanel` classes extending the Vaadin 14 `SplitLayout`
providing the old Vaadin 8 API. Note that only `Unit.PERCENTAGE` is supported -
trying to use `Unit.PX` will fail with an exception.

## TwinColSelect

Sorry, no replacement at the moment. TBD

## ListSelect

Replace with MultiSelectListBox. There is also a `ListSelect` component provided by `karibu-migration-common` which mimics Vaadin 8 version.

* `setItemCaptionGenerator()` - replaced by `setRenderer(new TextRenderer<>(itemLabelGenerator));`. See+vote on [issue #2601](https://github.com/vaadin/platform/issues/2601).

# Resources

* `ClassResource` - replace with [StreamResource](https://vaadin.com/docs/v14/flow/advanced/tutorial-dynamic-content/#using-streamresource)
* `ThemeResource` - You have two options:
  * you can store the image into the theme folder; then you can refer to the image from a CSS file as per
    [Creating a custom theme/Other Assets](https://vaadin.com/docs/v14/flow/styling/custom-theme/#other-theme-assets).
    You can also refer to the image via `new Image("themes/my-theme/img/foo.png", "foo")`
    if you store the image to `/frontend/themes/my-theme/img/foo.png` - Vaadin will copy the static resources
    to your WAR archive, to the `/META-INF/VAADIN/webapp/VAADIN/static/themes/my-theme/img/foo.png` folder
    and will serve the images from there.
  * Alternatively you can place the images
    into the static resource folder (usually `src/main/webapp`; the image would then go to `src/main/webapp/img/foo.png`)
    then refer to the image via `new Image("img/foo.png")`.
    See the [Resource cheat sheet](https://vaadin.com/docs/v14/flow/importing-dependencies/tutorial-ways-of-importing#resource-cheat-sheet)
    for more info.
  * Make sure to refer to the images as `new Image("img/foo.png")` and not `new Image("/img/foo.png")`
    nor `new Image("./img/foo.png")` - the first one would not work with a non-empty context root
    while the latter one will stop working for nested routes such as `@Route("my/nested/route")`.

# Further ideas

* Add extension functions for Groovy
* Add extension functions for Lombok
* Document all Vaadin 8 components in this README. Please create PRs to create a definitive migration guide here 👍

# License

Licensed under [Apache 2.0](https://www.apache.org/licenses/LICENSE-2.0.html)

Copyright 2021-2022 Martin Vysny

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this software except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

# Contributing / Developing

See [Contributing](CONTRIBUTING.md).
