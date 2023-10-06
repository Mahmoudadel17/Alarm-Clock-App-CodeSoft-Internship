package com.example.alarm.domain.alarmManagerRing

import android.Manifest
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.IBinder
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.alarm.R
import com.example.alarm.data.Constants
import com.example.alarm.domain.repositorys.AlarmRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ForegroundService : Service() {
    @Inject
    lateinit var repo: AlarmRepository

    override fun onBind(intent: Intent): IBinder {
        throw UnsupportedOperationException("Not yet implemented")
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null) {
            val ringtoneString = intent.getStringExtra(Constants.IntentAlarm)
            val alarmId = intent.getStringExtra(Constants.IntentAlarmId)
            if (ringtoneString != null && alarmId != null){
                startMyOwnForeground()
//
//                val window = Window(this,ringtoneString,alarmId,repo)
//                window.open()

            }
        }


        return super.onStartCommand(intent, flags, startId)
    }

    private fun startMyOwnForeground() {

        val notificationBuilder = NotificationCompat.Builder(this, Constants.channelId)
        val notification = notificationBuilder
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Alarm Clock")
            .setContentText("Alarm will Ring Now.")
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_RECOMMENDATION)
            .build()

        val notificationManager = NotificationManagerCompat.from(this)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) { return }
        notificationManager.notify(1, notification)
    }
}
