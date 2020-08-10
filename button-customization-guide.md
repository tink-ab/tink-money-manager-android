# Button Style Customization

Tink PFM UI allows you to customize the buttons shown in the finance overview UI by customizing their styles.

## Customize icons
You can customize the buttons by extending the existing `TinkFinanceOverviewStyle` from the SDK and overriding the button style resource attributes that are available.
Add your extended style in your application's `styles.xml`.
```xml
<resources>
    <style name="YourCustomTinkFinanceOverviewStyle" parent="TinkFinanceOverviewStyle">
        <item name="tink_textButtonStyle">@style/CustomTextButtonStyle</item>
    </style>
    
    <style name="CustomTextButtonStyle" parent="Widget.MaterialComponents.Button.TextButton">
        <item name="android:textColor">#ff461e7d</item>
        <item name="android:textAllCaps">true</item>
    </style>
</resources>
```
By default, Tink PFM UI uses [material buttons](https://material.io/components/buttons). It is recommended to take a look at the material button styles [as shown here](https://material.io/develop/android/components/buttons) before adding your custom styles.

You can also override from the default Tink button styles:
```xml
<resources>
    <style name="YourCustomTinkFinanceOverviewStyle" parent="TinkFinanceOverviewStyle">
        <item name="tink_textButtonStyle">@style/CustomTextButtonStyle</item>
    </style>
    
    <style name="CustomTextButtonStyle" parent="TinkTextButtonStyle">
        <item name="android:textAllCaps">true</item>
    </style>
</resources>
```

Here's a list of all the icon resource attributes in the Tink PFM UI that can be overridden:

| Custom Style Resource ID | Description | Default value |
|------|-----|-----|
| `tink_containerButtonStyle` | Style for all container buttons used in PFM UI | `TinkContainerButtonStyle` |
| `tink_textButtonStyle` | Style for all text buttons used in PFM UI | `TinkTextButtonStyle` |
| `tink_insightPrimaryActionButtonStyle` | Style for the primary action buttons in insight cards | `TinkInsightPrimaryActionButtonStyle` |
| `tink_insightSecondaryActionButtonStyle` | Style for the secondary action buttons in insight cards | `TinkInsightSecondaryActionButtonStyle` |
