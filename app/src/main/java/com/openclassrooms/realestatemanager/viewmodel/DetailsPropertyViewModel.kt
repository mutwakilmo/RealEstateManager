package com.openclassrooms.realestatemanager.viewmodel


import androidx.lifecycle.LiveData
import com.openclassrooms.realestatemanager.data.entity.PropertyWithAllData
import com.openclassrooms.realestatemanager.data.repository.CurrencyRepository
import com.openclassrooms.realestatemanager.data.repository.PropertyRepository
import com.openclassrooms.realestatemanager.base.BaseViewModel
import com.openclassrooms.realestatemanager.base.LoadingContentError
import com.openclassrooms.realestatemanager.base.RealStateManagerViewModel
import com.openclassrooms.realestatemanager.viewstate.DetailsPropertyIntent
import com.openclassrooms.realestatemanager.viewstate.DetailsPropertyResult
import com.openclassrooms.realestatemanager.viewstate.DetailsPropertyViewState
import com.openclassrooms.realestatemanager.utils.Currency
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Created by Mutwakil-Mo 🤩
 * Android Engineer,
 * Brussels
 */
class DetailsPropertyViewModel(
        private val propertyRepository: PropertyRepository,
        private val currencyRepository: CurrencyRepository
) : BaseViewModel<DetailsPropertyViewState>(),
        RealStateManagerViewModel<DetailsPropertyIntent, DetailsPropertyResult> {

    private var currentViewState = DetailsPropertyViewState()
        set(value) {
            field = value
            viewStateLD.value = value
        }

    val currency: LiveData<Currency>
        get() = currencyRepository.currency

    private var searchPropertyJob: Job? = null

    override fun actionFromIntent(intent: DetailsPropertyIntent) {
        when (intent) {
            is DetailsPropertyIntent.FetchDetailsIntent -> fetchDetailsProperty()
            is DetailsPropertyIntent.DisplayDetailsIntent -> displayPropertyDetails()
        }
    }

    override fun resultToViewState(result: LoadingContentError<DetailsPropertyResult>) {
        currentViewState = when (result) {
            is LoadingContentError.Content -> {
                when (result.packet) {
                    is DetailsPropertyResult.FetchDetailsResult -> {
                        currentViewState.copy(
                                isLoading = false,
                                property = result.packet.property,
                                address = result.packet.address,
                                amenities = result.packet.amenities,
                                pictures = result.packet.pictures
                        )
                    }

                }
            }
            is LoadingContentError.Loading -> {
                currentViewState.copy(
                        isLoading = true
                )
            }
            is LoadingContentError.Error -> {
                currentViewState.copy(
                        isLoading = false
                )

            }

        }
    }

    private fun displayPropertyDetails() {
        propertyRepository.propertyPicked?.let {
            emitResultDisplayProperty(it)
        }
    }

    private fun fetchDetailsProperty() {
        resultToViewState(LoadingContentError.Loading())
        if (searchPropertyJob?.isActive == true) searchPropertyJob?.cancel()

        val propertyId = propertyRepository.propertyPicked!!.property.id

        fun fetchProperty() {
            searchPropertyJob = launch {
                val property = propertyRepository.getProperty(propertyId)[0]
                propertyRepository.propertyPicked = property
                emitResultDisplayProperty(property)
            }
        }

        fetchProperty()

    }

    private fun emitResultDisplayProperty(property: PropertyWithAllData) {
        val result: LoadingContentError<DetailsPropertyResult> = LoadingContentError.Content(DetailsPropertyResult.FetchDetailsResult(
                property.property, property.address[0], property.amenities, property.pictures
        ))
        resultToViewState(result)
    }
}