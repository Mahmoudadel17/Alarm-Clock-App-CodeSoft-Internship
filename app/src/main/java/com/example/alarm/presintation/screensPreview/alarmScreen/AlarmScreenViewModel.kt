package com.example.alarm.presintation.screensPreview.alarmScreen



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alarm.data.local.Alarm
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
    (private val repo: AlarmRepository ) : ViewModel() {

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


    fun onToggleClick(alarm: Alarm, addAlarm: (Alarm) -> Unit, removeAlarm: (Alarm) -> Unit){
        alarm.isOn = alarm.isOn.not()
        //remove or make alarm from AlarmManager
        if (alarm.isOn){
            addAlarm(alarm)
        }else{
            removeAlarm(alarm)
        }
        // update alarm in database
        viewModelScope.launch(Dispatchers.IO){
            repo.addAlarm(alarm)
            getAllAlarms()

        }

    }

}

