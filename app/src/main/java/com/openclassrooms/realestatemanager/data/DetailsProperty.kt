package com.openclassrooms.realestatemanager.data

import com.openclassrooms.realestatemanager.data.entity.Picture
import com.openclassrooms.realestatemanager.utils.TypeFacility

/**
 * Created by Mutwakil-Mo 🤩
 * Android Engineer,
 * Brussels
 */

data class DetailsProperty(
        val id: String,
        val type: String,
        val price: Double?,
        val surface: Double?,
        val rooms: Int?,
        val bedrooms: Int?,
        val bathrooms: Int?,
        val description: String,
        val onMarketSince: String,
        val isSold: Boolean,
        val sellDate: String?,
        val agent: String?,
        val address: String,
        val neighborhood: String,
        val pictures: List<Picture>,
        val amenities: List<TypeFacility>
)