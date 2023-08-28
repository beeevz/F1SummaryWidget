package com.craiggibson.f1summary.widget.client

import com.craiggibson.f1summary.Constants
import com.craiggibson.f1summary.service.PreferencesStorage
import com.craiggibson.f1summary.widget.model.Race
import com.craiggibson.f1summary.widget.model.RacesResponse
import com.google.gson.Gson

class F1TrackStorageProvider {
    fun getF1TrackResponse(fileName: String, racesCallback: (String?) -> Unit) {
        val fileContent: String? = PreferencesStorage.readData(fileName)
        racesCallback(fileContent)
    }
}