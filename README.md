# Tink PFM SDK Android

## Prerequisites
   Before you can use Tink PFM SDK, you need to create a developer account at [Tink Console](https://console.tink.com/). You will also need to have a working integration with Tink to authenticate and authorize users.

## Installation

// TODO: Update if this is not confirmed

1. Take the `com` folder (containing the Tink Link local Maven dependencies) and put it in `~/.m2/repository/`.
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

3. Add dependency on `com.tink:tink-pfm-android:1.0.0`: // TODO: Finalize name for the SDK

```groovy
dependencies {
    implementation("com.tink:tink-pfm-android:1.0.0")
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
`[1]` The SSL certificate is used for certificate pinning. This is optional and you can choose to set it depending upon on your requirements.


2. Override the `TinkPfmStyle` for color customizations. Follow the [customization guide](https://github.com/tink-ab/tink-link-android/blob/master/customization-guide.md) to set this up.

3. Set up a `Tracker` implementation if you want to track screens and events in the finance overview UI for analytics. Follow the [analytics tracking guide](https://github.com/tink-ab/tink-link-android/blob/master/analytics-tracking-guide.md) to set this up.

3. Create an instance of `FinanceOverviewController`

```kotlin
val financeOverviewFragment = 
    FinanceOverviewFragment.newInstance(
        accessToken = "yourAccessToken", // [1]
        styleResId = R.style.YourCustomTinkPfmStyle, // Resource ID of your style that overrides TinkPfmStyle
        clientConfiguration = config, // Your client configuration object
        tracker = yourTracker // Your Tracker implementation
    )
```
`[1]` Tink PFM SDK needs a valid access token for a specific user to function correctly. Since Tink PFM SDK does not handle any type of authentication, this needs to be done by your backend. See [this link](https://docs.tink.com/api/#oauth) for more info on how this is done.

## Handling back press

In order for back presses to work properly within the `FinanceOverviewFragment` please override the `onBackPressed()` method in your activity.

```kotlin
override fun onBackPressed() {
    if (currentFinanceOverviewFragment?.handleBackPress() == false) {
        super.onBackPressed()
    }
}
```

## Guides
[Customization guide](https://github.com/tink-ab/tink-link-android/blob/master/customization-guide.md) This document outlines how to customize colors and fonts for the finance overview UI
[Analytics Tracking guide](https://github.com/tink-ab/tink-link-android/blob/master/analytics-tracking-guide.md) This document outlines how to set up trackers for tracking screens and events in the finance overview UI for analytics
