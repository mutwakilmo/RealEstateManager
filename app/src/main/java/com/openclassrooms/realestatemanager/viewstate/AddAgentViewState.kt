package com.openclassrooms.realestatemanager.viewstate

import com.openclassrooms.realestatemanager.view.addAgent.ErrorSourceAddAgent
import com.openclassrooms.realestatemanager.base.RealStateManagerIntent
import com.openclassrooms.realestatemanager.base.RealStateManagerResult
import com.openclassrooms.realestatemanager.base.RealStateManagerViewState

/**
 * Created by Mutwakil-Mo 🤩
 * Android Engineer,
 * Brussels
 */
data class AddAgentViewState(
        val isLoading: Boolean = false,
        val isSaved: Boolean = false,
        val errors: List<ErrorSourceAddAgent>? = null
) : RealStateManagerViewState

sealed class AddAgentIntent : RealStateManagerIntent {
    data class AddAgentToDBIntent(val pictureUrl: String?,
                                  val urlFromDevice: String?,
                                  val firstName: String,
                                  val lastName: String,
                                  val email: String,
                                  val phoneNumber: String) : AddAgentIntent()
}

sealed class AddAgentResult : RealStateManagerResult {
    data class AddAgentToDBResult(val errorSource: List<ErrorSourceAddAgent>?) : AddAgentResult()
}