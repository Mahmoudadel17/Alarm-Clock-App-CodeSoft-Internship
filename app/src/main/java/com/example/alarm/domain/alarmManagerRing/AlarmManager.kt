package com.example.alarm.domain.alarmManagerRing

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.alarm.data.Constants
import com.example.alarm.data.local.Alarm
import java.time.LocalTime
import java.util.Calendar

class AlarmManager {

     fun addNewAlarm(context:Context, alarm: Alarm){
        val intent = Intent(context, BroadcastForAlarm::class.java)
        intent.putExtra(Constants.IntentAlarm,alarm.ringtone.toString())
        intent.putExtra(Constants.IntentAlarmId,alarm.id.toString())

        val alarmOperation = PendingIntent.getBroadcast(
            context,
            alarm.id, // alarm id
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmRtcWakeup = AlarmManager.RTC_WAKEUP

        val calendar = Calendar.getInstance()
        var dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        // check if time before current time set alarm tomorrow
        if (alarm.hour < LocalTime.now().hour || alarm.minute < LocalTime.now().minute ){
            dayOfMonth+=1
        }

        calendar.set(Calendar.YEAR,calendar.get(Calendar.YEAR))
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH))
        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth)
        calendar.set(Calendar.HOUR_OF_DAY,alarm.hour)
        calendar.set(Calendar.MINUTE,alarm.minute)
        // make set to alarm we need object of Calender in Alarm object
        val alarmManager: AlarmManager =  context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        if (alarm.isRepeated){
            val intervalMillis = 1 * 60 * 1000L
            alarmManager.setRepeating (alarmRtcWakeup,calendar.timeInMillis,intervalMillis,alarmOperation)

        }else{
            alarmManager.set(alarmRtcWakeup,calendar.timeInMillis,alarmOperation)
        }

    }

     fun removeAlarm(context:Context,alarm: Alarm){
        val intent = Intent(context, BroadcastForAlarm::class.java)

        val alarmOperation = PendingIntent.getBroadcast(
            context,
            alarm.id, // alarm id
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager: AlarmManager =  context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(alarmOperation)

    }

}