package com.openclassrooms.realestatemanager.viewmodel

import com.openclassrooms.realestatemanager.viewstate.AddAgentIntent
import com.openclassrooms.realestatemanager.viewstate.AddAgentResult
import com.openclassrooms.realestatemanager.viewstate.AddAgentViewState
import com.openclassrooms.realestatemanager.view.addAgent.ErrorSourceAddAgent
import com.openclassrooms.realestatemanager.view.addAgent.ErrorSourceAddAgent.*
import com.openclassrooms.realestatemanager.data.entity.Agent
import com.openclassrooms.realestatemanager.data.repository.AgentRepository
import com.openclassrooms.realestatemanager.base.BaseViewModel
import com.openclassrooms.realestatemanager.base.LoadingContentError
import com.openclassrooms.realestatemanager.base.realStateManagerViewModel
import com.openclassrooms.realestatemanager.utils.extensions.isCorrectEmail
import com.openclassrooms.realestatemanager.utils.extensions.isCorrectName
import com.openclassrooms.realestatemanager.utils.extensions.isCorrectPhoneNumber
import com.openclassrooms.realestatemanager.utils.idGenerated
import com.openclassrooms.realestatemanager.utils.todaysDate
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Created by Mutwakil-Mo 🤩
 * Android Engineer,
 * Brussels
 */
class AddAgentViewModel (
        private val agentRepository: AgentRepository
) : BaseViewModel<AddAgentViewState>(), realStateManagerViewModel<AddAgentIntent, AddAgentResult> {

    private var currentViewState = AddAgentViewState()
        set(value) {
            field = value
            viewStateLD.value = value
        }

    private var addAgentsJob: Job? = null

    override fun actionFromIntent(intent: AddAgentIntent) {
        when(intent) {
            is AddAgentIntent.AddAgentToDBIntent -> {
                addAgentToDBRequest(
                        intent.pictureUrl,
                        intent.urlFromDevice,
                        intent.firstName,
                        intent.lastName,
                        intent.email,
                        intent.phoneNumber)

            }
        }
    }

    override fun resultToViewState(result: LoadingContentError<AddAgentResult>) {
        currentViewState = when (result){
            is LoadingContentError.Content ->{
                when(result.packet){
                    is AddAgentResult.AddAgentToDBResult -> {
                        currentViewState.copy(
                                errors = null,
                                isSaved = true,
                                isLoading = false)
                    }
                }
            }

            is LoadingContentError.Loading ->{
                currentViewState.copy(isLoading = true)

            }

            is LoadingContentError.Error ->{
                when(result.packet){
                    is AddAgentResult.AddAgentToDBResult -> {
                        currentViewState.copy(
                                errors = result.packet.errorSource,
                                isLoading = false)
                    }
                }

            }
        }
    }

    private fun addAgentToDBRequest(
            urlPicture: String?,
            urlFromDevice: String?,
            firstName: String,
            lastName: String,
            email: String,
            phoneNumber: String
    ){
        resultToViewState(LoadingContentError.Loading())

        if(addAgentsJob?.isActive == true) addAgentsJob?.cancel()

        fun checkErrors(): List<ErrorSourceAddAgent>{
            val listErrorInputs = mutableListOf<ErrorSourceAddAgent>()
            if(!firstName.isCorrectName()) listErrorInputs.add(FIRST_NAME_INCORRECT)
            if(!lastName.isCorrectName()) listErrorInputs.add(LAST_NAME_INCORRECT)
            if(!email.isCorrectEmail()) listErrorInputs.add(EMAIL_INCORRECT)
            if(!phoneNumber.isCorrectPhoneNumber()) listErrorInputs.add(PHONE_INCORRECT)

            return listErrorInputs
        }
        val listErrors = checkErrors().toMutableList()
        val agentId = idGenerated

        fun emitResult(){
            if (listErrors.isEmpty()) {
                resultToViewState(LoadingContentError.Content(AddAgentResult.AddAgentToDBResult(null)))
            } else {
                resultToViewState(LoadingContentError.Error(AddAgentResult.AddAgentToDBResult(listErrors)))
            }
        }

        fun createAgent(){
            val agent = Agent(agentId, firstName, lastName, email, phoneNumber, urlPicture, todaysDate)
            addAgentsJob = launch {
                agentRepository.createAgent(agent)
                        .addOnSuccessListener { emitResult() }
            }
        }

        fun updatePictureToNetwork(pictureUrl: String){
            agentRepository.uploadAgentPhotoInNetwork(pictureUrl, agentId)
                    .addOnFailureListener { listErrors.add(UPDATING_PICTURE) }
        }


        if(listErrors.isEmpty()){
            urlFromDevice?.let { updatePictureToNetwork(it) }
            createAgent()
        } else {
            emitResult()
        }

    }
}

