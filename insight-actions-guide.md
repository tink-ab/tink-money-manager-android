# Handling Insight Actions

Tink PFM UI allows you to add your own action handlers to perform the necessary logic when the user selects an action for an insight that requires more user interaction.

## Getting started
In order to perform custom logic for insight actions, you have to extend the `InsightActionHandler` class. A very simple subclass could be:

```kotlin 
class ExampleActionHandler(val navController: NavController) : InsightActionHandler() {
    override fun viewTransactions(transactionIds: List<String>): Boolean {
        navController.navigate(MyTransactionListFragment.newInstance(transactionIds))
        return true // [1]
    }
}
```
`[1]` The overridden method must return `true` or `false` to notify the SDK whether the action has been handled for an insight. If the value is `true`, the insight will be marked as handled and removed from the list.

The `InsightActionHandler` subclass then needs to be configured when setting up the `FinanceOverviewFragment`.

```kotlin
val financeOverviewFragment = 
    FinanceOverviewFragment.newInstance(
        accessToken = "yourAccessToken", // Your access token
        styleResId = R.style.YourCustomTinkFinanceOverviewStyle, // Resource ID of your style that extends TinkFinanceOverviewStyle
        clientConfiguration = config, // Your client configuration object
        insightActionHandler = yourInsightActionHandler // Your InsightActionHandler subclass
    )
```

Adding your own action handler is optional and if not set, the Tink PFM UI will use the default internal action handler to handle the insight actions.
