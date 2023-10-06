package com.example.alarm.presintation.components

import android.app.Activity
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alarm.presintation.components.alarmScreenComponents.AlarmSwitcher



@Composable
fun IconClick(icon: ImageVector, onIconClick:()->Unit) {
    Icon(
        imageVector = icon,
        tint = MaterialTheme.colorScheme.onBackground,
        contentDescription = "icon click",
        modifier = Modifier.clickable {
            onIconClick()
        }
    )
}

fun showTimePicker(
    hour:Int,
    minute:Int,
    context: Context,
    onTimeChange: (Int,Int) -> Unit
) {
    val timePicker = TimePickerDialog(
        /*context*/ context,
        /*listener*/ {_, hour, minute ->
            onTimeChange(hour,minute)
            Toast.makeText(context,  "$hour:$minute", Toast.LENGTH_SHORT).show()
        },
        /*hour*/ hour,
        /*minute*/ minute,
        /*is24Hour*/ false
    )
    timePicker.show()
}







@Composable
fun TitleNewOrUpdate(title:String,onCloseIconClick:()->Unit,onCompleteIconClick:()->Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ){
        IconClick(icon = Icons.Filled.Clear){
            onCloseIconClick()
        }
        Spacer(modifier = Modifier.weight(0.70f))
        Text(
            text = "$title Alarm",
            color = MaterialTheme.colorScheme.tertiary,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.weight(1f))
        IconClick(icon = Icons.Filled.Check){
            onCompleteIconClick()
        }
    }
}


@Composable
fun TimeShowSelected(context: Context,hour:Int,minute:Int,onTimeSelected:(Int,Int)->Unit) {
    var newHour = if(hour > 12) hour - 12 else hour
    if (newHour == 0){
        newHour = 12
    }
    var newMinute = minute.toString()
    if(newMinute.length ==1){
        newMinute = "0${minute}"
    }
    Column(
        modifier = Modifier.padding(top = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text ="${newHour}:${newMinute} AM",
            style = MaterialTheme.typography.displayLarge.merge(
                TextStyle(
                    color = MaterialTheme.colorScheme.tertiary
                )
            ),
            modifier = Modifier
                .padding(bottom = 30.dp)
                .clickable {
                    // show time picker
                    showTimePicker(
                        context = context,
                        hour = hour,
                        minute = minute
                    ) { newHour, newMinute ->
                        onTimeSelected(newHour, newMinute)
                    }
                }
        )
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmName(alarmName:String,onAlarmNameChange:(String)->Unit) {
    TextField(
        value = alarmName,
        enabled = true,
        label = { Text(text = "Alarm Name") },
        onValueChange ={onAlarmNameChange(it)},
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            textColor = MaterialTheme.colorScheme.tertiary,
            disabledTextColor = MaterialTheme.colorScheme.tertiary,
            focusedIndicatorColor= MaterialTheme.colorScheme.tertiary,
            unfocusedIndicatorColor= MaterialTheme.colorScheme.tertiary,
            focusedLabelColor= Color.Gray,
            unfocusedLabelColor= Color.Gray,
        ),
        modifier = Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth()
    )
}

@Composable
fun RingtoneSelection(context: Context, onRingtoneSelected:(Uri)->Unit) {
    var selectedRingtoneUri by remember { mutableStateOf<Uri?>(null) }
    val ringtonePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            selectedRingtoneUri = data?.getParcelableExtra<Uri>(RingtoneManager.EXTRA_RINGTONE_PICKED_URI)
            // Display the selected ringtone's name if available
            Log.d("ringtone","------------------- >>> $ <<< ------------------")

            selectedRingtoneUri?.let { uri ->
                onRingtoneSelected(uri)
            }
        }
    }

    Row(
        modifier = Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth()
            .clickable {
                // go to files to select ringtone for alarm
                val intent = Intent(RingtoneManager.ACTION_RINGTONE_PICKER)
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Ringtone")
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_RINGTONE)
                ringtonePickerLauncher.launch(intent)
            }
    ) {
        Text(text = "Ringtone",color = MaterialTheme.colorScheme.tertiary, fontSize = 20.sp)
        Spacer(modifier = Modifier.weight(1f))
        Icon(imageVector = Icons.Filled.KeyboardArrowRight, contentDescription = "ringtone select icon")
    }

}

@Composable
fun RepeatedSwitcher(isRepeated:Boolean,onToggleSwitch: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth(),
    ) {
        Text(text = "Repeat",color = MaterialTheme.colorScheme.tertiary, fontSize = 20.sp)
        Spacer(modifier = Modifier.weight(1f))
        AlarmSwitcher(isOn = isRepeated,size = 30.dp, padding = 5.dp) {
            onToggleSwitch()
        }
    }
}


