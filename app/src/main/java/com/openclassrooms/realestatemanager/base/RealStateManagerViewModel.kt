package com.openclassrooms.realestatemanager.base

/**
 * Created by Mutwakil-Mo 🤩
 * Android Engineer,
 * Brussels
 */
interface RealStateManagerViewModel<I : RealStateManagerIntent, R : RealStateManagerResult> {
    fun actionFromIntent(intent: I)
    fun resultToViewState(result: LoadingContentError<R>)
}