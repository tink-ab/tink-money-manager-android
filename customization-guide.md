# Customization

Tink PFM SDK allows you to perform various customizations to the UI.

## Customize fonts

You can add custom font resources in your application's `styles.xml` for three font typefaces which are represented by the custom attributes - `tink_font_bold`, `tink_font_semi_bold` and `tink_font_regular`. Set these custom attributes to the
resource IDs of the font files you want to use.
```xml
<resources>
    <item name="tink_font_bold" type="font">@font/your_custom_bold_font</item>
    <item name="tink_font_semi_bold" type="font">@font/your_custom_semi_bold_font</item>
    <item name="tink_font_regular" type="font">@font/your_custom_regular_font</item>
</resources>
```

## Customize colors
You can customize colors by extending the existing `TinkFinanceOverviewStyle` from the SDK and overriding the custom attributes that are available.
Add your extended style in your application's `styles.xml`.
```xml
<style name="YourCustomTinkFinanceOverviewStyle" parent="TinkFinanceOverviewStyle">
    <item name="tink_expensesColor">@color/custom_expenses</item>
    <item name="tink_expensesLightColor">@color/custom_expenses_light</item>
    <item name="tink_expensesDarkColor">@color/custom_expenses_dark</item>
    <item name="tink_colorOnExpenses">@color/custom_color_on_expenses</item>
    <item name="tink_incomeColor">@color/custom_income</item>
    <item name="tink_incomeLightColor">@color/custom_income_light</item>
    <item name="tink_incomeDarkColor">@color/custom_income_dark</item>
    <item name="tink_colorOnIncome">@color/custom_color_on_income</item>
    <item name="tink_transferColor">@color/custom_transfer</item>
    <item name="tink_transferLightColor">@color/custom_transfer_light</item>
    <item name="tink_colorOnTransfer">@color/custom_color_on_transfer</item>
    <item name="tink_uncategorizedColor">@color/custom_uncategorized</item>
    <item name="tink_uncategorizedLightColor">@color/custom_uncategorized_light</item>
    <item name="tink_snackbarColor">@color/custom_snackbar</item>
    <item name="tink_colorOnSnackBar">@color/custom_color_on_snackbar</item>
    <item name="tink_chartContainerBackgroundColor">@color/custom_chart_container_background</item>
    <item name="tink_chartMeanValueColor">@color/custom_chart_mean_value</item>
    <item name="tink_tabNormalColor">@color/custom_tab_normal</item>
    <item name="tink_tabSelectedColor">@color/custom_tab_selected</item>
    <item name="tink_colorAccent">@color/colorAccent</item>
    <item name="tink_colorAccentLight">@color/colorAccentLight</item>
    <item name="tink_colorAccentDark">@color/colorAccentDark</item>
    <item name="tink_colorOnAccent">@color/colorOnAccent</item>
    <item name="tink_colorPrimary">@color/colorPrimary</item>
    <item name="tink_colorPrimaryLight">@color/colorPrimaryLight</item>
    <item name="tink_colorPrimaryDark">@color/colorPrimaryDark</item>
    <item name="tink_colorOnPrimary">@color/colorOnPrimary</item>
    <item name="tink_textColorAction">@color/textColorAction</item>
</style>
```

# Customize Overview Features
You can customize the various sections shown in the Overview screen by creating an `OverviewFeatures` instance and setting a list of features you want to show.
```kotlin
val sampleOverviewFeatures =
        OverviewFeatures(
            listOf(
                OverviewFeature.ActionableInsights,
                OverviewFeature.Statistics(listOf(StatisticType.EXPENSES, StatisticType.INCOME)),
                OverviewFeature.Accounts,
                OverviewFeature.LatestTransactions
            )
        )
```
You can also change the ordering of the sections and specifying the desired order in the list above.

You have to pass this `OverviewFeatures` instance as parameter while setting up the `FinanceOverviewFragment`.

```kotlin
val financeOverviewFragment =
    FinanceOverviewFragment.newInstance(
        accessToken = "yourAccessToken", // Your access token
        styleResId = R.style.YourCustomTinkFinanceOverviewStyle, // Resource ID of your style that extends TinkFinanceOverviewStyle
        clientConfiguration = config, // Your client configuration object
        overviewFeatures = yourOverviewFeatures // Your OverviewFeatures instance
    )
