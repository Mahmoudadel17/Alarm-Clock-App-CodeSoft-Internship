package com.example.alarm.domain.alarmManagerRing

import android.app.Notification
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationCompat
import com.example.alarm.R
import com.example.alarm.data.Constants
import com.example.alarm.domain.repositorys.AlarmRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class ForegroundService : Service() {

    @Inject
    lateinit var repo: AlarmRepository
    override fun onBind(intent: Intent): IBinder {
        throw UnsupportedOperationException("Not yet implemented")
    }


    @RequiresApi(Build.VERSION_CODES.P)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null) {
            val ringtoneString = intent.getStringExtra(Constants.IntentAlarm)
            val alarmId = intent.getStringExtra(Constants.IntentAlarmId)
            if (ringtoneString != null && alarmId != null){
                startMyOwnForeground()

                val window = Window(this,ringtoneString,alarmId,repo)
                window.open()


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
        startForeground(1, notification)
    }
}
