![Min Android API level](https://img.shields.io/badge/api-21%2B-0E9EC2)
[![GitHub release (latest SemVer)](https://img.shields.io/github/v/release/tink-ab/tink-money-manager-android?color=%230E9EC2)](https://github.com/tink-ab/tink-money-manager-android/releases/latest)

# Tink Money Manager SDK for Android

## Prerequisites
Before you can use the **Money Manager SDK**, you need to create a developer account in the [Tink Console](https://console.tink.com/).
You will also need to have a working integration with Tink to authenticate and authorize users.

The minimum API level required for using this library is 21 (Android 5.0).

_Note: For running the SDK on devices with an **API level lower than 26**, some updates to your **build.gradle**
file are needed. See the [Supporting API < 26](#api-level-26) section below for more information._

## Installation ![Min Android API level](https://img.shields.io/maven-central/v/com.tink.moneymanager/moneymanager-ui)

The **Money Manager SDK** is distributed through the Maven Central repository.


To install it, add the latest `moneymanager-ui` release to your `build.gradle` dependencies.
```groovy
dependencies {
   implementation "com.tink.moneymanager:moneymanager-ui:<latest-version>"
}
```

Set the java compiler to Java 8 or higher. In your app-level `build.gradle`, inside the `android` block, add the following:
```groovy
compileOptions {
   sourceCompatibility = JavaVersion.VERSION_1_8
   targetCompatibility = JavaVersion.VERSION_1_8
}
```

<a name="api-level-26"></a>
#### Supporting API level lower than 26
If you need to support devices running API level lower than 26, then a few more updates to your app-level `build.gradle` file are needed.
Inside the `compileOptions` block, add the following:
```groovy
compileOptions {
    // Flag to enable support for the new language APIs

    // For AGP 4.1+
    isCoreLibraryDesugaringEnabled = true
    // For AGP 4.0
    // coreLibraryDesugaringEnabled = true
 }
```
Add a dependency to the Android desugaring library (check the [latest version here](https://github.com/google/desugar_jdk_libs/blob/master/CHANGELOG.md)):
```groovy
dependencies {
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:LATEST_SDK_VERSION")
}
```
More information about Java 8+ API desugaring support can be found on the [official Android documentation](https://developer.android.com/studio/write/java8-support#library-desugaring).

## Initialization

1. In your `Application` class, set up a configuration object with your specifics and initialize the **Money Manager SDK**:

```kotlin
val config =
    TinkConfiguration(
        environment = Environment.Production, // Or define your own environment
        oAuthClientId = "yourKey", // Your clientId. Retrieve it from console.tink.com,
    )

    Tink.init(config, applicationContext)
```

2. Generate a valid access token. The SDK needs a valid access token for a specific user to function correctly. Since the SDK does not handle any type of authentication, this needs to be done by your backend. See the [Tink API reference](https://docs.tink.com/api/#oauth) and the [Tink access token guide](https://docs.tink.com/resources/api-setup/get-access-token) for more info on how this is done.

3. Override the `TinkMoneyManagerStyle` for color customizations. Follow the [customization guide](https://docs.tink.com/resources/money-manager/money-manager-android/pfm-sdk-android-customization) to set this up.

4. Set up a `LogTracker` implementation. This is optional and you can add the implementation if you want to track screens and events happening in the SDK. Follow the [tracking guide](https://docs.tink.com/resources/money-manager/money-manager-android/pfm-sdk-android-event-tracking) to set this up.

5. Set up a `BackPressedListener` implementation. This is optional and you can add the implementation if you want to receive a callback every time a back button has been pressed.
   **Note**: _this is not the same as handling the back press._

   See the [Handling back press](#handling-back-press) section below for more information on how to handle the back navigation.

6. Create an instance of one of the Entrypoint subclass. The entrypoint determines which Money Manager feature to launch. See the [Entrypoints](#entrypoints) section for more information.

7. Call the `init` method of the `TinkMoneyManager` singleton class:

```kotlin
    TinkMoneyManager.init(
        accessToken = "myAccessToken", // A valid access token.
        styleResId = R.style.MyCustomTinkMoneyManagerStyle, // Resource ID of your style that extends TinkMoneyManagerStyle.
        tracker = myTracker, // Your event tracking implementation (optional).
        backPressedListener = myBackPressedListener, // Your back press listener (optional).
        editPendingTransaction = false, // Determines if pending transactions can be recategorized. Defaults to true.
        enableTransactionDetail = false, // Determines the behaviour of the SDK when the user clicks on a transaction. Defaults to true.
        enableRecommendedBudget = true, // Determines if SDK can show Recommended Budgets. Defaults to true.
        enableBudgetCreationSuccessScreen = true, // Determines if SDK can show Budget Confirmation Screen. Defaults to true.
        entryPoint = overviewEntrypoint, // The Money Manager feature to launch.
        containerId = R.id.fragmentContainer, // The resource ID of the container which will contain the Tink Fragment.
        fragmentManager = supportFragmentManager // The FragmentManager which performs the Tink fragment transaction.
    )
```

<a name="entrypoints"></a>
### Entrypoints
This section will help you set up the SDK with the Finance Overview screen as the entrypoint.

If you want to launch a different entrypoint, please refer to one of the following guides:

* [Finance overview](https://docs.tink.com/resources/money-manager/money-manager-android/pfm-sdk-android-finance-overview)
* [Actionable insights](https://docs.tink.com/resources/money-manager/money-manager-android/pfm-sdk-android-handling-insight-actions)
* [Accounts](https://docs.tink.com/resources/money-manager/money-manager-android/accounts-for-android)
* Budgets
* Recommended Budgets
* Statistics: Income, Expense, Left to spend
* Transactions


#### Finance Overview entrypoint

1. Create an instance of `OverviewFeatures`. This is optional and can be done if you want to customize the Finance Overview screen. Follow the [Finance Overview customization guide](https://docs.tink.com/resources/money-manager/money-manager-android/pfm-sdk-android-finance-overview) to set this up.

2. Define specific themes for Money Manager's individual features. This is optional and if not set, the main theme defined in the `TinkMoneyManager` class will be used.

3. Set up an `InsightActionHandler` implementation. This is optional and you can set up an implementation for defining custom handling of the insight actions. Follow the [Actionable insights guide](https://docs.tink.com/resources/money-manager/money-manager-android/pfm-sdk-android-handling-insight-actions) to set this up.

4. Set up an `OnFragmentViewCreatedListener` implementation. This is only required for setting up custom views. Follow the [Custom Views guide](https://docs.tink.com/resources/money-manager/money-manager-android/pfm-sdk-android-finance-overview#custom-views) to set this up.

```kotlin
val overviewEntrypoint =
   EntryPoint.Overview(
      overviewFeatures = myOverviewFeatures, // Optional: features customization of the Finance Overview screen.
      toolbarVisible = false, // Optional: toolbar visibility, defaults to false.
      featureSpecificThemes = myFeatureSpecificThemes, // Optional: feature specific themes.
      insightActionHandler = myInsightActionHandler, // Optional: custom handler for the insight actions.
      fragmentViewCreatedListener = myOnFragmentViewCreatedListener // Optional: only required for setting up custom views.  
   )
```

## Additional requirements

There are some things you need to address for the **Money Manager SDK** to work as expected.

<a name="handling-back-press"></a>
### Handling back press

To ensure proper navigation functionality when the **Money Manager SDK** is visible on the screen, you must forward all back press events to it.

To check if the SDK is visible on the screen make a call to the `isSdkActive()` method of the `TinkMoneyManager` class.


To forward a back press event to the SDK, override the `onBackPressed()` method in your activity and call `onBackPressed()` on the `TinkMoneyManager`class. When the last step of the back stack is reached, calling `TinkMoneyManager.onBackPressed()` will remove the SDK from the screen.


```kotlin
// In your activity:

    override fun onBackPressed() {
        if (TinkMoneyManager.isSdkActive()) {
            
            // Tink Money Manager is active and visible on the screen.
            // Delegate the back press to Tink SDK.
            TinkMoneyManager.onBackPressed()
            
            if (!TinkMoneyManager.isSdkActive()) { 
                // Tink Money Manager is removed from the stack.
                ...
            }
        } else {
            // Tink Money Manager is not active or visible on screen.
            super.onBackPressed()
        }
    }
```

### Screen orientation

The **Money Manager SDK** only works correctly when the screen orientation is locked to portrait mode. Fixing it to landscape mode or changing the configuration dynamically will lead to unexpected results and suboptimal user experience.

You can achieve this by opening your Android manifest file and setting `android:screenOrientation=“portrait”` on the Activity that is starting the SDK.


### Restoring state
To ensure proper handling of configuration changes when the **Tink Money Manager SDK** remains visible on the screen, you should invoke the `onRestore(fragmentManager: FragmentManager)` method of the `TinkMoneyManager`class after any configuration changes and provide an appropriate instance of the `FragmentManager`.

You can achieve this by checking the value of the `savedInstanceState` parameter within the `onCreate()` method of the Activity that launched the SDK.

```kotlin
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            TinkMoneyManager.onRestore(supportFragmentManager)
        }
        
        // ...
    }    
    
```

## Sample

This sample project shows how to setup and use the  **Tink Money Manager SDK** in your app.

- [Sample app](sample-moneymanager-ui)

## Documentation
For more detailed usage and full documentation, please refer to our guide.

- [Money Manager SDK for Android](https://docs.tink.com/resources/money-manager#money-manager-android)

## SDK reference
For the full API reference, please see the [Money Manager Android SDK Reference](https://tink-ab.github.io/tink-money-manager-android)