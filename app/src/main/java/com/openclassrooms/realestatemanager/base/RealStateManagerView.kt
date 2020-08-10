package com.openclassrooms.realestatemanager.base

/**
 * Created by Mutwakil-Mo 🤩
 * Android Engineer,
 * Brussels
 */
interface RealStateManagerView<S : RealStateManagerViewState> {
    fun configureViewModel()
    fun render(state: S?)
}