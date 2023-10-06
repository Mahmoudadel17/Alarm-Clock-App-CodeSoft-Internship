package com.example.alarm.presintation.screensPreview


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.alarm.presintation.components.clockScreenComponents.AnalogClock
import com.example.alarm.presintation.components.clockScreenComponents.DigitalClock
import kotlinx.coroutines.delay
import java.util.Calendar

@Composable
fun ClockScreen() {

    var hour by remember { mutableStateOf("0") }
    var minute by remember { mutableStateOf("0") }
    var second by remember { mutableStateOf("0") }
    var amOrPm by remember { mutableStateOf("0") }

    LaunchedEffect(Unit) {
        while (true) {
            val cal = Calendar.getInstance()
            hour = cal.get(Calendar.HOUR).run {
                if (this.toString().length == 1) "0$this" else "$this"
            }
            minute = cal.get(Calendar.MINUTE).run {
                if (this.toString().length == 1) "0$this" else "$this"
            }
            second = cal.get(Calendar.SECOND).run {
                if (this.toString().length == 1) "0$this" else "$this"
            }
            amOrPm = cal.get(Calendar.AM_PM).run {
                if (this == Calendar.AM) "AM" else "PM"
            }

            delay(1000)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxHeight(fraction = 0.8f),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    AnalogClock(
                        hour = hour.toInt(),
                        minute = minute.toInt(),
                        second = second.toInt()
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    DigitalClock(
                        hour = hour,
                        minute = minute,
                        amOrPm = amOrPm,
                    )
                }
            }
        }
    }
}