package com.openclassrooms.realestatemanager.currency

import com.openclassrooms.realestatemanager.base.BaseViewModel
import com.openclassrooms.realestatemanager.base.LoadingContentError
import com.openclassrooms.realestatemanager.base.REALESTATEMANAGERViewModel
import com.openclassrooms.realestatemanager.data.repository.CurrencyRepository

/**
 * Created by Mutwakil-Mo 🤩
 * Android Engineer,
 * Brussels
 */
class BaseCurrencyViewModel(private val currencyRepository: CurrencyRepository) : BaseViewModel<BaseCurrencyViewState>(),
        REALESTATEMANAGERViewModel<BaseCurrencyIntent, BaseCurrencyResult> {

    private var currentViewState = BaseCurrencyViewState()
        set(value) {
            field = value
            viewStateLD.value = value
        }

    override fun actionFromIntent(intent: BaseCurrencyIntent) {
        when (intent) {
            is BaseCurrencyIntent.ChangeCurrencyIntent -> changeCurrency()
            is BaseCurrencyIntent.GetCurrentCurrencyIntent -> emitCurrentCurrency()
        }
    }

    override fun resultToViewState(result: LoadingContentError<BaseCurrencyResult>) {
        if (result is LoadingContentError.Content && result.packet is BaseCurrencyResult.ChangeCurrencyResult) {
            currentViewState = currentViewState.copy(currency = result.packet.currency)
        }
    }

    private fun changeCurrency() {
        currencyRepository.setCurrency()
        emitCurrentCurrency()

    }

    private fun emitCurrentCurrency() {
        val currency = currencyRepository.currency.value!!
        val result: LoadingContentError<BaseCurrencyResult> = LoadingContentError.Content(BaseCurrencyResult.ChangeCurrencyResult(currency))

        resultToViewState(result)
    }
}