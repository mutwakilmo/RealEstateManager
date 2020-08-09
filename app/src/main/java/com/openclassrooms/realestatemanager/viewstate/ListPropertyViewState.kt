package com.openclassrooms.realestatemanager.viewstate

import com.openclassrooms.realestatemanager.data.entity.PropertyWithAllData
import com.openclassrooms.realestatemanager.base.realStateManagerIntent
import com.openclassrooms.realestatemanager.base.realStateManageresult
import com.openclassrooms.realestatemanager.base.realStateManagerViewState
import com.openclassrooms.realestatemanager.view.listProperties.ActionTypeList
import com.openclassrooms.realestatemanager.view.listProperties.ErrorSourceListProperty

/**
 * Created by Mutwakil-Mo 🤩
 * Android Engineer,
 * Brussels
 */
data class ListPropertyViewState(
        val errorSource: ErrorSourceListProperty? = null,
        val isLoading: Boolean = false,
        val listProperties: List<PropertyWithAllData>? = null,
        val openDetails: Boolean = false,
        val itemSelected: Int? = null
) : realStateManagerViewState

sealed class PropertyListResult : realStateManageresult{
    data class DisplayPropertiesResult(val properties: List<PropertyWithAllData>?) : PropertyListResult()
    data class OpenPropertyDetailResult(val itemSelected: Int?) : PropertyListResult()
}

sealed class PropertyListIntent : realStateManagerIntent{
    object DisplayPropertiesIntent : PropertyListIntent()
    data class OpenPropertyDetailIntent(val property: PropertyWithAllData, val itemPosition: Int?) : PropertyListIntent()
    data class SetActionTypeIntent(val actionType: ActionTypeList) : PropertyListIntent()

}