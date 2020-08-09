package com.openclassrooms.realestatemanager.viewstate

import com.openclassrooms.realestatemanager.data.entity.Address
import com.openclassrooms.realestatemanager.data.entity.Amenity
import com.openclassrooms.realestatemanager.data.entity.Picture
import com.openclassrooms.realestatemanager.data.entity.Property
import com.openclassrooms.realestatemanager.base.REALESTATEMANAGERIntent
import com.openclassrooms.realestatemanager.base.REALESTATEMANAGERResult
import com.openclassrooms.realestatemanager.base.REALESTATEMANAGERViewState

/**
 * Created by Mutwakil-Mo 🤩
 * Android Engineer,
 * Brussels
 */

data class DetailsPropertyViewState(
        val isLoading: Boolean = false,
        val property: Property? = null,
        val address: Address? = null,
        val pictures: List<Picture>? = null,
        val amenities: List<Amenity>? = null
) : REALESTATEMANAGERViewState

sealed class DetailsPropertyIntent : REALESTATEMANAGERIntent {
    object FetchDetailsIntent : DetailsPropertyIntent()
    object DisplayDetailsIntent : DetailsPropertyIntent()
}

sealed class DetailsPropertyResult : REALESTATEMANAGERResult {
    data class FetchDetailsResult(
            val property: Property?, val address: Address?,
            val amenities: List<Amenity>?, val pictures: List<Picture>?
    ) : DetailsPropertyResult()
}