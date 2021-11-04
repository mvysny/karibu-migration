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

# List of functions

## Component and generic

* `HasSize.setWidthUndefined()` is replaced by `setWidth(null)`
* `HasSize.setHeightUndefined()` is replaced by `setHeight(null)`
* `new Label("", ContentMode.HTML)` is replaced by `new HtmlSpan()` from karibu-tools
* `Component.setCaption()` is replaced by `Component.setLabel()` from karibu-tools.
   * Warning: in order for the caption to be visible, the component must be nested in `VerticalLayout`/`HorizontalLayout`/`FlexLayout`.
* `Component.getCaption()` is replaced by `Component.getLabel()` from karibu-tools. 
* `CssLayout` is replaced by a `Div`
* `Component.setDescription()` is replaced by `Component.setTooltip()` from karibu-tools, which
    sets the `tooltip` DOM attribute.
* `Component.getDescription()` is replaced by `Component.getTooltip()` from karibu-tools, which
    reads the `tooltip` DOM attribute.

## ComboBox

* `setItemCaptionGenerator()` is replaced by `setItemLabelGenerator()`
* use `addThemeVariants(ComboBoxVariant.Small)` instead of `ValoTheme.COMBOBOX_SMALL`.
* Use `addThemeVariants(ComboBoxVariant.AlignRight)` from karibu-tools instead of `ValoTheme.COMBOBOX_ALIGN_RIGHT`
* Use `addThemeVariants(ComboBoxVariant.AlignCenter)` from karibu-tools instead of `ValoTheme.COMBOBOX_ALIGN_CENTER`
* There are no direct replacements for `ValoTheme.COMBOBOX_TINY`, `ValoTheme.COMBOBOX_LARGE`, `ValoTheme.COMBOBOX_HUGE`
  and `ValoTheme.COMBOBOX_BORDERLESS`.

## RadioButtonGroup

* `setItemCaptionGenerator()` is replaced by `setItemLabelGenerator()` from karibu-tools
* No replacement for `OPTIONGROUP_SMALL` and `OPTIONGROUP_LARGE`
* RadioButtonGroup is horizontal by default; use `addThemeVariants(RadioGroupVariant.LUMO_VERTICAL)`
  to lay out buttons horizontally in absence of `ValoTheme.OPTIONGROUP_HORIZONTAL`.

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
