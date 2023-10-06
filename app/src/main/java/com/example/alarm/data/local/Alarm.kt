package com.example.alarm.data.local


import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Alarms")
data class Alarm(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    var idManager:Int = 5,
    var name:String = "",
    var hour:Int,
    var minute:Int,
    var ringtone: Uri,
    var isOn:Boolean = true,
    var isRepeated:Boolean = false,

    )

