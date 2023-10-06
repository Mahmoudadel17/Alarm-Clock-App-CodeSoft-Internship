package com.example.alarm.domain.repositorys


import com.example.alarm.data.local.Alarm
import kotlinx.coroutines.flow.Flow

interface AlarmRepository {

    suspend fun getAllAlarms(): Flow<List<Alarm>>

    suspend fun getAlarm(alarmId: Int):Alarm
    suspend fun getAlarmByIdManager(idManager: Int):Alarm

    suspend fun addAlarm(alarm: Alarm)

    suspend fun removeAlarm(alarm: Alarm)

    suspend fun removeAlarmById(alarmId:Int)

}