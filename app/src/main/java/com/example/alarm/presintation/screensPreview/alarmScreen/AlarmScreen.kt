package com.example.alarm.presintation.screensPreview.alarmScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.navigation.NavHostController
import com.example.alarm.data.local.Alarm
import com.example.alarm.presintation.components.alarmScreenComponents.AlarmCardItem
import com.example.alarm.presintation.navigation.Screens

@Composable
fun AlarmScreen(
    vm: AlarmScreenViewModel,
    appNavController: NavHostController,
) {
    val alarms by vm.alarms.collectAsState()
    val isChange by vm.isChange.collectAsState()
    val context = LocalContext.current
    if (isChange || isChange.not()){
        LazyColumn{
            items(alarms){
                AlarmCardItem(it,{alarmId -> appNavController.navigate("${Screens.UpdateAlarm.route}/$alarmId")}){ alarm ->
                    vm.onToggleClick(context,alarm)
                }
            }


        }
        if (alarms.isEmpty()){
            Column (
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                Text(
                    text ="No alarms yet.",
                    style = MaterialTheme.typography.headlineSmall.merge(
                        TextStyle(
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    ),
                )
            }
        }
    }

}