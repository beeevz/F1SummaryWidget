package com.craiggibson.f1summary.widget

import com.craiggibson.f1summary.widget.client.F1TrackClient
import com.craiggibson.f1summary.widget.model.Race
import com.craiggibson.f1summary.widget.model.UiRace
import com.craiggibson.f1summary.widget.utils.F1Utils
import java.time.OffsetDateTime
import java.util.*

class F1TrackSummaryViewModel {

    fun getF1WidgetData(updateRaceUI: (UiRace) -> Unit) {
        val f1TrackClient = F1TrackClient()
        f1TrackClient.getRaces {
            it?.let {
                val race = getNextRace(F1Utils.convertRaceListToSortedMap(it))
                val uiRace = UiRace(race)
                updateRaceUI(uiRace)
            }
        }
    }

    private fun getNextRace(races: HashMap<String, TreeMap<OffsetDateTime, MutableList<Race>>>)
            : TreeMap<OffsetDateTime, MutableList<Race>>? {
        val dateTimeNow = OffsetDateTime.parse("2022-11-15T00:00:00Z")
        var race: TreeMap<OffsetDateTime, MutableList<Race>>? = null
        for (fullRace in races.keys) {
            val entry = races[fullRace]
            entry?.let {
                for (session in entry.keys)
                    entry[session]?.let {
                        val raceSession = it.last()
                        val raceSessionTime = OffsetDateTime.parse(raceSession.date)
                        if (session.isAfter(dateTimeNow) && session.isBefore(raceSessionTime)) {
                            race = races[fullRace]
                        }
                    }
            }
        }
        return race
    }
}