package com.openclassrooms.realestatemanager.viewstate

import com.openclassrooms.realestatemanager.base.RealStateManagerIntent
import com.openclassrooms.realestatemanager.base.RealStateManagerResult
import com.openclassrooms.realestatemanager.base.RealStateManagerViewState
import com.openclassrooms.realestatemanager.utils.Currency

/**
 * Created by Mutwakil-Mo 🤩
 * Android Engineer,
 * Brussels
 */
data class BaseCurrencyViewState(
        val currency: Currency = Currency.EURO
) : RealStateManagerViewState

sealed class BaseCurrencyResult : RealStateManagerResult {
    data class ChangeCurrencyResult(val currency: Currency) : BaseCurrencyResult()
}

sealed class BaseCurrencyIntent : RealStateManagerIntent {
    object ChangeCurrencyIntent : BaseCurrencyIntent()
    object GetCurrentCurrencyIntent : BaseCurrencyIntent()
}