package com.example.alarm.presintation.screensPreview.updateScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.alarm.presintation.components.AlarmName
import com.example.alarm.presintation.components.RepeatedSwitcher
import com.example.alarm.presintation.components.RingtoneSelection
import com.example.alarm.presintation.components.TimeShowSelected
import com.example.alarm.presintation.components.TitleNewOrUpdate

@Composable
fun UpdateAlarmScreen(
    vm: UpdateScreenViewModel,
    navController: NavHostController,
    alarmId:Int,
) {
    vm.setAlarmNeededToUpdated(alarmId)
    UpdateAlarmScreenPreview(vm,navController)
}

@Composable
fun UpdateAlarmScreenPreview(
    vm: UpdateScreenViewModel,
    navController: NavHostController,

) {
    val state = vm.state.value
    val showDialog by vm.showDialog.collectAsState()
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .padding(horizontal = 15.dp)
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    )
    {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            TitleNewOrUpdate("Update",{vm.onCloseIconClick(navController)},{vm.onCompleteIconClick(context,navController)})
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
            DeleteAlarmButton { vm.onShowDialogDelete() }
        }
        DeleteConfirmationDialog(showDialog,{vm.onAlarmDelete(context,navController)}){
            vm.onDismissDialogDelete()
        }
    }
}


@Composable
fun DeleteAlarmButton(onDeleteAlarm:()->Unit) {
    Button (colors = ButtonDefaults.buttonColors(containerColor =MaterialTheme.colorScheme.background),
        enabled = true,
        onClick = {onDeleteAlarm()},
        modifier = Modifier
            .padding(8.dp, top = 220.dp)
            .fillMaxWidth(1f)
            .border(1.dp, color = MaterialTheme.colorScheme.secondary, shape = RoundedCornerShape(16.dp)),
    ){
        Text(text = "Delete", fontSize = 30.sp, style = TextStyle(color = MaterialTheme.colorScheme.secondary))
    }
}

@Composable
fun DeleteConfirmationDialog(
    showDialog: Boolean,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            containerColor = MaterialTheme.colorScheme.background,
            onDismissRequest = onDismiss,
            title = { Text("Confirm Deletion", color =  MaterialTheme.colorScheme.tertiary)},
            text = { Text("Are you sure you want to delete this Alarm?", color = MaterialTheme.colorScheme.tertiary) },
            confirmButton = {
                Button(
                    onClick = {
                        onConfirm()
                        onDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor =MaterialTheme.colorScheme.secondary),
                ) {
                    Text("Delete", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.background)
                }
            },
            dismissButton = {
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(containerColor =MaterialTheme.colorScheme.secondary),
                ) {
                    Text("Cancel",color = MaterialTheme.colorScheme.background)
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    modifier = Modifier.size(48.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        )
    }
}

