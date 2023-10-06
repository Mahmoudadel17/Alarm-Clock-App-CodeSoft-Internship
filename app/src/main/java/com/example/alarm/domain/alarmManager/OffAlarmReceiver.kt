package com.example.alarm.domain.alarmManager


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import com.example.alarm.data.Constants
import com.example.alarm.di.App
import com.example.alarm.domain.repositorys.AlarmRepository
import dagger.hilt.android.AndroidEntryPoint

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class OffAlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var repo: AlarmRepository

    private val tag:String = "BroadcastForAlarm"
    private val coroutineScope= CoroutineScope(Dispatchers.IO)


    override fun onReceive(context: Context?, intent: Intent?) {

        val alarmId = intent?.getStringExtra(Constants.IntentAlarmId)?.toInt()
        Log.d(tag,"onHandleIntent: OffAlarmReceiver $alarmId" )
        coroutineScope.launch {
            if (alarmId != null) {
                // get alarm from database
                val alarm = repo.getAlarm(alarmId)
                repo.removeAlarmById(alarmId)

                // if alarm not repeated make it off
                val isRepeating = alarm.isRepeated
                if (!isRepeating){
                    alarm.isOn = alarm.isOn.not()
                    // update alarm in database
                    repo.addAlarm(alarm)
                }

            }
        }


        // Stop the ringtone when Action 2 is clicked
        val app = context?.applicationContext as App
        if (app.ringtone?.isPlaying == true) {
            app.ringtone?.stop()
        }
        // Remove the notification
        val notificationManager = NotificationManagerCompat.from(context!!)
        notificationManager.cancel(1)
    }

}

