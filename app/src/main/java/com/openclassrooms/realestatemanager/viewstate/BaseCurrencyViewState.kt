package com.openclassrooms.realestatemanager.viewstate

import com.openclassrooms.realestatemanager.base.realStateManagerIntent
import com.openclassrooms.realestatemanager.base.realStateManageresult
import com.openclassrooms.realestatemanager.base.realStateManagerViewState
import com.openclassrooms.realestatemanager.utils.Currency

/**
 * Created by Mutwakil-Mo 🤩
 * Android Engineer,
 * Brussels
 */
data class BaseCurrencyViewState(
        val currency: Currency = Currency.EURO
) : realStateManagerViewState

sealed class BaseCurrencyResult : realStateManageresult {
    data class ChangeCurrencyResult(val currency: Currency) : BaseCurrencyResult()
}

sealed class BaseCurrencyIntent : realStateManagerIntent {
    object ChangeCurrencyIntent : BaseCurrencyIntent()
    object GetCurrentCurrencyIntent : BaseCurrencyIntent()
}