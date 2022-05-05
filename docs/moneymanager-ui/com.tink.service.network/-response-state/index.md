---
title: ResponseState
---
//[moneymanager-ui](../../../index.html)/[com.tink.service.network](../index.html)/[ResponseState](index.html)



# ResponseState



[androidJvm]\
sealed class [ResponseState](index.html)&lt;out [T](index.html)&gt;



## Inheritors


| Name |
|---|
| [LoadingState](../-loading-state/index.html) |
| [SuccessState](../-success-state/index.html) |
| [ErrorState](../-error-state/index.html) |


## Extensions


| Name | Summary |
|---|---|
| [map](../map.html) | [androidJvm]<br>fun &lt;[T](../map.html), [R](../map.html)&gt; [ResponseState](index.html)&lt;[T](../map.html)&gt;.[map](../map.html)(f: ([T](../map.html)) -&gt; [R](../map.html)): [ResponseState](index.html)&lt;[R](../map.html)&gt; |

