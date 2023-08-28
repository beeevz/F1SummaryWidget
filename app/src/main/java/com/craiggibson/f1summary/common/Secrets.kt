package com.craiggibson.f1summary.common

object Secrets {
    var rapidApiKey: String? = null
    var rapidApiHost: String? = null

    fun assignValues(rapidApiKey: String, rapidApiHost: String) {
        Secrets.rapidApiKey = rapidApiKey
        Secrets.rapidApiHost = rapidApiHost
    }
}