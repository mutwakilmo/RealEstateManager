package com.openclassrooms.realestatemanager.base

import androidx.fragment.app.Fragment
import java.lang.ref.WeakReference

/**
 * Created by Mutwakil-Mo 🤩
 * Android Engineer,
 * Brussels
 */
abstract class BaseFragmentRealStateManager : Fragment(){

    interface OnLoading{
        fun displayLoading(loading: Boolean)
    }

    var callbackLoading: WeakReference<OnLoading>? = null
    protected var loading: Boolean = false

    protected fun renderLoading(loading: Boolean){
        callbackLoading?.get()?.displayLoading(loading)
        this.loading = loading
    }
}