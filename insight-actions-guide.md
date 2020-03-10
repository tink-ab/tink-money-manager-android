# Handling Insight Actions

Tink PFM SDK allows you to add your own action handlers to perform the necessary logic when the user selects an action for an insight that requires more user interaction.

## Getting started
In order to perform custom logic for insight actions, you have to extend the `InsightActionHandler` class. A very simple subclass could be:

```kotlin 
class ExampleActionHandler(val navController: NavController) : InsightActionHandler() {
    override fun viewTransactions(transactionIds: List<String>): Boolean {
        navController.navigate(MyTransactionListFragment.newInstance(transactionIds))
        return true
    }
}
```

The `InsightActionHandler` subclass then needs to be configured when setting up the `FinanceOverviewFragment`.

```kotlin
val financeOverviewFragment = 
    FinanceOverviewFragment.newInstance(
        accessToken = "yourAccessToken", // Your access token
        styleResId = R.style.YourCustomTinkFinanceOverviewStyle, // Resource ID of your style that extends TinkFinanceOverviewStyle
        clientConfiguration = config, // Your client configuration object
        tracker = eventTracker, // Your EventTracker implementation (optional)
        featureSet = yourOverviewFeatures, // Your OverviewFeatures instance (optional)
        insightActionHandler = yourInsightActionHandler // Your InsightActionHandler subclass
    )
```

This is all you need to do to perform custom logic for insight actions in the finance overview UI.

This is optional and if not set, the Tink PFM SDK uses the default internal action handler to handle the insight actions.
