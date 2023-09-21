# Migration Guide to Version 1.0.0

## Dependencies update
Tink Money Manager Android is now available on Maven Central: [Android Money manager on Maven](https://mvnrepository.com/artifact/com.tink/moneymanager). Therefore, you can remove mavenLocal() from your main `build.gradle` file (or `settings.gradle` file, depending upon your implementation) and use mavenCentral() instead.
1. In your `build.gradle` product module file replace:

```
dependencies {
    implementation "com.tink.moneymanager:moneymanager-ui:0.28.0"
}
```

With:

```
dependencies {
    implementation 'com.tink.moneymanager:moneymanager-ui:1.0.0'
}
```

2. Sync the changes.

## Finance overview migration guide
Version 1.0.0 introduces a new way of launching the overview screen. This involves using the new ___TinkMoneyManagerFragment___ instead of the deprecated ___FinanceOverviewFragment___.

#### Previous implementation with Tink Money Manager Android version prior 1.0.0:

```kotlin
        supportFragmentManager.beginTransaction().add(
            R.id.fragmentContainer,
            FinanceOverviewFragment.newInstance(
                accessToken = myAccessToken,
                styleResId = R.style.myStyle,
                tracker = myLogTracker,
                overviewFeatures = myOverviewFeatures,
                javaInsightActionHandler = myJavaInsightActionHandler,
                backPressedListener = myBackPressedListener,
                isOverviewToolbarVisible = true,
                isEditableOnPendingTransaction = true,
                isTransactionDetailsEnabled = true,
                featureSpecificThemes = myFeatureSpecificThemes,
                fragmentViewCreatedListener = myFragmentViewCreatedListener
            )
        ).commit()
```

#### New implementation with Tink Money Manager Android version 1.0.0:

Define entrypoint for the Overview screen. Be aware that these parameters below were previously inside the ___FinanceOverviewFragment___, but now are inside the ___Entrypoint.Overview___ class.

Please note:

* The ___isOverviewToolbarVisible___ parameter is now called ___toolbarVisible___.

* Your theme should now override the new ___TinkMoneyManagerStyle___.

* The  ___javaInsightActionHandler___ parameter is now called ___insightActionHandler___, this is because the ___insightActionHandler___ is inheriting InsightActionHandler that works for both Java and Kotlin.

```kotlin
    private val overviewEntrypoint = EntryPoint.Overview(
        overviewFeatures = myOverviewFeatures,
        toolbarVisible = true, 
        featureSpecificThemes = myFeatureSpecificThemes,
        insightActionHandler = myInsightActionHandler,
        fragmentViewCreatedListener = myFragmentViewCreatedListener
        )
```

Use the new ___TinkMoneyManager___ and pass to the entrypoint parameter, the ___EntryPoint.Overview___ instance defined above.

* The ___isTransactionDetailsEnabled___ parameter is now called ___enableTransactionDetail___ and ___isEditableOnPendingTransaction___ is called ___editPendingTransaction___.

```kotlin
            TinkMoneyManager.init(
                accessToken = myAccessToken,
                styleResId = R.style.myStyle,
                tracker = myLogTracker,
                backPressedListener = myBackPressedListener,
                editPendingTransaction = true,
                enableTransactionDetail = true,
                entryPoint = overviewEntrypoint,
                containerId = R.id.fragmentContainer,
                fragmentManager = supportFragmentManager
                )
```

## Declaration changes
Android reference documentation for Money Manager Android version 1.0.0 is now available at [Android reference documentation](https://tink-ab.github.io/tink-money-manager-android/)
