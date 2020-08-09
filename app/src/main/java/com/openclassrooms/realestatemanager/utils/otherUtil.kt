package com.openclassrooms.realestatemanager.utils

import android.content.Intent
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.openclassrooms.realestatemanager.view.addProperty.ActionType
import com.openclassrooms.realestatemanager.view.addProperty.AddPropertyActivity
import java.util.*

/**
 * Created by Mutwakil-Mo 🤩
 * Android Engineer,
 * Brussels
 */

fun displayData(message: String){
    Log.e("REMData", message)
}

var idGenerated: String = ""
    get() {
        field = UUID.randomUUID().toString()
        return field
    }

var todaysDate: Date = Calendar.getInstance(Locale.CANADA).time

fun modifyPropertyIntent(activity: FragmentActivity): Intent = Intent(
        activity, AddPropertyActivity::class.java).apply {
    putExtra(ACTION_TYPE_ADD_PROPERTY, ActionType.MODIFY_PROPERTY.actionName)
}
