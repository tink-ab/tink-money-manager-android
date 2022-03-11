package com.tink.moneymanagerui.extensions

import com.tink.service.network.ErrorState
import com.tink.service.network.LoadingState
import com.tink.service.network.ResponseState
import com.tink.service.network.SuccessState

// TODO: Move to core

inline fun <P1, P2, R> mapState(
    p1: ResponseState<P1>?,
    p2: ResponseState<P2>?,
    function: (p1: P1, p2: P2) -> R): ResponseState<R> {
    return if (p1 is SuccessState && p2 is SuccessState) {
        SuccessState(function(p1.data, p2.data))
    } else if (p1 is ErrorState || p2 is ErrorState) {
        ErrorState("")
    } else {
        LoadingState
    }
}

inline fun <P1, P2, P3, R> mapState(
    p1: ResponseState<P1>?,
    p2: ResponseState<P2>?,
    p3: ResponseState<P3>?,
    function: (p1: P1, p2: P2, p3: P3) -> R): ResponseState<R> {
    return if (p1 is SuccessState && p2 is SuccessState && p3 is SuccessState) {
        SuccessState(function(p1.data, p2.data, p3.data))
    } else if (p1 is ErrorState || p2 is ErrorState || p3 is ErrorState) {
        ErrorState("")
    } else {
        LoadingState
    }
}

inline fun <P1, P2, P3, P4, R> mapState(
    p1: ResponseState<P1>?,
    p2: ResponseState<P2>?,
    p3: ResponseState<P3>?,
    p4: ResponseState<P4>?,
    function: (p1: P1, p2: P2, p3: P3, p4: P4) -> R): ResponseState<R> {
    return if (p1 is SuccessState && p2 is SuccessState && p3 is SuccessState && p4 is SuccessState) {
        SuccessState(function(p1.data, p2.data, p3.data, p4.data))
    } else if (p1 is ErrorState || p2 is ErrorState || p3 is ErrorState || p4 is ErrorState) {
        ErrorState("")
    } else {
        LoadingState
    }
}

