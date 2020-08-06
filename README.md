# Tink PFM UI Android

## Prerequisites
   Before you can use Tink PFM UI, you need to create a developer account at [Tink Console](https://console.tink.com/). You will also need to have a working integration with Tink to authenticate and authorize users.

## Installation

1. Take the `com` folder (containing the Tink PFM UI local maven dependencies) and put it in `~/.m2/repository/`.
2. Add `mavenLocal()` as repository in your root level build.gradle file.

```groovy
allprojects {
    repositories {
        mavenLocal()
        // Rest of the repositories
    }
}
```

_Note: The `mavenLocal()` repository needs to be on top of the other repositories, as shown above._

3. Add dependency on the Tink PFM UI:

```groovy
dependencies {
    implementation("com.tink:pfm-ui:0.9.7")
}
```

4. Enable databinding. In your app-level `build.gradle`, inside the `android` block:
```groovy
dataBinding {
   enabled = true
}
```

5. Set the java compiler to Java 8 or higher. In your app-level `build.gradle`, inside the `android` block: 
```groovy
compileOptions {
   sourceCompatibility = JavaVersion.VERSION_1_8
   targetCompatibility = JavaVersion.VERSION_1_8
}
```

## Initialization

1. Set up a client configuration object with your specifics:

```kotlin
val config = 
    ClientConfiguration(
        environment = Environment.Production, // Or define your own environment
        sslCertificate = "yourCertificate" // The SSL certificate used for certificate pinning.
    )
```


2. Override the `TinkFinanceOverviewStyle` for color customizations. Follow the [customization guide](/customization-guide.md) to set this up.

3. Set up a `EventTracker` implementation. This is optional and you can add the implementation if you want to track screens and events in the finance overview UI. Follow the [tracking guide](/tracking-guide.md) to set this up.

4. Create an instance of `OverviewFeatures`. This is optional and can be done if you want to customize the Overview screen. Follow the [customization guide](/customization-guide.md) to set this up.

5. [Optional] Extend the `InsightActionHandler` class, if you want to handle the actions for the insights in the Events UI. Follow the [insight actions guide](/insight-actions-guide.md) to set this up.

5. Create an instance of `FinanceOverviewFragment`

```kotlin
val financeOverviewFragment = 
    FinanceOverviewFragment.newInstance(
        accessToken = "yourAccessToken", // [1]
        styleResId = R.style.YourCustomTinkFinanceOverviewStyle, // Resource ID of your style that extends TinkFinanceOverviewStyle
        clientConfiguration = config, // The client configuration object you created in step 1
        tracker = yourTracker, // Your EventTracker implementation (optional)
        overviewFeatures = yourOverviewFeatures, // Your OverviewFeatures instance (optional)
        insightActionHandler = yourInsightActionHandler // Your InsightActionHandler subclass (optional)
    )
```
`[1]` Tink PFM UI needs a valid access token for a specific user to function correctly. Since Tink PFM UI does not handle any type of authentication, this needs to be done by your backend. See [this link](https://docs.tink.com/api/#oauth) for more info on how this is done.

_Note: All data and connections are scoped to the lifecycle of the `FinanceOverviewFragment`, i.e. after it is destroyed, all cached content will be garbage collected. That means it is important to not leak a reference to the fragment so no sensitive user data is retained in memory after usage._

## Refreshing access tokens
User access tokens expire after a set amount of time. You can keep your user logged in by exchanging your a refresh token for a new access token [(see Tink docs)](https://docs.tink.com/api/#get-an-authorization-token) and passing it to the Tink PFM UI. This will overwrite the token that the fragment was initialzed with. If needed you can also refresh the statistics and latest transactions:

```kotlin
financeOverviewFragment.setAccessToken(yourNewToken)
financeOverviewFragment.refreshData() // Optional
```

## Additional requirements

There are some things you need to address for the Tink Finance Overview to work as expected.

### Handling back press

In order for navigation to work properly within the `FinanceOverviewFragment`, you need to forward back press events to it. This can be done by overriding the `onBackPressed()` method in your activity and call `handleBackPress()` on the fragment. This method will also return a boolean value indication whether the `FinanceOverviewFragment` has consumed the event or not.

```kotlin
// In your activity:

override fun onBackPressed() {
   val backpressHandled = financeOverviewFragment?.handleBackPress()
   if (backpressHandled == false) {
       super.onBackPressed() // Or do whatever suits your application
   }
}
```

### Locking screen orientation

The Tink Finance Overview only works correctly when the screen orientation is locked to portrait mode. Fixed landscape mode or changing the configuration dynamically will lead to unexpected results and suboptimal user experience.
You can achieve this by opening your Android manifest and setting `android:screenOrientation=“portrait”` on the Activity containing the `FinanceOverviewFragment`.

## Guides
- [Customization guide](/customization-guide.md) This document outlines how to set up various customizations available for the finance overview UI
- [Tracking guide](/tracking-guide.md) This document outlines how to set up trackers for tracking screens and events in the finance overview UI
