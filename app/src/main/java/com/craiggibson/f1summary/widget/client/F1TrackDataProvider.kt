package com.craiggibson.f1summary.widget.client

import android.util.Log
import com.craiggibson.f1summary.Constants
import com.craiggibson.f1summary.Constants.BASE_URL
import com.craiggibson.f1summary.common.Secrets
import com.craiggibson.f1summary.widget.model.RacesResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class F1TrackDataProvider {

    private val service: RaceService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        service = retrofit.create(RaceService::class.java)
    }

    fun getF1TrackResponse(
        year: String,
        timezone: String,
        racesCallback: (response: String?) -> Unit
    ) {
        val call = service.retrieveRaces(year, timezone, Secrets.rapidApiKey, Secrets.rapidApiHost)
        val coroutineScope = CoroutineScope(Job() + Dispatchers.Default)

        coroutineScope.launch {
            call?.enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>?, t: Throwable?) {
                    Log.e("Widget", "Problem calling F1 API {${t?.message}}")
                    racesCallback(null)
                }

                override fun onResponse(
                    call: Call<String>?,
                    response: Response<String>?
                ) {
                    if (response?.body() == null) {
                        onFailure(call, Throwable("Response was null"))
                    } else {
                        racesCallback(response.body())
                    }
                }
            })
        }
    }
}