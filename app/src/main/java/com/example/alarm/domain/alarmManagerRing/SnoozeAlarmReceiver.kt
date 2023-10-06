package com.example.alarm.domain.alarmManagerRing
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import com.example.alarm.data.Constants
import com.example.alarm.data.local.Alarm
import com.example.alarm.di.App
import com.example.alarm.domain.repositorys.AlarmRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SnoozeAlarmReceiver @Inject constructor(
    private val repo:AlarmRepository,
    private val alarmManager: AlarmManager
) : BroadcastReceiver() {
    private val tag:String = "BroadcastForAlarm"
    private lateinit var alarm:Alarm
    private val coroutineScope= CoroutineScope(Dispatchers.IO)
    override fun onReceive(context: Context?, intent: Intent?) {
        val alarmId = intent?.getStringExtra(Constants.IntentAlarmId)?.toInt()
        Log.d(tag,"onHandleIntent: SnoozeAlarmReceiver $alarmId" )
        if (alarmId != null){
            coroutineScope.launch {
                alarm = repo.getAlarm(alarmId)
            }

        }
        // Stop the ringtone when Action 2 is clicked
        val app = context?.applicationContext as App
        if (app.ringtone?.isPlaying == true) {
            app.ringtone?.stop()
        }
        // Remove the notification

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.cancel(1)

        alarmManager.addNewAlarmSnooze(context,alarm)
    }



}