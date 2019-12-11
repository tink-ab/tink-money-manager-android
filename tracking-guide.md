# Tracking

Tink PFM SDK allows you to track certain user events.

You can track user events taking place in the Finance Overview UI. The events are separated into _Screens_ and _Actions_. A _Screen_ event occurs when a user navigates
to a new screen within the Finance Overview UI. An _Action_ event occurs when the user performs an action such as pressing a button. An _Action_ will also contain 
some contextual data like where the event occurred in, what kind of action was performed.

## Getting started
In order to track the different events sent by the SDK you have to implement the `EventTracker` interface. A very simple implementation that simply prints the events could be:

```kotlin 
class LogTracker : EventTracker {
    override fun track(actionEvent: ActionEvent) {
        Timber.tag(TAG).d(
            "Track event - Category: %s, Action: %s, Label: %s",
            actionEvent.category,
            actionEvent.action,
            actionEvent.label
        )
    }

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

All the different events tracked by Tink PFM SDK are presented below. 

### Screens 

// TODO: Confirm list of screens

The different screen events that can occur are declared as public constants inside the `ScreenEvent` class. All screens have a `name` property which is consistent across platforms. 

| `ScreenEvent` | `name` | Description |
| -----|------|-----|
| `overview` | Overview | The overview screen. |
| `categoryList` | Category.List | Screen where user can choose from a list of categories. |
| `expenses` | Expenses | The expenses screen. |
| `income` | Income | The income screen. |
| `transactionsList` | Transactions.List | The transaction list. |
| `transactionsSimilar` | Transactions.Similar | Screen where user can select similar transactions. |


### Actions

// TODO: Confirm list of actions and add logic to track actions currently left untracked

The different actions that can occur are declared as public constants inside the `ActionEvent` class. The event contains a `category` property to describe where the event happened, an `action` property to describe what occurred, and a `label` property that describes what the text was on the button (or similar) that triggered the event. 

| `ActionEvent` | `category` | `action` | `label` | Description
| -----|------|-----|----|---|
| `expensesShowPageOneMonth` | Expenses | Show page | One month | Occurs when the user presses the "One Month" tab in the Expenses screen. | 
| `expensesShowPageSixMonths` | Expenses | Show page | Six months | Occurs when the user presses the "Six months" tab in the Expenses screen. | 
| `expensesShowPageTwelveMonths` | Expenses | Show page | Twelve months | Occurs when the user presses the "Twelve months" tab in the Expenses screen. | 
| `incomeShowPageOneMonth` | Income | Show page | One month | Occurs when the user presses the "One Month" tab in the Income screen. | 
| `incomeShowPageSixMonths` | Income | Show page | Six months | Occurs when the user presses the "Six months" tab in the Income screen. | 
| `incomeShowPageTwelveMonths` | Income | Show page | Twelve months | Occurs when the user presses the "Twelve months" tab in the Income screen. | 
| `overviewButtonPressedShowAllTransactions` | Overview | Button pressed | Show all transacions | Occurs when the user press "All transactions" in the overview screen. | 
| `expensesShowPageTransactions` | Expenses | Show page | Transactions | Occurs when the user press "Show transactions" in the expenses screen. | 
| `incomeShowPageTransactions` | Income | Show page | Transactions | Occurs when the user press "Show transactions" in the income screen. | 
| `expensesShowPageOverTime` | Expenses | Show page | Over time | Occurs when the user selects the "Over time" tab to show expenses over time in the expenses screen. | 
| `incomeShowPageOverTime` | Income | Show page | Over time | Occurs when the user selects the "Over time" tab to show income over time in the income screen. | 
| `expensesShowPageOneMonth` | Expenses | Show page | Monthly | Occurs when the user selects the "Monthly" tab to show expenses for one month in the expenses screen. | 
| `incomeShowPageOneMonth` | Income | Show page | Monthly | Occurs when the user selects the "Monthly" tab to show income for one month in the income screen.  | 
| `expensesButtonPressedCategoryPicker` | Expenses | Button pressed | All categories | Occurs when the press the button to filter for specific categories in the expenses over time screen. | 
| `incomeButtonPressedCategoryPicker` | Income | Button pressed | All categories | Occurs when the press the button to filter for specific categories in the income over time screen. | 
| `transactionsSimilarButtonPressedChangeSelected` | Transactions.Similar | Button pressed | Change Selected | Occurs when the user pressed the button to categorize similar transactions that the user selected. | 
| `transactionsSimilarButtonPressedSkip` | Transactions.Similar | Button pressed | Skip | Occurs when the user pressed the button to skip categorizing similar transactions. | 
| `expensesButtonPressedCategoryPickerSelect` | Expenses.CategoryFilter | Button pressed | Select | Occurs when the user pressed the button to select which category to filter the expenses over time screen by. | 
| `incomeButtonPressedCategoryPickerSelect` | Income.CategoryFilter | Button pressed | Select | Occurs when the user pressed the button to select which category to filter the income over time screen by. | 
| `expensesButtonPressedCategoryPickerCancel` | Expenses.CategoryFilter | Button pressed | Close | Occurs when the user pressed the button to cancel selecting category to filter the expenses over time screen by. | 
| `incomeButtonPressedCategoryPickerCancel` | Income.CategoryFilter | Button pressed | Close | Occurs when the user pressed the button to cancel selecting category to filter the income over time screen by. | 
| `transactionChooseCategoryButtonPressedCancel` | Transaction.ChooseCategory | Button pressed | Cancel | Occurs when the user pressed the button to cancel selecting category for a transaction. | 