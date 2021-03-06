package com.openclassrooms.realestatemanager.viewmodel

import androidx.lifecycle.LiveData
import com.openclassrooms.realestatemanager.data.entity.Agent
import com.openclassrooms.realestatemanager.data.entity.PropertyWithAllData
import com.openclassrooms.realestatemanager.data.repository.AgentRepository
import com.openclassrooms.realestatemanager.data.repository.CurrencyRepository
import com.openclassrooms.realestatemanager.data.repository.PropertyRepository
import com.openclassrooms.realestatemanager.base.BaseViewModel
import com.openclassrooms.realestatemanager.base.LoadingContentError
import com.openclassrooms.realestatemanager.base.RealStateManagerViewModel
import com.openclassrooms.realestatemanager.view.searchProperty.ErrorSourceSearch
import com.openclassrooms.realestatemanager.viewstate.SeachPropertyViewState
import com.openclassrooms.realestatemanager.viewstate.SearchPropertyIntent
import com.openclassrooms.realestatemanager.viewstate.SearchPropertyResult
import com.openclassrooms.realestatemanager.utils.*
import com.openclassrooms.realestatemanager.utils.Currency
import com.openclassrooms.realestatemanager.utils.extensions.keepPropertiesWithAllAmenityRequested
import com.openclassrooms.realestatemanager.utils.extensions.toDate
import com.openclassrooms.realestatemanager.utils.extensions.toEuro
import com.openclassrooms.realestatemanager.utils.extensions.toSqMeter
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

/**
 * Created by Mutwakil-Mo 🤩
 * Android Engineer,
 * Brussels
 */
