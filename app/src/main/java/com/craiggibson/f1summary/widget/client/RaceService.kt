package com.craiggibson.f1summary.widget.client

import com.craiggibson.f1summary.widget.model.RacesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RaceService {
    @GET("/races")
    fun retrieveRaces(
        @Query("season") one: String,
        @Query("timezone") timezone: String,
        @Header("x-rapidapi-key") rapidApiKey : String?,
        @Header("x-rapidapi-host") rapidApiHost: String?,
    ): Call<String>?
}