package com.openclassrooms.realestatemanager.viewstate

import android.content.Context
import com.openclassrooms.realestatemanager.base.RealStateManagerIntent
import com.openclassrooms.realestatemanager.base.RealStateManagerResult
import com.openclassrooms.realestatemanager.base.RealStateManagerViewState
import com.openclassrooms.realestatemanager.view.mainActivity.ErrorSourceMainActivity
import com.openclassrooms.realestatemanager.utils.Currency

/**
 * Created by Mutwakil-Mo 🤩
 * Android Engineer,
 * Brussels
 */

data class MainActivityViewState(
        val isOpenAddProperty:Boolean = false,
        val errorSource: ErrorSourceMainActivity? = null,
        val isLoading: Boolean = false,
        val currency: Currency = Currency.EURO,
        val propertyAdded: Int? = null
) : RealStateManagerViewState

sealed class MainActivityResult : RealStateManagerResult {
    object OpenAddPropertyResult: MainActivityResult()
    data class ChangeCurrencyResult(val currency: Currency) : MainActivityResult()
    data class UpdateDataFromNetwork(val errorSource: ErrorSourceMainActivity?, val numberPropertyAdded: Int?) : MainActivityResult()
}

sealed class MainActivityIntent : RealStateManagerIntent {
    object OpenAddPropertyActivityIntent : MainActivityIntent()
    object ChangeCurrencyIntent : MainActivityIntent()
    object GetCurrentCurrencyIntent : MainActivityIntent()
    data class UpdatePropertyFromNetwork(val context: Context) : MainActivityIntent()
}

