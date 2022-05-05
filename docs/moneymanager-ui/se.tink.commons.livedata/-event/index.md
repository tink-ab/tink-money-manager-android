---
title: Event
---
//[moneymanager-ui](../../../index.html)/[se.tink.commons.livedata](../index.html)/[Event](index.html)



# Event



[androidJvm]\
open class [Event](index.html)&lt;out [T](index.html)&gt;(content: [T](index.html))

Used as a wrapper for data that is exposed via a LiveData that represents an event.



## Constructors


| | |
|---|---|
| [Event](-event.html) | [androidJvm]<br>fun &lt;out [T](index.html)&gt; [Event](-event.html)(content: [T](index.html)) |


## Functions


| Name | Summary |
|---|---|
| [getContentIfNotHandled](get-content-if-not-handled.html) | [androidJvm]<br>fun [getContentIfNotHandled](get-content-if-not-handled.html)(): [T](index.html)?<br>Returns the content and prevents its use again. |
| [peekContent](peek-content.html) | [androidJvm]<br>fun [peekContent](peek-content.html)(): [T](index.html)<br>Returns the content, even if it's already been handled. |


## Properties


| Name | Summary |
|---|---|
| [hasBeenHandled](has-been-handled.html) | [androidJvm]<br>@get:[JvmName](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-name/index.html)(name = &quot;hasBeenHandled&quot;)<br>val [hasBeenHandled](has-been-handled.html): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false |

