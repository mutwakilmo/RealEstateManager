package com.openclassrooms.realestatemanager.viewstate

import com.openclassrooms.realestatemanager.data.entity.Address
import com.openclassrooms.realestatemanager.data.entity.Amenity
import com.openclassrooms.realestatemanager.data.entity.Picture
import com.openclassrooms.realestatemanager.data.entity.Property
import com.openclassrooms.realestatemanager.base.RealStateManagerIntent
import com.openclassrooms.realestatemanager.base.RealStateManagerResult
import com.openclassrooms.realestatemanager.base.RealStateManagerViewState

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
) : RealStateManagerViewState

sealed class DetailsPropertyIntent : RealStateManagerIntent {
    object FetchDetailsIntent : DetailsPropertyIntent()
    object DisplayDetailsIntent : DetailsPropertyIntent()
}

sealed class DetailsPropertyResult : RealStateManagerResult {
    data class FetchDetailsResult(
            val property: Property?, val address: Address?,
            val amenities: List<Amenity>?, val pictures: List<Picture>?
    ) : DetailsPropertyResult()
}