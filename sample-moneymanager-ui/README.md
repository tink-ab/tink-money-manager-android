![Min Android API level](https://img.shields.io/badge/api-21%2B-0E9EC2)
[![GitHub release (latest SemVer)](https://img.shields.io/github/v/release/tink-ab/tink-money-manager-android?color=%230E9EC2)](https://github.com/tink-ab/tink-money-manager-android/releases/latest)


# Tink Money Manager Sample 

This directory contains sample code that demonstrates the usage of **Tink Money Manager SDK**.

## Prerequisites

1. Follow the [getting started guide](https://docs.tink.com/resources/getting-started/set-up-your-account) to retrieve your `client_id`.
2. Make sure you are an [Enterprise customer](https://tink.com/pricing/) with permanent users enabled.

<a name="sample-app-configuration"></a>
## Configuration

Before running the sample project, you need to configure the `MainEntrypointsActivity` with the following:

1. Set up the `TinkConfiguration` object with your specifics:

```kotlin
   val config =
    TinkConfiguration(
        environment = Environment.Production, // Or define your own environment
        oAuthClientId = "yourKey", // Your clientId. Retrieve it from console.tink.com,
    )
```

2. Generate a valid access token and set it in the `accessToken` parameter of the `TinkMoneyManager.init()` method.

```kotlin
TinkMoneyManager.init(
    accessToken = "", // Add a valid access token to visualize data
    
)
```
See [Tink Api reference](https://docs.tink.com/api/#oauth) and [Tink access token guide](https://docs.tink.com/resources/api-setup/get-access-token) for more info on how this is done.

## Running the sample app

1. Open the project in Android Studio.
2. Configure it following the [Configuration guide](#sample-app-configuration).
3. Press the run button. This will launch a simulator with the sample app running.

Read more about Tink Money Manager Android in [here](https://docs.tink.com/resources/money-manager#money-manager-android).
