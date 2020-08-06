package com.openclassrooms.realestatemanager.utils.extensions

import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.data.entity.Amenity
import com.openclassrooms.realestatemanager.utils.TypeFacility

/**
 * Created by Mutwakil-Mo 🤩
 * Android Engineer,
 * Brussels
 */

fun Amenity.toDrawable(): Int{
    return when(this.type){
        TypeFacility.SCHOOL -> R.drawable.school_icon
        TypeFacility.PLAYGROUND -> R.drawable.playground_icon
        TypeFacility.SHOP -> R.drawable.shopping_icon
        TypeFacility.BUSES -> R.drawable.bus_icon
        TypeFacility.SUBWAY -> R.drawable.subway_icon
        TypeFacility.PARK -> R.drawable.park_icon
    }
}