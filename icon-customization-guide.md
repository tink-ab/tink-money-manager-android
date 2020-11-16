# Icon Customization

Tink PFM UI allows you to customize the icons shown in the finance overview UI.

## Customize icons
You can customize icons by extending the existing `TinkFinanceOverviewStyle` from the SDK and overriding the icon resource attributes that are available.
Add your extended style in your application's `styles.xml`.
```xml
<style name="YourCustomTinkFinanceOverviewStyle" parent="TinkFinanceOverviewStyle">
    <item name="tink_icon_category_all_expenses">@drawable/custom_all_expenses</item>
    <item name="tink_icon_category_all_income">@drawable/custom_all_income</item>
    <item name="tink_icon_category_expenses_transport">@drawable/custom_transport</item>
    <item name="tink_icon_category_expenses_foodanddrinks">@drawable/custom_foodanddrinks</item>
    <item name="tink_icon_category_expenses_home">@drawable/custom_home</item>
    <item name="tink_icon_category_expenses_healthandbeauty">@drawable/custom_healthandbeauty</item>
    <item name="tink_icon_category_expenses_leisure">@drawable/custom_leisure</item>
    <item name="tink_icon_category_expenses_houseandgarden">@drawable/custom_houseandgarden</item>
    <item name="tink_icon_category_expenses_shopping">@drawable/custom_shopping</item>
    <item name="tink_icon_category_expenses_other">@drawable/custom_other</item>
    <item name="tink_icon_category_income_benefits">@drawable/custom_benefits</item>
    <item name="tink_icon_category_income_financial">@drawable/custom_financial</item>
    <item name="tink_icon_category_income_pension">@drawable/custom_pension</item>
    <item name="tink_icon_category_income_salary">@drawable/custom_salary</item>
    <item name="tink_icon_category_income_reimbursements">@drawable/custom_reimbursement</item>
    <item name="tink_icon_category_income_other">@drawable/custom_other</item>
    <item name="tink_icon_category_transfers_exclude">@drawable/custom_excluded</item>
    <item name="tink_icon_category_transfers_savings">@drawable/custom_savings</item>
    <item name="tink_icon_category_transfers_other">@drawable/custom_other</item>
    <item name="tink_icon_category_uncategorized">@drawable/custom_uncategorized</item>
</style>
```
By default, Tink PFM UI uses [material icons](https://material.io/resources/icons/?style=baseline). It is recommended to take a look at the [material icon design principles](https://material.io/design/iconography/system-icons.html#design-principles) before adding your custom icons.
Here's a list of all the icon resource attributes in the Tink PFM UI that can be overridden:

| Icon Resource ID | Description |
|------|-----|
| `tink_icon_category_all_expenses` | Icon shown for default expenses type |
| `tink_icon_category_all_income` | Icon shown for default income type |
| `tink_icon_category_expenses_transport` | Icon shown for transport expenses  |
| `tink_icon_category_expenses_foodanddrinks` | Icon shown for food expenses |
| `tink_icon_category_expenses_home` | Icon shown for home expenses |
| `tink_icon_category_expenses_healthandbeauty` | Icon shown for health and wellness expenses  |
| `tink_icon_category_expenses_leisure` | Icon shown for entertainment expenses |
| `tink_icon_category_expenses_houseandgarden` | Icon shown for house expenses |
| `tink_icon_category_expenses_shopping` | Icon shown for shopping expenses |
| `tink_icon_category_expenses_other` | Icon shown for miscellaneous expenses |
| `tink_icon_category_income_benefits` | Icon shown for income from benefits |
| `tink_icon_category_income_financial` | Icon shown for financial income |
| `tink_icon_category_income_pension` | Icon shown for income from pension |
| `tink_icon_category_income_salary` | Icon shown for income from salary |
| `tink_icon_category_income_reimbursements` | Icon shown for income from refunds |
| `tink_icon_category_income_other` | Icon shown for any other type of income |
| `tink_icon_category_transfers_exclude` | Icon shown for excluded transfers |
| `tink_icon_category_transfers_savings` | Icon shown for savings transfers |
| `tink_icon_category_transfers_other` | Icon shown for any other type of transfers |
| `tink_icon_category_uncategorized` | Icon shown for uncategorized transactions |

## Customize icon background shapes

Tink PFM UI uses [ShapeableImageView](https://developer.android.com/reference/com/google/android/material/imageview/ShapeableImageView) for displaying icons with shape styling for backgrounds.

You can customize the icon background shapes by extending the existing `TinkFinanceOverviewStyle` from the SDK and overriding the shape style resource attributes that are available.

1. Add dependency on the Material Components library. You can follow the [material components getting started guide](https://material.io/develop/android/docs/getting-started) for more information to set it up.
```groovy
dependencies {
    // ...
    implementation 'com.google.android.material:material:<version>'
    // ...
  }
```

2. Add your extended style in your application's `styles.xml`.
```xml
<resources>
    <style name="YourCustomTinkFinanceOverviewStyle" parent="TinkFinanceOverviewStyle">
        <item name="tink_categoryIconShapeStyle">@style/SquareWithRoundedCornersStyle</item>
    </style>

    <style name="SquareWithRoundedCornersStyle" parent="ShapeAppearance.MaterialComponents.MediumComponent">
        <item name="cornerFamily">rounded</item>
        <item name="cornerSize">30%</item>
    </style>
</resources>
```
It is recommended to take a look at how material shaping works [as shown here](https://material.io/develop/android/theming/shape) before adding your custom styles.

By default, Tink PFM UI uses the shape style that inherits from `ShapeAppearance.MaterialComponents.MediumComponent`.

You can also override from the default shape style:
```xml
<resources>
    <style name="YourCustomTinkFinanceOverviewStyle" parent="TinkFinanceOverviewStyle">
        <item name="tink_textButtonStyle">@style/SquareWithRoundedCornersStyle</item>
    </style>

    <style name="SquareWithRoundedCornersStyle" parent="TinkCategoryIconShapeStyle">
        <item name="cornerSize">30%</item>
    </style>
</resources>
```

Here's a list of all the shape style resource attributes in the Tink PFM UI that can be overridden:

| Custom Style Resource ID | Description | Default value |
|------|-----|-----|
| `tink_categoryIconShapeStyle` | Style for all category icon background shapes used in PFM UI | `TinkCategoryIconShapeStyle` |
