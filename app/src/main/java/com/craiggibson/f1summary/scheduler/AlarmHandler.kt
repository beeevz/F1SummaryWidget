package com.craiggibson.f1summary.scheduler

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import java.util.*

class AlarmHandler(var context: Context) {

    fun setAlarmManager() {
        var intent = Intent(context, WidgetService::class.java)
        var sender: PendingIntent

        sender = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.getBroadcast(context, 2, intent, PendingIntent.FLAG_IMMUTABLE)
        } else {
            PendingIntent.getBroadcast(context, 2, intent, 0)
        }

        val am: AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        var cal = Calendar.getInstance()
        var l: Long = cal.timeInMillis + 10000

        if (am != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                am.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, l, sender)
            } else {
                am.set(AlarmManager.RTC_WAKEUP, l, sender)
            }
        }
    }

    fun cancelAlarmManager() {
        var intent = Intent(context, WidgetService::class.java)
        var sender: PendingIntent

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            sender = PendingIntent.getBroadcast(context, 2, intent, PendingIntent.FLAG_IMMUTABLE)
        } else {
            sender = PendingIntent.getBroadcast(context, 2, intent, 0)
        }

        var am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        if (am != null) {
            am.cancel(sender)
        }
    }
}