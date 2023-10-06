package com.example.alarm.di

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.Ringtone
import com.example.alarm.data.Constants
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App :Application() {
    var ringtone: Ringtone? = null
    override fun onCreate() {
        super.onCreate()
        val channel =  NotificationChannel(Constants.channelId, Constants.channelName, NotificationManager.IMPORTANCE_DEFAULT)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

    }
}