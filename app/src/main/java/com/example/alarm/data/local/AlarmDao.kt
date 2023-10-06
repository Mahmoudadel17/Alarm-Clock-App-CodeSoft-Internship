package com.example.alarm.data.local


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao {
    @Query("SELECT * FROM Alarms")
    fun getAllAlarms(): Flow<List<Alarm>>

    @Query("SELECT * FROM Alarms WHERE id= :id")
    fun getAlarm(id:Int) : Alarm
    @Query("SELECT * FROM Alarms WHERE idManager= :idManager")
    fun getAlarmByIdManager(idManager:Int) : Alarm

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAlarm(alarm: Alarm)

    @Delete
    fun deleteAlarm(alarm: Alarm)

    @Query("Delete From Alarms WHERE id= :id")
    fun deleteAlarmById(id:Int)

}