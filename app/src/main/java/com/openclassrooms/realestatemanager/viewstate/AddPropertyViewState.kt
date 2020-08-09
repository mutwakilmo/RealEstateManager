package com.openclassrooms.realestatemanager.viewstate

import android.content.Context
import com.openclassrooms.realestatemanager.view.addProperty.ActionType
import com.openclassrooms.realestatemanager.view.addProperty.ErrorSourceAddProperty
import com.openclassrooms.realestatemanager.data.entity.Agent
import com.openclassrooms.realestatemanager.data.entity.Picture
import com.openclassrooms.realestatemanager.base.REALESTATEMANAGERIntent
import com.openclassrooms.realestatemanager.base.REALESTATEMANAGERResult
import com.openclassrooms.realestatemanager.base.REALESTATEMANAGERViewState
import com.openclassrooms.realestatemanager.utils.TypeFacility


/**
 * Created by Mutwakil-Mo 🤩
 * Android Engineer,
 * Brussels
 */

data class AddPropertyViewState(
        val isModifyProperty: Boolean = false,
        val isLoading: Boolean = false,
        val isSavedToDB: Boolean = false,
        val isSavedToDraft: Boolean = false,
        val isADraft: Boolean = false,
        val isOriginalAvailable: Boolean = false,
        val errors: List<ErrorSourceAddProperty>? = null,
        val listAgents: List<Agent>? = null,
        val type: String = "",
        val price: Double? = null, val surface: Double? = null,
        val bedrooms: Int? = null, val rooms: Int? = null,
        val bathrooms: Int? = null, val description: String? = null,
        val address: String = "", val neighborhood: String = "",
        val onMarketSince: String = "", val isSold: Boolean? = false,
        val sellDate: String? = null, val agentId: String? = null,
        val amenities: List<TypeFacility>? = null, val pictures: List<Picture>? = null,
        val agentFirstName: String = "", val agentLastName: String = ""

) : REALESTATEMANAGERViewState

sealed class AddPropertyViewEffect {
    data class PropertyFromDBEffect(
            val type: String, val price: Double?, val surface: Double?,
            val bedrooms: Int?, val rooms: Int?,
            val bathrooms: Int?, val description: String?,
            val address: String, val neighborhood: String,
            val onMarketSince: String, val isSold: Boolean,
            val sellDate: String, val agentId: String?,
            val amenities: List<TypeFacility>?, val agentFirstName: String,
            val agentLastName: String
    ) : AddPropertyViewEffect()
    data class PropertyfromDraftEffect(
            val type: String, val price: Double?, val surface: Double?,
            val bedrooms: Int?, val rooms: Int?,
            val bathrooms: Int?, val description: String?,
            val address: String, val neighborhood: String,
            val onMarketSince: String, val isSold: Boolean,
            val sellDate: String, val agentId: String?,
            val amenities: List<TypeFacility>?, val agentFirstName: String,
            val agentLastName: String, val isOriginalAvailable: Boolean
    ) : AddPropertyViewEffect()
}

sealed class AddPropertyIntent : REALESTATEMANAGERIntent {
    data class AddPropertyToDBIntent(
            val type: String, val price: Double?,
            val surface: Double?, val rooms: Int?,
            val bedrooms: Int?, val bathrooms: Int?,
            val description: String, val address: String,
            val neighborhood: String, val onMarketSince: String,
            val isSold: Boolean, val sellDate: String?,
            val amenities: List<TypeFacility>, val context: Context
    ) : AddPropertyIntent()

    data class SelectAgentIntent(val agentId: String): AddPropertyIntent()

    data class InitialIntent(val actionType: ActionType) : AddPropertyIntent()

    object OpenListAgentsIntent : AddPropertyIntent()

    data class AddPictureToListIntent(val pictureUrl: String, val thumbnailUrl: String?) : AddPropertyIntent()

    data class RemovePictureFromListIntent(val picture: Picture) : AddPropertyIntent()

    data class MovePictureInListPositionIntent(val from: Int, val to: Int) : AddPropertyIntent()

    data class AddDescriptionToPicture(val position: Int, val description: String) : AddPropertyIntent()

    data class SaveDraftIntent(
            val type: String, val price: Double?,
            val surface: Double?, val rooms: Int?,
            val bedrooms: Int?, val bathrooms: Int?,
            val description: String, val address: String,
            val neighborhood: String, val onMarketSince: String,
            val isSold: Boolean, val sellDate: String?,
            val amenities: List<TypeFacility>
    ) : AddPropertyIntent()

    object DisplayDataFromDB : AddPropertyIntent()

}

sealed class AddPropertyResult : REALESTATEMANAGERResult {
    data class PropertyAddedToDBResult(val errorSource: List<ErrorSourceAddProperty>?) : AddPropertyResult()
    object PropertyAddedToDraftResult : AddPropertyResult()
    data class PropertyFromDBResult(
            val type: String, val price: Double?,
            val surface: Double?, val rooms: Int?,
            val bedrooms: Int?, val bathrooms: Int?,
            val description: String?, val address: String,
            val neighborhood: String, val onMarketSince: String,
            val isSold: Boolean?, val sellOn: String?, val amenities: List<TypeFacility>?,
            val agent: Agent?, val errorSource: List<ErrorSourceAddProperty>?
    ) : AddPropertyResult()
    data class PropertyFromDraftResult(
            val type: String, val price: Double?,
            val surface: Double?, val rooms: Int?,
            val bedrooms: Int?, val bathrooms: Int?,
            val description: String?, val address: String,
            val neighborhood: String, val onMarketSince: String,
            val isSold: Boolean?, val sellOn: String?, val amenities: List<TypeFacility>?,
            val agent: Agent?, val originalAvailable: Boolean
    ) : AddPropertyResult()
    data class ListAgentsResult(val listAgents: List<Agent>?, val errorSource: List<ErrorSourceAddProperty>?) : AddPropertyResult()
    data class PictureResult(val pictures: List<Picture>) : AddPropertyResult()
    object NewPropertyResult : AddPropertyResult()

}