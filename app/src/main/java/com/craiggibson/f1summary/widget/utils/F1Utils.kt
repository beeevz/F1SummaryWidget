package com.craiggibson.f1summary.widget.utils

import com.craiggibson.f1summary.widget.model.Race
import com.craiggibson.f1summary.widget.model.SessionType
import java.time.OffsetDateTime
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.collections.HashMap

class F1Utils {
    companion object {
        fun convertRaceListToSortedMap(listOfRaces: List<Race>): HashMap<String, TreeMap<OffsetDateTime, MutableList<Race>>> {
            val map = HashMap<String, TreeMap<OffsetDateTime, MutableList<Race>>>()

            for (race in listOfRaces) {
                val offsetDateTime = OffsetDateTime.parse(race.date)
                val raceDate = offsetDateTime.truncatedTo(ChronoUnit.DAYS)
                race.competition?.name?.let {
                    if (map.containsKey(it)) {
                        if (map[it]!!.containsKey(raceDate)) {
                            map[it]!![raceDate]?.add(race)
                        } else {
                            map[it]!!.put(raceDate, mutableListOf(race))
                        }
                    } else {
                        val sessionMap = TreeMap<OffsetDateTime, MutableList<Race>>()
                        sessionMap.put(raceDate, mutableListOf(race))
                        map.put(it, sessionMap)
                    }
                }
            }

            return map
        }
    }
}