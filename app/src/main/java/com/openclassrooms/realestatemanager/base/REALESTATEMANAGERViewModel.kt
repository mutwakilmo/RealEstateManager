package com.openclassrooms.realestatemanager.base

/**
 * Created by Mutwakil-Mo 🤩
 * Android Engineer,
 * Brussels
 */
interface REALESTATEMANAGERViewModel<I : REALESTATEMANAGERIntent, R : REALESTATEMANAGERResult> {
    fun actionFromIntent(intent: I)
    fun resultToViewState(result: LoadingContentError<R>)
}