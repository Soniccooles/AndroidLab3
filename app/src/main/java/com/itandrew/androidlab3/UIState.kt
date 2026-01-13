package com.itandrew.androidlab3

sealed class UiState<out T> {
    class Success<T>(val value: T?) : UiState<T>()
    class Failure(val message: String) : UiState<Nothing>()
    data object Loading : UiState<Nothing>()
}

fun <T> Result<T?>.toUiState(): UiState<T> {
    return if (this.isSuccess) {
        UiState.Success(this.getOrNull())
    } else this.exceptionOrNull()?.let { UiState.Failure(it.message ?: "Unknown error") }
        ?: UiState.Failure("Unknown error")
}