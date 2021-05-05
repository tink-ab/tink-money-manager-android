![Platform](https://img.shields.io/badge/platform-Android-orange.svg)
![Languages](https://img.shields.io/badge/languages-kotlin-orange.svg)

# Finance Overview Sample 

![OverView Android PFM](https://images.ctfassets.net/tmqu5vj33f7w/Z48REXV9yE6LMW4pfV411/43103871e8015c6c6bb8ee89766c122d/Overview-Android.png)

This directory contains sample code that demonstrates the usage of `TinkMoneyManager` Android SDK.

## Prerequisites

1. Follow the [getting started guide](https://docs.tink.com/resources/getting-started/set-up-your-account) to retrieve your `client_id`.
2. Make sure you are an [Enterprise customer](https://tink.com/pricing/) with permanent users enabled.

## Configuration

Before running the sample project, you need to configure the following:

1. Set up a configuration object with your specifics:

```kotlin
   val config =
    TinkConfiguration(
        environment = Environment.Production, // Or define your own environment
        oAuthClientId = "yourKey", // Your clientId. Retrieve it from console.tink.com,
        redirectUri = "https://localhost:3000/callback" // [1]
    )
```

`[1]` _Note: This is only required if you also use Tink Link in your application. Please follow the [third party authentication guide](https://docs.tink.com/resources/tutorials/tink-link-sdk-android-tutorial#third-party-authentication) to set this up.
Otherwise, this can be just set to `https://localhost:3000/callback` as shown in the sample above. We will be working on improving this setup and making this field optional in the future.

2. Initialize Tink in your application:

```kotlin
   Tink.init(config, applicationContext)
```

## Running the example app

1. Open the project in Android Studio.
2. Press the run button. If all went well, this should launch an emulator with the sample app running.

Read more about Tink Money Manager Android in [here](https://docs.tink.com/resources/pfm-sdk-android).
