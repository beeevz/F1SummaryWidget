package com.craiggibson.f1summary.widget.client

import com.craiggibson.f1summary.BuildConfig
import com.craiggibson.f1summary.Constants.RACE_FILE_NAME
import com.craiggibson.f1summary.service.PreferencesStorage
import com.craiggibson.f1summary.widget.model.Race
import com.craiggibson.f1summary.widget.model.RacesResponse
import com.google.gson.Gson
import java.util.*


class F1TrackClient {
    private val f1TrackDataProvider = F1TrackDataProvider()
    private val f1TrackStorageProvider = F1TrackStorageProvider()

    companion object {
        private val FILE_NAME = "${getYear()}_${RACE_FILE_NAME}"

        // Static functions used inside of the class
        private fun getYear() = Calendar.getInstance().get(Calendar.YEAR).toString()
        private fun getTimezone() = BuildConfig.TIMEZONE
    }

    fun getRaces(callback: (races: List<Race>?) -> Unit) {
        val racesInStorage = getRacesFromStorage()
        if (racesInStorage == null) {
            f1TrackDataProvider.getF1TrackResponse(
                year = getYear(),
                timezone = getTimezone(),
                racesCallback = { storageResponse ->
                    PreferencesStorage.writeData(FILE_NAME, storageResponse)
                    val response = convertStringToListOfRaces(storageResponse)
                    callback(response.response)
                }
            )
        } else {
            // Get List of Races from Storage
            f1TrackStorageProvider.getF1TrackResponse(
                fileName = FILE_NAME,
                racesCallback = { storageResponse ->
                    val response = convertStringToListOfRaces(storageResponse)
                    callback(response.response)
                }
            )
        }
    }

    private fun convertStringToListOfRaces(response: String?): RacesResponse {
        val gson = Gson()
        return gson.fromJson(response, RacesResponse::class.java)
    }

    private fun getRacesFromStorage(): String? {
        return PreferencesStorage.readData(FILE_NAME)
    }
}
