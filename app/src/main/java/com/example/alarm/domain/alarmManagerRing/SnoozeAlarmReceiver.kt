package com.example.alarm.domain.alarmManagerRing
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import com.example.alarm.di.App

class SnoozeAlarmReceiver : BroadcastReceiver() {
    private val tag:String = "BroadcastForAlarm"

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(tag,"onHandleIntent: SnoozeAlarmReceiver" )

        // Stop the ringtone when Action 2 is clicked
        val app = context?.applicationContext as App
        if (app.ringtone?.isPlaying == true) {
            app.ringtone?.stop()
        }
        // Remove the notification

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.cancel(1)
    }


}
