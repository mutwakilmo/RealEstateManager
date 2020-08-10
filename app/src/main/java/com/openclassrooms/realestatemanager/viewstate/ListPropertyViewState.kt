package com.openclassrooms.realestatemanager.viewstate

import com.openclassrooms.realestatemanager.data.entity.PropertyWithAllData
import com.openclassrooms.realestatemanager.base.RealStateManagerIntent
import com.openclassrooms.realestatemanager.base.RealStateManagerResult
import com.openclassrooms.realestatemanager.base.RealStateManagerViewState
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
) : RealStateManagerViewState

sealed class PropertyListResult : RealStateManagerResult{
    data class DisplayPropertiesResult(val properties: List<PropertyWithAllData>?) : PropertyListResult()
    data class OpenPropertyDetailResult(val itemSelected: Int?) : PropertyListResult()
}

sealed class PropertyListIntent : RealStateManagerIntent{
    object DisplayPropertiesIntent : PropertyListIntent()
    data class OpenPropertyDetailIntent(val property: PropertyWithAllData, val itemPosition: Int?) : PropertyListIntent()
    data class SetActionTypeIntent(val actionType: ActionTypeList) : PropertyListIntent()

}