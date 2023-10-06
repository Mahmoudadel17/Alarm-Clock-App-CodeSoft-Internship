package com.example.alarm.domain.alarmManagerRing

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.alarm.data.Constants
import com.example.alarm.ui.theme.AlarmTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyAlarmDialogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlarmTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val extras = intent.extras
                    extras?.let {
                        val ringtoneUriString = extras.getString(Constants.IntentAlarm)
                        val alarmId = extras.getString(Constants.IntentAlarmId)

                        if (ringtoneUriString != null && alarmId != null){
                            checkOverlayPermission()
                            startService(ringtoneUriString,alarmId)
                        }
                    }
                }
            }
        }
    }



    private fun startService(ringtoneUriString:String,alarmId:String){
        // check if the user has already granted
        // the Draw over other apps permission
        if (Settings.canDrawOverlays(this)) {
            // start the service based on the android version
            val intentForService = Intent(this,ForegroundService::class.java)
            intentForService.putExtra(Constants.IntentAlarm,ringtoneUriString)
            intentForService.putExtra(Constants.IntentAlarmId,alarmId)
            startForegroundService(intentForService)
        }
    }

    private fun checkOverlayPermission(){
        if (!Settings.canDrawOverlays(this)) {
            // send user to the device settings
            val myIntent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            startActivity(myIntent)
        }
    }

//    override fun onResume() {
//        super.onResume()
//        startService()
//    }
}
