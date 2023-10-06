package com.example.alarm.presintation.screensPreview.alarmScreen



import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alarm.data.local.Alarm
import com.example.alarm.domain.alarmManagerRing.AlarmManager
import com.example.alarm.domain.repositorys.AlarmRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmScreenViewModel @Inject constructor
    (private val repo: AlarmRepository,private val alarmManager: AlarmManager) : ViewModel() {

    private  val _alarms: MutableStateFlow<List<Alarm>> = MutableStateFlow(emptyList())
    val alarms = _alarms.asStateFlow()

    private val _isChange = MutableStateFlow(false)
    val isChange: StateFlow<Boolean> = _isChange.asStateFlow()

    init {
        getAllAlarms()
    }

    private fun getAllAlarms(){
        _isChange.value = _isChange.value.not()
        viewModelScope.launch (Dispatchers.IO){
            repo.getAllAlarms().collect{
                _alarms.value = it.toMutableList()
            }
        }
    }


    fun onToggleClick(context:Context, alarm: Alarm){
        alarm.isOn = alarm.isOn.not()
        //remove or make alarm from AlarmManager
        if (alarm.isOn){
            alarmManager.addNewAlarm(context,alarm)
        }else{
            alarmManager.removeAlarm(context,alarm)
        }
        // update alarm in database
        viewModelScope.launch(Dispatchers.IO){
            repo.addAlarm(alarm)
            getAllAlarms()

        }

    }

}

