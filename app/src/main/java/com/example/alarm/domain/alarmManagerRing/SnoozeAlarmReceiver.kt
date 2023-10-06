package com.example.alarm.domain.alarmManagerRing
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import com.example.alarm.data.Constants
import com.example.alarm.data.local.Alarm
import com.example.alarm.di.App
import java.time.LocalTime
import java.util.Calendar

class SnoozeAlarmReceiver : BroadcastReceiver() {
    private val tag:String = "BroadcastForAlarm"

    override fun onReceive(context: Context?, intent: Intent?) {
        val alarmId = intent?.getStringExtra(Constants.IntentAlarmId)?.toInt()
        Log.d(tag,"onHandleIntent: SnoozeAlarmReceiver $alarmId" )

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