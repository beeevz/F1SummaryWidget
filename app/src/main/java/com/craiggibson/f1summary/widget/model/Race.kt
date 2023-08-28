package com.craiggibson.f1summary.widget.model

data class Race(
    val id: Int?,
    val competition: Competition?,
    val circuit: Circuit?,
    val season: Int?,
    val type: String?,
    val laps: Laps?,
    val fastestLap: FastestLap?,
    val distance: String?,
    val timeZone: String?,
    val date: String?,
    val weather: String?,
    val status: String?
)

data class Competition(
    val id: Int?,
    val name: String?,
    val location: Location?
)

data class Location(
    val country: String?,
    val city: String?
)

data class Circuit(
    val id: Int?,
    val name: String?,
    val image: String?
)

data class Laps(
    val current: Int?,
    val total: Int?,
)

data class FastestLap(
    val driver: Driver,
    val time: String
)

data class Driver(
    val id: Int
)

enum class SessionType(val session: String) {
    PRACTICE_1("1st Practice"),
    PRACTICE_2("2nd Practice"),
    PRACTICE_3("3rd Practice"),
    QUALIFYING_1("1st Qualifying"),
    QUALIFYING_2("2nd Qualifying"),
    QUALIFYING_3("3rd Qualifying"),
    SPRINT_QUALIFYING("Sprint Qualifying"),
    RACE("Race")
}