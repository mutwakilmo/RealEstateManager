package com.openclassrooms.realestatemanager.data.api.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Mutwakil-Mo 🤩
 * Android Engineer,
 * Brussels
 */
data class Result(
        @SerializedName("address_components")
        val addressComponents: List<AddressComponent>,
        @SerializedName("formatted_address")
        val formattedAddress: String,
        @SerializedName("geometry")
        val geometry: Geometry,
        @SerializedName("place_id")
        val placeId: String,
        @SerializedName("types")
        val types: List<String>
)