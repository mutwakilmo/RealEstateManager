package com.openclassrooms.realestatemanager.base

/**
 * Created by Mutwakil-Mo 🤩
 * Android Engineer,
 * Brussels
 */
interface REALESTATEMANAGERView<S : REALESTATEMANAGERViewState> {
    fun configureViewModel()
    fun render(state: S?)
}