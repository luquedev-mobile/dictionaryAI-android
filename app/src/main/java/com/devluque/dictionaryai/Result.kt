package com.devluque.dictionaryai

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

sealed interface Result<out T> {
    data class Success<T>(val data: T) : Result<T>
    data class Error(val throwable: Throwable) : Result<Nothing>
    data object Loading : Result<Nothing>
}

@OptIn(ExperimentalContracts::class)
inline fun <T> Result<T>.isSuccess(block: (T) -> Unit): Result<T> {
    contract {
        callsInPlace(block, InvocationKind.AT_MOST_ONCE)
    }

    if (this is Result.Success) {
        block(this.data)
    }

    return this
}

@OptIn(ExperimentalContracts::class)
inline fun <T> Result<T>.onFailure(block: (Throwable) -> Unit): Result<T> {
    contract {
        callsInPlace(block, InvocationKind.AT_MOST_ONCE)
    }

    if (this is Result.Error) {
        block(this.throwable)
    }

    return this
}