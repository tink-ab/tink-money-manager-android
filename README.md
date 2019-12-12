# Tink PFM SDK Android

## Prerequisites
   Before you can use Tink PFM SDK, you need to create a developer account at [Tink Console](https://console.tink.com/). You will also need to have a working integration with Tink to authenticate and authorize users.

## Installation

1. Take the `com` folder (containing the Tink Sdk local maven dependencies) and put it in `~/.m2/repository/`.
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

3. Add dependency on the Tink Sdk:

```groovy
dependencies {
    implementation("com.tink:pfm-sdk:1.0.0")
}
```

## Initialization

1. Set up a client configuration object with your specifics:

```kotlin
val config = 
    ClientConfiguration(
        environment = Environment.Production, // Or define your own environment
        sslCertificate = "yourCertificate" // [1]
    )
```
`[1]` The SSL certificate is used for certificate pinning. This is optional and you can choose to set it depending on your requirements.


2. Override the `TinkFinanceOverviewStyle` for color customizations. Follow the [customization guide](https://github.com/tink-ab/tink-link-android/blob/master/customization-guide.md) to set this up.

3. Set up a `EventTracker` implementation. This is optional and you can add the implementation if you want to track screens and events in the finance overview UI. Follow the [tracking guide](https://github.com/tink-ab/tink-link-android/blob/master/tracking-guide.md) to set this up.

3. Create an instance of `FinanceOverviewFragment`

```kotlin
val financeOverviewFragment = 
    FinanceOverviewFragment.newInstance(
        accessToken = "yourAccessToken", // [1]
        styleResId = R.style.YourCustomTinkFinanceOverviewStyle, // Resource ID of your style that extends TinkFinanceOverviewStyle
        clientConfiguration = config, // Your client configuration object
        tracker = yourTracker // Your EventTracker implementation
    )
```
`[1]` Tink PFM SDK needs a valid access token for a specific user to function correctly. Since Tink PFM SDK does not handle any type of authentication, this needs to be done by your backend. See [this link](https://docs.tink.com/api/#oauth) for more info on how this is done.

## Refreshing access tokens
User access tokens expire after a set amount of time. You can keep your user logged in by exchanging your a refresh token for a new access token [(see Tink docs)](https://docs.tink.com/api/#get-an-authorization-token) and passing it to the Tink Sdk. This will overwrite the token that the fragment was initialzed with. If needed you can also refresh the statistics and latest transactions:

```kotlin
financeOverviewFragment.setAccessToken(yourNewToken)
financeOverviewFragment.refreshData() // Optional
```

## Handling back press

In order for back presses to work properly within the `FinanceOverviewFragment` please override the `onBackPressed()` method in your activity.

```kotlin
override fun onBackPressed() {
    if (financeOverviewFragment?.handleBackPress() == false) {
        super.onBackPressed()
    }
}
```

## Guides
- [Customization guide](https://github.com/tink-ab/tink-link-android/blob/master/customization-guide.md) This document outlines how to customize colors and fonts for the finance overview UI
- [Tracking guide](https://github.com/tink-ab/tink-link-android/blob/master/tracking-guide.md) This document outlines how to set up trackers for tracking screens and events in the finance overview UI
