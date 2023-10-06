package com.example.alarm.presintation.screensPreview.alarmScreen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import com.example.alarm.data.local.Alarm
import com.example.alarm.presintation.components.alarmScreenComponents.AlarmCardItem
import com.example.alarm.presintation.navigation.Screens

@Composable
fun AlarmScreen(
    vm: AlarmScreenViewModel,
    appNavController: NavHostController,
    addAlarm: (Alarm) -> Unit,
    removeAlarm: (Alarm) -> Unit
) {
    val alarms by vm.alarms.collectAsState()
    val isChange by vm.isChange.collectAsState()
    if (isChange || isChange.not()){
        LazyColumn{
            items(alarms){
                AlarmCardItem(it,{alarmId -> appNavController.navigate("${Screens.UpdateAlarm.route}/$alarmId")}){ alarm ->
                    vm.onToggleClick(alarm,addAlarm,removeAlarm)
                }
            }
        }
    }

}