package com.openclassrooms.realestatemanager.base

/**
 * Created by Mutwakil-Mo 🤩
 * Android Engineer,
 * Brussels
 */
sealed class LoadingContentError<T> {
    class Loading<T>: LoadingContentError<T>()
    data class Content<T>(val packet: T): LoadingContentError<T>()
    data class Error<T>(val packet: T): LoadingContentError<T>()
}