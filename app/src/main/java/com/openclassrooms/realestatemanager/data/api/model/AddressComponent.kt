package com.openclassrooms.realestatemanager.data.api.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Mutwakil-Mo 🤩
 * Android Engineer,
 * Brussels
 */

data class AddressComponent(
        @SerializedName("long_name")
        val longName: String,
        @SerializedName("short_name")
        val shortName: String,
        @SerializedName("types")
        val types: List<String>
)