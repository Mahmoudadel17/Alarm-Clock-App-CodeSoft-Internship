package com.example.alarm.data.repositorys


import com.example.alarm.data.local.Alarm
import com.example.alarm.data.local.AlarmDao
import com.example.alarm.domain.repositorys.AlarmRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AlarmRepositoryImpl @Inject constructor(private val dao:AlarmDao) : AlarmRepository {
    override suspend fun getAllAlarms(): Flow<List<Alarm>> {
        return dao.getAllAlarms()
    }

    override suspend fun getAlarm(alarmId: Int): Alarm {
        return dao.getAlarm(alarmId)
    }

    override suspend fun getAlarmByIdManager(idManager: Int): Alarm {
        val alarm = dao.getAlarmByIdManager(idManager)
        alarm.idManager = 0
        dao.addAlarm(alarm)
        return alarm
    }

    override suspend fun addAlarm(alarm: Alarm) {
        dao.addAlarm(alarm)
    }

    override suspend fun removeAlarm(alarm: Alarm) {
        dao.deleteAlarm(alarm)
    }

    override suspend fun removeAlarmById(alarmId: Int) {
        dao.deleteAlarmById(alarmId)
    }
}