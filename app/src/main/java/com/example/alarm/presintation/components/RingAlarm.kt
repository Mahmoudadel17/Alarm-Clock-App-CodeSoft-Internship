package com.example.alarm.presintation.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.alarm.ui.theme.secondaryColorLight
import java.time.LocalTime

@Composable
fun AlarmConfirmationDialog(
    onOffClick: () -> Unit,
    onSnoozeClick: () -> Unit
) {

    var hour:Int = LocalTime.now().hour
    val minute:Int = LocalTime.now().minute
    var aMorPM = "AM"
    if (hour > 12){
        aMorPM = "PM"
        hour -= 12
    }
    AlertDialog(
        containerColor = MaterialTheme.colorScheme.background,
        onDismissRequest = {},
        title = { Text("Alarm Clock") },
        text = { Text("Alarm $hour:$minute $aMorPM Ring", color = MaterialTheme.colorScheme.tertiary) },
        confirmButton = {
            Button(
                onClick = {
                    onOffClick()
                },
                colors = ButtonDefaults.buttonColors(containerColor =MaterialTheme.colorScheme.secondary),
            ) {
                Text("Off", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.background)
            }
        },
        dismissButton = {
            Button(
                onClick = onSnoozeClick,
                colors = ButtonDefaults.buttonColors(containerColor =MaterialTheme.colorScheme.secondary),
            ) {
                Text("Snooze",color = MaterialTheme.colorScheme.background)
            }
        },
        icon = {
            Icon(
                imageVector = Icons.Default.Alarm,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    )
}