package com.craiggibson.f1summary.widget.model

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*

data class UiRace(val sessionDays: TreeMap<OffsetDateTime, MutableList<Race>>?) {
    var title: String? = null
    var imageURL: String? = null

    var sessionOne: String? = null // Practice 1
    var sessionTwo: String? = null // Practice 2 OR Qualifying for Sprint Race

    var sessionThree: String? = null // Practice 3 OR Practice 2
    var sessionFour: String? = null // Qualifying OR Sprint Qualifying

    var sessionFive: String? = null // Race

    init {
        val firstSessionDay = getFirstSessionOfWeekend()
        imageURL = firstSessionDay?.circuit?.image
        title = firstSessionDay?.circuit?.name
        if (isSprintQualifying()) {
            setSprintQualifyingRaceUI()
        } else {
            setQualifyingRaceUI()
        }
    }

    private fun isSprintQualifying(): Boolean {
        sessionDays?.let { days ->
            for (day in days.keys) {
                days[day]?.let { sessionList ->
                    for (session in sessionList) {
                        if (session.type!!.equals(SessionType.SPRINT_QUALIFYING)) {
                            return true
                        }
                    }
                }
            }
        }
        return false
    }

    private fun setSprintQualifyingRaceUI() {
        val dateFormat = SimpleDateFormat("HH:mm")
        sessionOne = getFormattedTime(getSession(SessionType.PRACTICE_1)?.date, dateFormat)
        sessionTwo = getFormattedTime(getSession(SessionType.QUALIFYING_1)?.date, dateFormat)
        sessionThree = getFormattedTime(getSession(SessionType.PRACTICE_2)?.date, dateFormat)
        sessionFour = getFormattedTime(getSession(SessionType.SPRINT_QUALIFYING)?.date, dateFormat)
        sessionFive = getFormattedTime(getSession(SessionType.RACE)?.date, dateFormat)
    }

    private fun setQualifyingRaceUI() {
        val dateFormat = SimpleDateFormat("HH:mm")
        sessionOne = getFormattedTime(getSession(SessionType.PRACTICE_1)?.date, dateFormat)
        sessionTwo = getFormattedTime(getSession(SessionType.PRACTICE_2)?.date, dateFormat)
        sessionThree = getFormattedTime(getSession(SessionType.PRACTICE_3)?.date, dateFormat)
        sessionFour = getFormattedTime(getSession(SessionType.QUALIFYING_1)?.date, dateFormat)
        sessionFive = getFormattedTime(getSession(SessionType.RACE)?.date, dateFormat)
    }

    private fun getFirstSessionOfWeekend(): Race? {
        return sessionDays?.firstEntry()?.value?.get(0)
    }

    private fun getFormattedTime(dateForFormatting: String?, dateFormat: SimpleDateFormat): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXXXX")
        val date = LocalDateTime.parse(dateForFormatting, formatter)
        return date.toLocalTime().toString()
    }

    private fun getSession(sessionType: SessionType): Race? {
        sessionDays?.let { sessionDays ->
            for (day in sessionDays.keys) {
                val daySessions = sessionDays[day]
                if (daySessions != null) {
                    for (session: Race in daySessions) {
                        if (sessionType.session == session.type) return session
                    }
                }
            }
        }
        return null
    }
}