class SearchPropertyViewModel(
        private val agentRepository: AgentRepository,
        private val propertyRepository: PropertyRepository,
        private val currencyRepository: CurrencyRepository
) : BaseViewModel<SeachPropertyViewState>(), RealStateManagerViewModel<SearchPropertyIntent, SearchPropertyResult> {

    private var currentViewState = SeachPropertyViewState()
        set(value) {
            field = value
            viewStateLD.value = value
        }

    val currency: LiveData<Currency>
        get() = currencyRepository.currency

    private var searchAgentsJob: Job? = null
    private var searchPropertyJob: Job? = null

    //data
    private var minPriceQuery = MIN_VALUE
    private var maxPriceQuery = MAX_VALUE
    private var minSurfaceQuery = MIN_VALUE
    private var maxSurfaceQuery = MAX_VALUE
    private var nbRoomQuery = MIN_VALUE.toInt()
    private var nbBedroomQuery = MIN_VALUE.toInt()
    private var nbBathroomQuery = MIN_VALUE.toInt()
    private var neighborhoodQuery = "%"
    private var isSoldQuery = listOf(0, 1)
    private var hasPictureQuery = listOf(0, 1)
    private lateinit var typeQuery: List<TypeProperty>
    private lateinit var agentsQuery: List<String>
    private lateinit var amenitiesQuery: List<TypeFacility>
    private lateinit var dateQuery: Date

    override fun actionFromIntent(intent: SearchPropertyIntent) {
        when(intent){
            is SearchPropertyIntent.SearchPropertyFromInputIntent -> {
                searchProperties(
                        intent.type, intent.minPrice, intent.maxPrice, intent.minSurface, intent.maxSurface,
                        intent.minNbRooms, intent.minNbBedrooms, intent.minNbBathrooms, intent.neighborhood,
                        intent.stillOnMarket, intent.manageBy, intent.closeTo, intent.maxDateOnMarket, intent.hasPhotos
                )
            }
            is SearchPropertyIntent.GetListAgentsIntent -> fetchAgentsFromDB()
        }
    }

    override fun resultToViewState(result: LoadingContentError<SearchPropertyResult>) {
        currentViewState = when(result){
            is LoadingContentError.Content -> {
                when(result.packet){
                    is SearchPropertyResult.SearchResult -> {
                        currentViewState.copy(
                                agents = null,
                                error = null,
                                loading = false,
                                showProperty = true
                        )
                    }
                    is SearchPropertyResult.ListAgentsResult -> {
                        currentViewState.copy(
                                showProperty = false,
                                error = null,
                                loading = false,
                                agents = result.packet.listAgents
                        )
                    }
                }
            }
            is LoadingContentError.Loading -> {
                currentViewState.copy(
                        showProperty = false,
                        agents = null,
                        loading = true
                )
            }
            is LoadingContentError.Error -> {
                when(result.packet){
                    is SearchPropertyResult.SearchResult -> {
                        currentViewState.copy(
                                showProperty = false,
                                agents = null,
                                error = result.packet.error,
                                loading = false
                        )
                    }

                    is SearchPropertyResult.ListAgentsResult -> {
                        currentViewState.copy(
                                showProperty = false,
                                agents = null,
                                error = result.packet.errorSource,
                                loading = false
                        )
                    }
                }
            }
        }
    }

    private fun searchProperties(
            type: List<TypeProperty>, minPrice: Double?, maxPrice: Double?,
            minSurface: Double?, maxSurface: Double?, minNbRooms: Int?,
            minNbBedrooms: Int?, minNbBathrooms: Int?, neighborhood: String?,
            stillOnMarket: Boolean?, manageBy: List<String>?, amenitiesSelected: List<TypeFacility>,
            maxDateOnMarket: String, hasPicture: Boolean?
    ){
        resultToViewState(LoadingContentError.Loading())
        val result: LoadingContentError<SearchPropertyResult>
        val listErrors = mutableListOf<ErrorSourceSearch>()

        fun checkErrorsInputUser(){
            if (type.isEmpty()) listErrors.add(ErrorSourceSearch.NO_TYPE_SELECTED)
            if (manageBy == null || manageBy.isEmpty()) listErrors.add(ErrorSourceSearch.NO_AGENT_SELECTED)
            if (maxDateOnMarket.isNotBlank() && maxDateOnMarket.toDate() == null){
                listErrors.add(ErrorSourceSearch.WRONG_DATE_FORMAT)
            }
        }

        fun setQueryInput(){
            val currentCurrency = if(currency.value != null) currency.value!! else Currency.EURO
            minPrice?.let { minPriceQuery = it.toEuro(currentCurrency) }
            maxPrice?.let { maxPriceQuery = it.toEuro(currentCurrency) }
            minSurface?.let { minSurfaceQuery = it.toSqMeter(currentCurrency) }
            maxSurface?.let { maxSurfaceQuery = it.toSqMeter(currentCurrency) }
            minNbRooms?.let { nbRoomQuery = it }
            minNbBedrooms?.let { nbBedroomQuery = it }
            minNbBathrooms?.let { nbBathroomQuery = it }
            if(neighborhood!!.isNotEmpty())  neighborhoodQuery = neighborhood
            if(stillOnMarket!!) isSoldQuery = listOf(0)
            if(hasPicture!!) hasPictureQuery = listOf(1)
            typeQuery = type
            agentsQuery = manageBy!!
            amenitiesQuery = amenitiesSelected
            dateQuery = maxDateOnMarket.toDate() ?: initiateDefaultDate()

        }

        checkErrorsInputUser()

        if(listErrors.isNotEmpty()){
            result = LoadingContentError.Error(SearchPropertyResult.SearchResult(listErrors))
            resultToViewState(result)
        } else {
            resetQueryToDefaultValues()
            setQueryInput()
            fetchQueryPropertiesFromDB()
        }
    }

    private fun fetchQueryPropertiesFromDB(){
        if (searchPropertyJob?.isActive == true) searchPropertyJob?.cancel()

        var propertiesQuery = listOf<PropertyWithAllData>()

        if (amenitiesQuery.isEmpty()) {
            searchPropertyJob = launch {
                propertiesQuery = propertyRepository.getPropertiesQuery(
                        minPriceQuery, maxPriceQuery, minSurfaceQuery, maxSurfaceQuery,
                        nbRoomQuery, nbBedroomQuery, nbBathroomQuery, agentsQuery, typeQuery,
                        neighborhoodQuery, isSoldQuery, hasPictureQuery, dateQuery
                )
                emitResultPropertyFetched(propertiesQuery)
            }
        } else {
            searchPropertyJob = launch {
                propertiesQuery = propertyRepository.getPropertiesQuery(
                        minPriceQuery, maxPriceQuery, minSurfaceQuery, maxSurfaceQuery,
                        nbRoomQuery, nbBedroomQuery, nbBathroomQuery, agentsQuery, typeQuery, neighborhoodQuery,
                        isSoldQuery, hasPictureQuery, dateQuery, amenitiesQuery
                )
                if(propertiesQuery.isNotEmpty() && amenitiesQuery.size > 1) {
                    propertiesQuery = propertiesQuery.keepPropertiesWithAllAmenityRequested(amenitiesQuery.size)
                }
                emitResultPropertyFetched(propertiesQuery)
            }
        }


    }

    private fun emitResultPropertyFetched(properties: List<PropertyWithAllData>){
        val result: LoadingContentError<SearchPropertyResult> = if (properties.isEmpty()) {
            val listErrors = listOf(ErrorSourceSearch.NO_PROPERTY_FOUND)
            LoadingContentError.Error(SearchPropertyResult.SearchResult(listErrors))
        } else {
            propertyRepository.propertyFromSearch = properties
            LoadingContentError.Content(SearchPropertyResult.SearchResult(null))

        }
        resultToViewState(result)
    }

    //--------------------
    // FETCH AGENTS
    //--------------------

    private fun fetchAgentsFromDB(){
        resultToViewState(LoadingContentError.Loading())

        if(searchAgentsJob?.isActive == true) searchAgentsJob?.cancel()

        searchAgentsJob = launch {
            val agents: List<Agent>? = agentRepository.getAllAgents()
            val result: LoadingContentError<SearchPropertyResult> = if(agents == null || agents.isEmpty()){
                val listErrors = listOf(ErrorSourceSearch.ERROR_FETCHING_AGENTS)
                LoadingContentError.Error(SearchPropertyResult.ListAgentsResult(null, listErrors))
            } else{
                LoadingContentError.Content(SearchPropertyResult.ListAgentsResult(agents, null))
            }
            resultToViewState(result)
        }

    }

    //--------------------
    // UTILS
    //--------------------
    private fun resetQueryToDefaultValues(){
        minPriceQuery = MIN_VALUE
        maxPriceQuery = MAX_VALUE
        minSurfaceQuery = MIN_VALUE
        maxSurfaceQuery = MAX_VALUE
        nbRoomQuery = MIN_VALUE.toInt()
        nbBedroomQuery = MIN_VALUE.toInt()
        nbBathroomQuery = MIN_VALUE.toInt()
        neighborhoodQuery = "%"
        isSoldQuery = listOf(0, 1)
        hasPictureQuery = listOf(0, 1)
        dateQuery = initiateDefaultDate()

    }

    private fun initiateDefaultDate(): Date {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, 1800)
        return calendar.time
    }
}