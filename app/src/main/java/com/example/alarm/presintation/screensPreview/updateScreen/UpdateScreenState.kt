package com.example.alarm.presintation.screensPreview.updateScreen

import android.media.RingtoneManager
import android.net.Uri
import java.time.LocalTime

data class UpdateScreenState (
    val name:String = "",
    val hour:Int = LocalTime.now().hour,
    val minute:Int = LocalTime.now().minute,
    val ringtone: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM),
    var isOn:Boolean = true,
    var isRepeated:Boolean = false,
)