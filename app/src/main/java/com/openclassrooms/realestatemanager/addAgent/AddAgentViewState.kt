package com.openclassrooms.realestatemanager.addAgent

import com.openclassrooms.realestatemanager.base.REALESTATEMANAGERIntent
import com.openclassrooms.realestatemanager.base.REALESTATEMANAGERResult
import com.openclassrooms.realestatemanager.base.REALESTATEMANAGERViewState

/**
 * Created by Mutwakil-Mo 🤩
 * Android Engineer,
 * Brussels
 */
data class AddAgentViewState(
        val isLoading: Boolean = false,
        val isSaved: Boolean = false,
        val errors: List<ErrorSourceAddAgent>? = null
) : REALESTATEMANAGERViewState

sealed class AddAgentIntent : REALESTATEMANAGERIntent {
    data class AddAgentToDBIntent(val pictureUrl: String?,
                                  val urlFromDevice: String?,
                                  val firstName: String,
                                  val lastName: String,
                                  val email: String,
                                  val phoneNumber: String) : AddAgentIntent()
}

sealed class AddAgentResult : REALESTATEMANAGERResult {
    data class AddAgentToDBResult(val errorSource: List<ErrorSourceAddAgent>?) : AddAgentResult()
}