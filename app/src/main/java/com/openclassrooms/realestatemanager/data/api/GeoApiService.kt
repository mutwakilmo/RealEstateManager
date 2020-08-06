package com.openclassrooms.realestatemanager.data.api

import com.openclassrooms.realestatemanager.data.api.model.GeoApiResponse
import com.openclassrooms.realestatemanager.utils.BASE_URL_MAP_API
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Mutwakil-Mo 🤩
 * Android Engineer,
 * Brussels
 */
interface GeoApiService{

    @GET("geocode/json?")
    fun getLocationFromAddress(
            @Query("address") address: String,
            @Query("key") apiKey: String
    ): Observable<GeoApiResponse>

    companion object {

        fun create(): GeoApiService {
            val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL_MAP_API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()

            return retrofit.create(GeoApiService::class.java)
        }
    }



}