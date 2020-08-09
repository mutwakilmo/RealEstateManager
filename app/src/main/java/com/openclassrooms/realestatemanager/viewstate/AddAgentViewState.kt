package com.openclassrooms.realestatemanager.viewstate

import com.openclassrooms.realestatemanager.view.addAgent.ErrorSourceAddAgent
import com.openclassrooms.realestatemanager.base.realStateManagerIntent
import com.openclassrooms.realestatemanager.base.realStateManageresult
import com.openclassrooms.realestatemanager.base.realStateManagerViewState

/**
 * Created by Mutwakil-Mo 🤩
 * Android Engineer,
 * Brussels
 */
data class AddAgentViewState(
        val isLoading: Boolean = false,
        val isSaved: Boolean = false,
        val errors: List<ErrorSourceAddAgent>? = null
) : realStateManagerViewState

sealed class AddAgentIntent : realStateManagerIntent {
    data class AddAgentToDBIntent(val pictureUrl: String?,
                                  val urlFromDevice: String?,
                                  val firstName: String,
                                  val lastName: String,
                                  val email: String,
                                  val phoneNumber: String) : AddAgentIntent()
}

sealed class AddAgentResult : realStateManageresult {
    data class AddAgentToDBResult(val errorSource: List<ErrorSourceAddAgent>?) : AddAgentResult()
}