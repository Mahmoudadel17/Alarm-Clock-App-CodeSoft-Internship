package com.example.alarm.di

import android.content.Context
import androidx.room.Room
import com.example.alarm.data.local.DataBase
import com.example.alarm.data.repositorys.AlarmRepositoryImpl
import com.example.alarm.domain.alarmManagerRing.AlarmManager
import com.example.alarm.domain.repositorys.AlarmRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context,DataBase::class.java,"AlarmsDatabase").build()

    @Provides
    @Singleton
    fun providesDatabaseDao(dataBase: DataBase)=dataBase.getAlarmDao()


    @Provides
    @Singleton
    fun providesAlarmScreenRepository(@ApplicationContext context: Context):AlarmRepository =
        AlarmRepositoryImpl(providesDatabaseDao(providesDatabase(context)))

    @Provides
    @Singleton
    fun providesAlarmManager():AlarmManager = AlarmManager()

}