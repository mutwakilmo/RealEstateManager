package com.openclassrooms.realestatemanager.viewmodel

import androidx.lifecycle.LiveData
import com.openclassrooms.realestatemanager.data.entity.PropertyWithAllData
import com.openclassrooms.realestatemanager.data.repository.CurrencyRepository
import com.openclassrooms.realestatemanager.data.repository.PropertyRepository
import com.openclassrooms.realestatemanager.base.BaseViewModel
import com.openclassrooms.realestatemanager.base.LoadingContentError
import com.openclassrooms.realestatemanager.base.RealStateManagerViewModel
import com.openclassrooms.realestatemanager.view.listProperties.*
import com.openclassrooms.realestatemanager.utils.Currency
import com.openclassrooms.realestatemanager.viewstate.ListPropertyViewState
import com.openclassrooms.realestatemanager.viewstate.PropertyListIntent
import com.openclassrooms.realestatemanager.viewstate.PropertyListResult
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Created by Mutwakil-Mo 🤩
 * Android Engineer,
 * Brussels
 */
class ListPropertyViewModel(
        private val propertyRepository: PropertyRepository,
        private val currencyRepository: CurrencyRepository
)
    : BaseViewModel<ListPropertyViewState>(), RealStateManagerViewModel<PropertyListIntent, PropertyListResult> {

    private var currentViewState = ListPropertyViewState()
        set(value) {
            field = value
            viewStateLD.value = value
        }

    val currency: LiveData<Currency>
        get() = currencyRepository.currency

    private var actionType = ActionTypeList.ALL_PROPERTIES

    private var searchPropertiesJob: Job? = null

    override fun actionFromIntent(intent: PropertyListIntent){
        when(intent){
            is PropertyListIntent.DisplayPropertiesIntent -> fetchPropertiesFromDB()
            is PropertyListIntent.OpenPropertyDetailIntent -> setPropertySelected(intent.property, intent.itemPosition)
            is PropertyListIntent.SetActionTypeIntent -> setActionType(intent.actionType)
        }

    }

    override fun resultToViewState(result: LoadingContentError<PropertyListResult>){
        currentViewState = when (result){
            is LoadingContentError.Content -> {
                when(result.packet){
                    is PropertyListResult.DisplayPropertiesResult -> {
                        currentViewState.copy(
                                openDetails = false,
                                errorSource = null,
                                isLoading = false,
                                listProperties = result.packet.properties
                        )
                    }
                    is PropertyListResult.OpenPropertyDetailResult -> {
                        currentViewState.copy(
                                errorSource = null,
                                isLoading = false,
                                openDetails = true,
                                itemSelected = result.packet.itemSelected
                        )
                    }
                }
            }

            is LoadingContentError.Loading -> {
                currentViewState.copy(
                        errorSource = null,
                        openDetails = false,
                        isLoading = true
                )
            }
            is LoadingContentError.Error -> {
                when(result.packet){
                    is PropertyListResult.DisplayPropertiesResult -> {
                        currentViewState.copy(
                                openDetails = false,
                                isLoading = false,
                                errorSource = ErrorSourceListProperty.NO_PROPERTY_IN_DB
                        )
                    }
                    else -> throw Exception("unknow error")
                }
            }
        }
    }

    private fun setActionType(actionType: ActionTypeList){
        this.actionType = actionType
    }

    private fun setPropertySelected(property: PropertyWithAllData, itemPosition: Int?){
        propertyRepository.propertyPicked = property
        propertyRepository.propertyPicked?.let {
            val result: LoadingContentError<PropertyListResult> = LoadingContentError.Content(PropertyListResult.OpenPropertyDetailResult(itemPosition))
            resultToViewState(result)
        }

    }

    private fun fetchPropertiesFromDB() {
        resultToViewState(LoadingContentError.Loading())
        if(searchPropertiesJob?.isActive == true) searchPropertiesJob?.cancel()

        var propertiesForDisplay: List<PropertyWithAllData>? = null

        fun emitResult(){
            val result: LoadingContentError<PropertyListResult> = if(propertiesForDisplay!!.isEmpty()){
                LoadingContentError.Error(PropertyListResult.DisplayPropertiesResult(null))
            } else{
                LoadingContentError.Content(PropertyListResult.DisplayPropertiesResult(propertiesForDisplay))
            }
            resultToViewState(result)
        }

        when(actionType){
            ActionTypeList.ALL_PROPERTIES -> {
                searchPropertiesJob = launch {
                    propertiesForDisplay = propertyRepository.getAllProperties()
                    emitResult()
                }

            }
            ActionTypeList.SEARCH_RESULT -> {
                propertiesForDisplay = propertyRepository.propertyFromSearch
                emitResult()
            }
        }


    }

}