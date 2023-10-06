package com.example.alarm.presintation.screensPreview.updateScreen

import android.content.Context
import android.net.Uri
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UpdateScreenViewModel  @Inject constructor(
    private val repo: AlarmRepository,
    private val alarmManager: AlarmManager
)  : ViewModel() {
    private var _state by mutableStateOf(UpdateScreenState())
    private var id: Int? = null


    val state: State<UpdateScreenState>
        get() = derivedStateOf { _state }


    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog.asStateFlow()


    fun setAlarmNeededToUpdated(alarmId: Int) {
        id = alarmId
        viewModelScope.launch(Dispatchers.IO) {
            val alarm = repo.getAlarm(alarmId)
            if (alarm != null){
                _state = _state.copy(
                    name = alarm.name,
                    hour = alarm.hour,
                    minute = alarm.minute,
                    ringtone = alarm.ringtone,
                    isRepeated = alarm.isRepeated,
                )
            }
        }
    }


    fun onCloseIconClick(navController: NavHostController) {
        navController.popBackStack()
    }

    fun onCompleteIconClick(
        context: Context,
        navController: NavHostController,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            id?.let {
                val alarm = repo.getAlarm(it)
                // update alarm with state if user change it
                alarm.name = _state.name
                alarm.hour = _state.hour
                alarm.minute = _state.minute
                alarm.ringtone = _state.ringtone
                alarm.isRepeated = _state.isRepeated
                // delete this alarm from alarm manager if it isOn
                if (alarm.isOn) {
                    alarmManager.removeAlarm(context,alarm)
                }

                // add alarm to alarm manager with new update
                alarmManager.addNewAlarm(context, alarm)

                // make alarm On
                alarm.isOn = true

                // finally add alarm to database
                viewModelScope.launch(Dispatchers.IO) {
                    repo.addAlarm(alarm)

                    // back to alarms screen
                    withContext(Dispatchers.Main) {
                        navController.popBackStack()
                    }
                }

            }

        }
    }

        fun onTimeSelected(hour: Int, minute: Int) {
            _state = _state.copy(
                hour = hour,
                minute = minute
            )
        }

        fun onAlarmNameChange(newName: String) {
            _state = _state.copy(
                name = newName
            )
        }

        fun onRingtoneSelected(ringtone: Uri) {
            _state = _state.copy(
                ringtone = ringtone
            )
        }

        fun onRepeatSwitch() {
            _state = _state.copy(
                isRepeated = _state.isRepeated.not()
            )
        }


        fun onShowDialogDelete() {
            _showDialog.value = true
        }

        fun onDismissDialogDelete() {
            _showDialog.value = false
        }

        fun onAlarmDelete(context: Context, navController: NavHostController) {

            viewModelScope.launch(Dispatchers.IO) {
                id?.let {
                    val alarm = repo.getAlarm(it)
                    repo.removeAlarmById(it)
                    withContext(Dispatchers.Main) {
                        alarmManager.removeAlarm(context, alarm)
                        navController.popBackStack()
                    }
                }
            }
        }



}


