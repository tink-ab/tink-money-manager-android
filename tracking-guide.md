# Tracking

Tink PFM SDK allows you to track certain user events.

A _Screen_ event occurs when a user navigates to a new screen within the Finance Overview UI.

## Getting started
In order to track the different events sent by the SDK you have to implement the `EventTracker` interface. A very simple implementation that simply prints the events could be:

```kotlin 
class LogTracker : EventTracker {
    override fun track(screenEvent: ScreenEvent) {
        Timber.tag(TAG).d("Track screen - Screen name: %s", screenEvent.name)
    }
}
```

The `EventTracker` implementation then needs to be configured when setting up the `FinanceOverviewFragment`.

```kotlin
val financeOverviewFragment = 
    FinanceOverviewFragment.newInstance(
        accessToken = "yourAccessToken", // Your access token
        styleResId = R.style.YourCustomTinkFinanceOverviewStyle, // Resource ID of your style that extends TinkFinanceOverviewStyle
        clientConfiguration = config, // Your client configuration object
        tracker = eventTracker // Your EventTracker implementation
    )
```

This is all you need to do to track events in the finance overview UI. 


## Events  

The different screen events that can occur are declared as public constants inside the `ScreenEvent` class. All screens have a `name` property which is consistent across platforms. 

| `ScreenEvent` | `name` | Description |
| -----|------|-----|
| `OVERVIEW` | Overview | The overview screen. |
| `CATEGORY_SELECTION` | Category Selection | Screen where user can choose from a list of categories. |
| `EXPENSES` | Expenses | The expenses screen. |
| `INCOME` | Income | The income screen. |
| `TRANSACTIONS` | Transactions | The transaction list. |
| `SIMILAR_TRANSACTIONS` | Transactions.Similar | Screen where user can select similar transactions. |
