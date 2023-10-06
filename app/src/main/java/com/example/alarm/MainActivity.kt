package com.example.alarm


import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.alarm.data.Constants
import com.example.alarm.data.local.Alarm
import com.example.alarm.domain.alarmManagerRing.BroadcastForAlarm
import com.example.alarm.presintation.navigation.AppNavigate
import com.example.alarm.presintation.screensPreview.alarmScreen.AlarmScreenViewModel
import com.example.alarm.presintation.screensPreview.newAlarmScreen.NewAlarmScreenViewModel
import com.example.alarm.presintation.screensPreview.updateScreen.UpdateScreenViewModel
import com.example.alarm.ui.theme.AlarmTheme
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalTime
import java.util.Calendar

@AndroidEntryPoint
class MainActivity() : ComponentActivity()  {
    private val alarmScreenViewModel by viewModels<AlarmScreenViewModel>()
    private val newAlarmScreenViewModel by viewModels<NewAlarmScreenViewModel>()
    private val updateScreenViewModel by viewModels<UpdateScreenViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val isDark =  isSystemInDarkTheme()
            var darkTheme by remember { mutableStateOf(isDark) }
            AlarmTheme(darkTheme) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigate(darkTheme,alarmScreenViewModel,newAlarmScreenViewModel,updateScreenViewModel,addNewAlarm,removeAlarm) { darkTheme = darkTheme.not() }
                }
            }


        }
    }


    private val addNewAlarm = fun(alarm: Alarm){
        val intent = Intent(this, BroadcastForAlarm::class.java)
        intent.putExtra(Constants.IntentAlarm,alarm.ringtone.toString())
        intent.putExtra(Constants.IntentAlarmId,alarm.id.toString())

        val alarmOperation = PendingIntent.getBroadcast(
            this,
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
        val alarmManager: AlarmManager =  getSystemService(Context.ALARM_SERVICE) as AlarmManager
        if (alarm.isRepeated){
            val intervalMillis = 5 * 60 * 1000L
            alarmManager.setRepeating (alarmRtcWakeup,calendar.timeInMillis,intervalMillis,alarmOperation)

        }else{
            alarmManager.set(alarmRtcWakeup,calendar.timeInMillis,alarmOperation)
        }

    }

    private val removeAlarm =  fun(alarm: Alarm){
        val intent = Intent(this, BroadcastForAlarm::class.java)

        val alarmOperation = PendingIntent.getBroadcast(
            this,
            alarm.id, // alarm id
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager: AlarmManager =  getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(alarmOperation)

    }


}

