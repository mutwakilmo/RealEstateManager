package com.openclassrooms.realestatemanager.data.api.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Mutwakil-Mo 🤩
 * Android Engineer,
 * Brussels
 */

data class Location(
        @SerializedName("lat")
        val lat: Double,
        @SerializedName("lng")
        val lng: Double
)