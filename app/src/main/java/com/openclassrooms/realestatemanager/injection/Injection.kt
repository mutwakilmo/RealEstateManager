package com.openclassrooms.realestatemanager.injection

import android.content.Context
import com.openclassrooms.realestatemanager.data.api.GeoApiService
import com.openclassrooms.realestatemanager.data.database.RealStateManagerDatabase
import com.openclassrooms.realestatemanager.data.repository.AgentRepository
import com.openclassrooms.realestatemanager.data.repository.CurrencyRepository
import com.openclassrooms.realestatemanager.data.repository.PropertyRepository
import com.openclassrooms.realestatemanager.data.repository.SaveDataRepository

/**
 * Created by Mutwakil-Mo 🤩
 * Android Engineer,
 * Brussels
 */
class Injection {

    companion object {

        private fun providesAgentRepository(context: Context): AgentRepository {
            val database = RealStateManagerDatabase.getDatabase(context)
            return AgentRepository.getAgentRepository(database.agentDao())
        }

        private fun providesPropertyRepository(context: Context): PropertyRepository {
            val database = RealStateManagerDatabase.getDatabase(context)
            val geocodingApi = GeoApiService.create()
            return PropertyRepository.getPropertyRepository(database.propertyDao(), geocodingApi)
        }

        private fun providesCurrencyRepository(context: Context) = CurrencyRepository.getCurrencyRepository(context)


        private fun providesSaveDataRepository(context: Context) = SaveDataRepository.getSaveDataRepository(context)


        fun providesViewModelFactory(context: Context): ViewModelFactory {
            val agentRepository = providesAgentRepository(context)
            val propertyRepository = providesPropertyRepository(context)
            val currencyRepository = providesCurrencyRepository(context)
            val saveDataRepository = providesSaveDataRepository(context)
            return ViewModelFactory(agentRepository, propertyRepository, currencyRepository, saveDataRepository)
        }
    }
}