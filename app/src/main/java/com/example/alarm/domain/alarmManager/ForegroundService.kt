package com.example.alarm.domain.alarmManager

import android.app.Notification
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.alarm.R
import com.example.alarm.data.Constants

class ForegroundService : Service() {

    override fun onBind(intent: Intent): IBinder {
        throw UnsupportedOperationException("Not yet implemented")
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null) {
            val ringtoneString = intent.getStringExtra(Constants.IntentAlarm)
            val alarmId = intent.getStringExtra(Constants.IntentAlarmId)
            if (ringtoneString != null && alarmId != null){
                startMyOwnForeground()

                val window = Window(this,ringtoneString,alarmId)
                window.open()
            }
        }


        return super.onStartCommand(intent, flags, startId)
    }

    private fun startMyOwnForeground() {
        val notificationBuilder = NotificationCompat.Builder(this, Constants.channelId)
        val notification = notificationBuilder.setOngoing(true)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Alarm Clock")
            .setContentText("Alarm will Ring Now.")
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationManager.IMPORTANCE_MIN)
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()
        startForeground(2, notification)
    }
}
