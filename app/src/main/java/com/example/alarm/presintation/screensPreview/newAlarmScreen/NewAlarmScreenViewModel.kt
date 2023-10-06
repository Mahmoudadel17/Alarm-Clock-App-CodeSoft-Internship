package com.example.alarm.presintation.screensPreview.newAlarmScreen


import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.alarm.data.local.Alarm
import com.example.alarm.domain.alarmManagerRing.AlarmManager
import com.example.alarm.domain.repositorys.AlarmRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NewAlarmScreenViewModel @Inject constructor(
    private val repo:AlarmRepository,
    private val alarmManager: AlarmManager
)  :ViewModel() {
    private var _state by mutableStateOf(NewAlarmScreenState())


    val state:State<NewAlarmScreenState>
        get() =  derivedStateOf{_state}


    fun resetState(){
        _state = NewAlarmScreenState()
    }


    fun onCloseIconClick(navController: NavHostController){
        navController.popBackStack()
    }

    fun onCompleteIconClick(context: Context,navController: NavHostController){
        // add alarm to alarmManager and database
        val alarm = Alarm(
            name =  _state.name,
            hour = _state.hour,
            minute = _state.minute,
            ringtone = _state.ringtone,
        )

        viewModelScope.launch (Dispatchers.IO){
            repo.addAlarm(alarm)
            val databaseAlarm = repo.getAlarmByIdManager(5)

            withContext(Dispatchers.Main){
                alarmManager.addNewAlarm(context, databaseAlarm)
                Log.d("AddAlarmToAlarmManager","<<<<<<<<<< ${databaseAlarm.id} >>>>>>>")
                navController.popBackStack()
            }
        }
    }





    fun onTimeSelected(hour:Int,minute:Int){
        _state =  _state.copy(
            hour = hour,
            minute = minute
        )
    }

    fun onAlarmNameChange(newName:String){
        _state =  _state.copy(
            name = newName
        )
    }

    fun onRingtoneSelected(ringtone:Uri){
        _state =  _state.copy(
            ringtone = ringtone
        )
    }

    fun onRepeatSwitch(){
        _state =  _state.copy(
            isRepeated = _state.isRepeated.not()
        )
    }


}