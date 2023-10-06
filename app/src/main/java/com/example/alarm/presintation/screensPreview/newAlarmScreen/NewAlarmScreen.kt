package com.example.alarm.presintation.screensPreview.newAlarmScreen


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.alarm.data.local.Alarm
import com.example.alarm.presintation.components.AlarmName
import com.example.alarm.presintation.components.RepeatedSwitcher
import com.example.alarm.presintation.components.RingtoneSelection
import com.example.alarm.presintation.components.TimeShowSelected
import com.example.alarm.presintation.components.TitleNewOrUpdate

@Composable
fun NewAlarmScreen(
    vm: NewAlarmScreenViewModel,
    navController: NavHostController,
) {
    LaunchedEffect(Unit ){
        vm.resetState()
    }
    val state = vm.state.value
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .padding(horizontal = 15.dp)
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    )
    {
        Column (horizontalAlignment = Alignment.CenterHorizontally){
            TitleNewOrUpdate("New",{vm.onCloseIconClick(navController)},{vm.onCompleteIconClick(context,navController)})
            TimeShowSelected(context,state.hour,state.minute){hour,minute -> vm.onTimeSelected(hour, minute)}
            AlarmName(state.name){
                vm.onAlarmNameChange(it)
            }
            RingtoneSelection(context){
                vm.onRingtoneSelected(it)
            }
            RepeatedSwitcher(isRepeated = state.isRepeated ) {
                vm.onRepeatSwitch()

            }
        }
    }

}
