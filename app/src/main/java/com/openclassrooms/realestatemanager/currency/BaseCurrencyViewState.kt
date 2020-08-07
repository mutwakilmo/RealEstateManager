package com.openclassrooms.realestatemanager.currency

import com.openclassrooms.realestatemanager.base.REALESTATEMANAGERIntent
import com.openclassrooms.realestatemanager.base.REALESTATEMANAGERResult
import com.openclassrooms.realestatemanager.base.REALESTATEMANAGERViewState
import com.openclassrooms.realestatemanager.utils.Currency

/**
 * Created by Mutwakil-Mo 🤩
 * Android Engineer,
 * Brussels
 */
data class BaseCurrencyViewState(
        val currency: Currency = Currency.EURO
) : REALESTATEMANAGERViewState

sealed class BaseCurrencyResult : REALESTATEMANAGERResult {
    data class ChangeCurrencyResult(val currency: Currency) : BaseCurrencyResult()
}

sealed class BaseCurrencyIntent : REALESTATEMANAGERIntent {
    object ChangeCurrencyIntent : BaseCurrencyIntent()
    object GetCurrentCurrencyIntent : BaseCurrencyIntent()
}