package com.craiggibson.f1summary.scheduler

import android.appwidget.AppWidgetManager
import android.content.*
import android.util.Log
import com.craiggibson.f1summary.widget.F1TrackSummaryWidget

class WidgetService : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        // Wake the device
        if (context != null) {
            WakeLocker.acquire(context)
        }

        var pref = context!!.getSharedPreferences("PREFS", 0)
        var value = pref.getInt("value", 1 )
        value++
        var editor = pref.edit()
        editor.putInt("value", value)

        editor.apply()

        //force widget update
        var widgetIntent = Intent(context, F1TrackSummaryWidget::class.java)
        widgetIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE)

        var ids = AppWidgetManager.getInstance(context).getAppWidgetIds(ComponentName(context, F1TrackSummaryWidget::class.java))
        widgetIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
        context.sendBroadcast(widgetIntent)

        Log.d("WIDGET", "Widget set to update")
        //go back to sleep
        WakeLocker.release()
    }
}