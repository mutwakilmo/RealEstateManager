package com.openclassrooms.realestatemanager.viewstate

import com.openclassrooms.realestatemanager.data.entity.Agent
import com.openclassrooms.realestatemanager.base.RealStateManagerIntent
import com.openclassrooms.realestatemanager.base.RealStateManagerResult
import com.openclassrooms.realestatemanager.base.RealStateManagerViewState
import com.openclassrooms.realestatemanager.view.searchProperty.ErrorSourceSearch
import com.openclassrooms.realestatemanager.utils.TypeFacility
import com.openclassrooms.realestatemanager.utils.TypeProperty


/**
 * Created by Mutwakil-Mo 🤩
 * Android Engineer,
 * Brussels
 */
data class SeachPropertyViewState(
        val error: List<ErrorSourceSearch>? = null,
        val showProperty: Boolean = false,
        val agents: List<Agent>? = null,
        val loading: Boolean = false
) : RealStateManagerViewState

sealed class SearchPropertyIntent : RealStateManagerIntent {
    data class SearchPropertyFromInputIntent(
            val type: List<TypeProperty>, val minPrice: Double?, val maxPrice: Double?,
            val minSurface: Double?, val maxSurface: Double?, val minNbRooms: Int?,
            val minNbBedrooms: Int?, val minNbBathrooms: Int?, val neighborhood: String?,
            val stillOnMarket: Boolean?, val manageBy: List<String>?, val closeTo: List<TypeFacility>,
            val maxDateOnMarket: String, val hasPhotos: Boolean?
    ) : SearchPropertyIntent()

    object GetListAgentsIntent : SearchPropertyIntent()
}

sealed class SearchPropertyResult : RealStateManagerResult {
    data class SearchResult(val error: List<ErrorSourceSearch>?) : SearchPropertyResult()
    data class ListAgentsResult(val listAgents: List<Agent>?, val errorSource: List<ErrorSourceSearch>?) : SearchPropertyResult()
}