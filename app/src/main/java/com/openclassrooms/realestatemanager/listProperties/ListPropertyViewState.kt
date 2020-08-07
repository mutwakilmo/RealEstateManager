package com.openclassrooms.realestatemanager.listProperties

import com.openclassrooms.realestatemanager.base.REALESTATEMANAGERIntent
import com.openclassrooms.realestatemanager.base.REALESTATEMANAGERResult
import com.openclassrooms.realestatemanager.base.REALESTATEMANAGERViewState
import com.openclassrooms.realestatemanager.data.entity.PropertyWithAllData

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
) : REALESTATEMANAGERViewState

sealed class PropertyListResult : REALESTATEMANAGERResult {
    data class DisplayPropertiesResult(val properties: List<PropertyWithAllData>?) : PropertyListResult()
    data class OpenPropertyDetailResult(val itemSelected: Int?) : PropertyListResult()
}

sealed class PropertyListIntent : REALESTATEMANAGERIntent {
    object DisplayPropertiesIntent : PropertyListIntent()
    data class OpenPropertyDetailIntent(val property: PropertyWithAllData, val itemPosition: Int?) : PropertyListIntent()
    data class SetActionTypeIntent(val actionType: ActionTypeList) : PropertyListIntent()
}