package com.openclassrooms.realestatemanager.utils.extensions

import android.widget.EditText

/**
 * Created by Mutwakil-Mo 🤩
 * Android Engineer,
 * Brussels
 */


fun EditText.toDouble(): Double?{
    return this.text.toString().toDoubleOrNull()
}

fun EditText.toInt(): Int?{
    return this.text.toString().toIntOrNull()
}
