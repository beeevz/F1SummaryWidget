package com.craiggibson.f1summary.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.RemoteViews
import com.craiggibson.f1summary.Constants
import com.craiggibson.f1summary.R
import com.craiggibson.f1summary.scheduler.AlarmHandler
import com.craiggibson.f1summary.service.PreferencesStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

/**
 * Implementation of App Widget functionality.
 */
class F1TrackSummaryWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        // Construct the RemoteViews object

        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Stop updating the widget
        val alarmHandler = AlarmHandler(context)
        alarmHandler.cancelAlarmManager()
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    PreferencesStorage.init(context) // Initializing storage with the context of the "mini" app
    val f1ViewModel = F1TrackSummaryViewModel()
    runBlocking {
        f1ViewModel.getF1WidgetData() {
            val views = RemoteViews(context.packageName, R.layout.f1_track_summary_widget)
            views.setTextViewText(R.id.practice1Tv, it.sessionOne)
//            views.setTextViewText(R.id.trackImage, it.imageURL)
            views.setImageViewUri(R.id.trackImage, Uri.parse(it.imageURL))
            views.setTextViewText(R.id.trackName, it.title)
            views.setTextViewText(R.id.practice1Tv, it.sessionOne)
            views.setTextViewText(R.id.practice1Tv, it.sessionOne)
            views.setTextViewText(R.id.practice2Tv, it.sessionTwo)
            views.setTextViewText(R.id.practice3Tv, it.sessionThree)
            views.setTextViewText(R.id.qualifyingTv, it.sessionFour)
            views.setTextViewText(R.id.raceTv, it.sessionFive)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    val alarmHandler = AlarmHandler(context)
    alarmHandler.cancelAlarmManager()
    alarmHandler.setAlarmManager()


    // Instruct the widget manager to update the widget

    Log.v("TEST", "UPDATE CALLED")
}