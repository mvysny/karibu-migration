# Karibu-Migration: Aids migration from Vaadin 8 to Vaadin 14+

[![GitHub tag](https://img.shields.io/github/tag/mvysny/karibu-migration.svg)](https://github.com/mvysny/karibu-migration/tags)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.mvysny.karibu-migration/karibu-migration/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.mvysny.karibu-migration/karibu-migration)
[![CI](https://github.com/mvysny/karibu-migration/actions/workflows/gradle.yml/badge.svg)](https://github.com/mvysny/karibu-migration/actions/workflows/gradle.yml)

A collection of extension functions mimicking Vaadin 8 API but redirecting to Vaadin 14+ API where applicable.
This simplifies the process when rewriting Vaadin 8 components/layouts/view to Vaadin 14, since
you can continue using the same Vaadin 8 functions and get useful information and warnings.
For example,
there is `setWidthUndefined()` function marked as deprecated (since it's no longer in Vaadin 14), redirecting to a `setWidth(null)` call.

The jar is in Maven Central (TODO release), so it's easy to add this library to your project.

Gradle:
```groovy
repositories {
  mavenCentral()
}
dependencies {
  api("com.github.mvysny.karibu-migration:karibu-migration:x.y")
}
```

See the tag above for the latest version.

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
  * For Vaadin8+14 MPR demo project see [Vaadin 14 MPR + Vaadin 8 Gradle Demo Project](https://gitlab.com/mvysny/vaadin14-mpr-gradle-demo)
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

# Component-related replacements

## Component and generic

* `HasSize.setWidthUndefined()` is replaced by `setWidth(null)`
* `HasSize.setHeightUndefined()` is replaced by `setHeight(null)`
* `Component.setCaption()` is replaced by `Component.setLabel()` from karibu-tools.
   * Warning: in order for the caption to be visible, the component must be nested in `VerticalLayout`/`HorizontalLayout`/`FlexLayout`.
   * Alternatively, wrap the component in the `LabelWrapper` component decorator from karibu-tools which adds label to any component.
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

## NativeSelect -> Select

* `setItemCaptionGenerator()` is replaced by `setItemLabelGenerator()`
* `setTextInputAllowed()` - no replacement. Select never allows text input. Use `ComboBox` instead
  if you want to enable text input.

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
* `Column.setCaption()` - replace with `setHeader()`
* *NOTE*: Grid.Column now expands by default; if you want to set it to a fixed width
   then you have to call `setWidth("50px").setFlexGrow(0)`.

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

## Label

`Label` is no longer a `Div` but an actual `<label>` element. Usually the best approach is
to replace label by `Div` or `Span`.

* `new Label("", ContentMode.HTML)` is replaced by `new HtmlSpan()` from karibu-tools

## CustomField

Vaadin 8's `CustomField` sets its width to 100% by default while Vaadin 10+'s `CustomField` wraps its children by default.
Make sure to have your `CustomField` implement `HasSize` and call `this.setWidthFull()`.

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
