package com.openclassrooms.realestatemanager.base

/**
 * Created by Mutwakil-Mo 🤩
 * Android Engineer,
 * Brussels
 */
interface realStateManagerViewModel<I : realStateManagerIntent, R : realStateManageresult> {
    fun actionFromIntent(intent: I)
    fun resultToViewState(result: LoadingContentError<R>)
}