package com.example.alarm.domain.alarmManager


import android.Manifest
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.alarm.R
import com.example.alarm.data.Constants
import com.example.alarm.di.App
import android.content.res.Configuration
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
class BroadcastForAlarm : BroadcastReceiver() {
    private lateinit var app:App
    private val tag:String = "BroadcastForAlarm"


    @RequiresApi(Build.VERSION_CODES.P)
    override fun onReceive(context: Context?, intent: Intent?) {

        val name = intent?.getStringExtra(Constants.IntentAlarmName)
        val ringtoneUriString = intent?.getStringExtra(Constants.IntentAlarm)
        val alarmId = intent?.getStringExtra(Constants.IntentAlarmId)

        Log.d(tag,"onHandleIntent: 1111111111111111111111111 $alarmId" )


        val intentForService = Intent(context,MyAlarmDialogActivity::class.java)
        intentForService.putExtra(Constants.IntentAlarm,ringtoneUriString)
        intentForService.putExtra(Constants.IntentAlarmId,alarmId)
        // Starting foreground service on Android Oreo and above requires the startForegroundService method
//        context?.startForegroundService(intentForService)
        context?.startActivity(intentForService)

//        try {
//            // Parse the ringtone Uri string to a Uri
//            val ringtoneUri: Uri = Uri.parse(ringtoneUriString)
//            Log.d(tag,"onHandleIntent: 222222222222222222222222" )
//            // Now you have a Uri object representing the ringtone
//            // You can use this Uri with RingtoneManager to get a Ringtone instance if needed
//            app = context?.applicationContext as App
//            app.ringtone = RingtoneManager.getRingtone(context, ringtoneUri)
//
//        } catch (e: Exception) {
//            // Handle any parsing or other exceptions that may occur
//            Log.d(tag,"onHandleIntent: 3333333333333333333333333333" )
//        }
//
//        createNotification(context!!,alarmId!!)
    }

    // this notification with actions
    @RequiresApi(Build.VERSION_CODES.P)
    private fun createNotification(context: Context, alarmId:Int) {

        // Create two actions
        val action1Intent = Intent(context, OffAlarmReceiver::class.java)
        action1Intent.putExtra(Constants.IntentAlarmId,alarmId.toString())
        val action1PendingIntent = PendingIntent.getBroadcast(context, alarmId, action1Intent,
            PendingIntent.FLAG_IMMUTABLE)

        val action2Intent = Intent(context, SnoozeAlarmReceiver::class.java)
        val action2PendingIntent = PendingIntent.getBroadcast(context, alarmId, action2Intent,
            PendingIntent.FLAG_IMMUTABLE)

        // Start the ringtone
        app.ringtone?.play()
        app.ringtone?.isLooping = true


        val notification  = NotificationCompat.Builder(context,  Constants.channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("My Notification")
            .setContentText("This is a notification with two actions")
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_RECOMMENDATION)
            .addAction(R.drawable.baseline_snooze_24, "Snooze", action2PendingIntent)
            .addAction(R.drawable.baseline_alarm_off_24, "Off", action1PendingIntent)
            .build()


        val notificationManager = NotificationManagerCompat.from(context)

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) { return }
        notificationManager.notify(1, notification)
    }

}










