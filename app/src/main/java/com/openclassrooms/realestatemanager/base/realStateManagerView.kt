package com.openclassrooms.realestatemanager.base

/**
 * Created by Mutwakil-Mo 🤩
 * Android Engineer,
 * Brussels
 */
interface realStateManagerView<S : realStateManagerViewState> {
    fun configureViewModel()
    fun render(state: S?)
}