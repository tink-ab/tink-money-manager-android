---
title: CurrencyUtils
---
//[moneymanager-ui](../../../index.html)/[com.tink.moneymanagerui.util](../index.html)/[CurrencyUtils](index.html)



# CurrencyUtils



[androidJvm]\
object [CurrencyUtils](index.html)



## Types


| Name | Summary |
|---|---|
| [CurrencyFormat](-currency-format/index.html) | [androidJvm]<br>object [CurrencyFormat](-currency-format/index.html) |


## Functions


| Name | Summary |
|---|---|
| [formatAmountExactWithCurrencySymbol](format-amount-exact-with-currency-symbol.html) | [androidJvm]<br>fun [formatAmountExactWithCurrencySymbol](format-amount-exact-with-currency-symbol.html)(amount: [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html), currency: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [formatAmountExactWithoutCurrencySymbol](format-amount-exact-without-currency-symbol.html) | [androidJvm]<br>fun [formatAmountExactWithoutCurrencySymbol](format-amount-exact-without-currency-symbol.html)(amount: [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html), currency: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [formatAmountRound](format-amount-round.html) | [androidJvm]<br>fun [formatAmountRound](format-amount-round.html)(amount: [ExactNumber](../../com.tink.model.misc/-exact-number/index.html), currencyCode: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [formatAmountRoundWithCurrencySymbol](format-amount-round-with-currency-symbol.html) | [androidJvm]<br>@[JvmStatic](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-static/index.html)<br>fun [formatAmountRoundWithCurrencySymbol](format-amount-round-with-currency-symbol.html)(amount: [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html), currency: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [formatAmountRoundWithoutCurrencySymbol](format-amount-round-without-currency-symbol.html) | [androidJvm]<br>fun [formatAmountRoundWithoutCurrencySymbol](format-amount-round-without-currency-symbol.html)(amount: [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html), currency: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [formatCurrency](format-currency.html) | [androidJvm]<br>fun [formatCurrency](format-currency.html)(amount: [Amount](../../com.tink.model.misc/-amount/index.html), currencyFormat: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>fun [formatCurrency](format-currency.html)(amount: [Amount](../../com.tink.model.misc/-amount/index.html), currencyFormat: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), explicitPositive: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [formatCurrencyExact](format-currency-exact.html) | [androidJvm]<br>fun [formatCurrencyExact](format-currency-exact.html)(amount: [Amount](../../com.tink.model.misc/-amount/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [formatCurrencyExactIfNotInteger](format-currency-exact-if-not-integer.html) | [androidJvm]<br>fun [formatCurrencyExactIfNotInteger](format-currency-exact-if-not-integer.html)(amount: [Amount](../../com.tink.model.misc/-amount/index.html), additionalFlags: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [formatCurrencyExactWithExplicitPositive](format-currency-exact-with-explicit-positive.html) | [androidJvm]<br>fun [formatCurrencyExactWithExplicitPositive](format-currency-exact-with-explicit-positive.html)(amount: [Amount](../../com.tink.model.misc/-amount/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [formatCurrencyExactWithoutSign](format-currency-exact-without-sign.html) | [androidJvm]<br>fun [formatCurrencyExactWithoutSign](format-currency-exact-without-sign.html)(amount: [Amount](../../com.tink.model.misc/-amount/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [formatCurrencyExactWithoutSignAndSymbol](format-currency-exact-without-sign-and-symbol.html) | [androidJvm]<br>fun [formatCurrencyExactWithoutSignAndSymbol](format-currency-exact-without-sign-and-symbol.html)(amount: [Amount](../../com.tink.model.misc/-amount/index.html), currency: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [formatCurrencyExactWithoutSymbol](format-currency-exact-without-symbol.html) | [androidJvm]<br>fun [formatCurrencyExactWithoutSymbol](format-currency-exact-without-symbol.html)(amount: [Amount](../../com.tink.model.misc/-amount/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [formatCurrencyRound](format-currency-round.html) | [androidJvm]<br>fun [formatCurrencyRound](format-currency-round.html)(amount: [Amount](../../com.tink.model.misc/-amount/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [formatCurrencyRoundWithoutAmountSign](format-currency-round-without-amount-sign.html) | [androidJvm]<br>fun [formatCurrencyRoundWithoutAmountSign](format-currency-round-without-amount-sign.html)(amount: [Amount](../../com.tink.model.misc/-amount/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [formatCurrencyRoundWithoutSign](format-currency-round-without-sign.html) | [androidJvm]<br>fun [formatCurrencyRoundWithoutSign](format-currency-round-without-sign.html)(amount: [Amount](../../com.tink.model.misc/-amount/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [formatCurrencyWithAmountSign](format-currency-with-amount-sign.html) | [androidJvm]<br>fun [formatCurrencyWithAmountSign](format-currency-with-amount-sign.html)(amount: [Amount](../../com.tink.model.misc/-amount/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [formatCurrencyWithAmountSignExact](format-currency-with-amount-sign-exact.html) | [androidJvm]<br>fun [formatCurrencyWithAmountSignExact](format-currency-with-amount-sign-exact.html)(amount: [Amount](../../com.tink.model.misc/-amount/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [formatCurrencyWithExplicitPositive](format-currency-with-explicit-positive.html) | [androidJvm]<br>fun [formatCurrencyWithExplicitPositive](format-currency-with-explicit-positive.html)(amount: [Amount](../../com.tink.model.misc/-amount/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [formatCurrencyWithoutAmountSign](format-currency-without-amount-sign.html) | [androidJvm]<br>fun [formatCurrencyWithoutAmountSign](format-currency-without-amount-sign.html)(amount: [Amount](../../com.tink.model.misc/-amount/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

