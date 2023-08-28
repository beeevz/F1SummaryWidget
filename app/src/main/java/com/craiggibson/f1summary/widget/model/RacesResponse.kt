package com.craiggibson.f1summary.widget.model

data class RacesResponse(
    val get: String?,
    val parameters: Parameters?,
    val errors: List<String>?,
    val results: Int?,
    val response: List<Race>?
)

data class Parameters(
    val season: String?
)
