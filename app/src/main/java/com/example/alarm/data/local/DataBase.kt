package com.example.alarm.data.local


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Alarm::class], version = 1, exportSchema = false)
@TypeConverters(UriConverter::class)
abstract class DataBase:RoomDatabase() {
    abstract fun getAlarmDao():AlarmDao
}