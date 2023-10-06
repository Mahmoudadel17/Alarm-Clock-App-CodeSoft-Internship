package com.example.alarm.presintation.components.alarmScreenComponents


import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FiberManualRecord
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.alarm.data.local.Alarm
import com.example.alarm.ui.theme.textInOffMode



@Composable
fun AlarmCardItem(alarm: Alarm,onCardClick:(Int)->Unit, onToggleClick: (Alarm) -> Unit) {
    val textColor = if (alarm.isOn) MaterialTheme.colorScheme.tertiary else textInOffMode
    var newHour = if(alarm.hour > 12) alarm.hour - 12 else alarm.hour
    if (newHour == 0){
        newHour = 12
    }
    var newMinute = alarm.minute.toString()
    if(newMinute.length ==1){
        newMinute = "0${alarm.minute}"
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier =  Modifier.padding(vertical = 10.dp, horizontal = 15.dp).clickable {
            // go to update screen
            onCardClick(alarm.id)
        }
    )
    {
        Column {
            Row (  verticalAlignment = Alignment.CenterVertically,){
                Text(
                    text = "${newHour}:${newMinute}",
                    color = textColor,
                    style = MaterialTheme.typography.headlineLarge
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = if(alarm.hour > 12)"PM" else "AM", color = textColor, style = MaterialTheme.typography.bodyLarge)
            }
            if (alarm.name!=""){
                Text(
                    text = alarm.name,
                    color = textColor.copy(
                        alpha = 0.6f
                    ),
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        AlarmSwitcher(isOn = alarm.isOn,size = 30.dp, padding = 5.dp) {
            onToggleClick(alarm)
        }
    }
}


@Composable
fun AlarmSwitcher(
    isOn:Boolean,
    size: Dp = 150.dp,
    iconSize: Dp = size / 3,
    padding: Dp = 10.dp,
    borderWidth: Dp = 1.dp,
    parentShape: Shape = CircleShape,
    toggleShape: Shape = CircleShape,
    animationSpec: AnimationSpec<Dp> = tween(durationMillis = 300),
    onClick: () -> Unit
) {

    val offset by animateDpAsState(
        targetValue = if (isOn) 0.dp else size,
        animationSpec = animationSpec, label = ""
    )
    val backgroundColor = if (isOn)MaterialTheme.colorScheme.secondary else textInOffMode
    Box(modifier = Modifier
        .width(size * 2)
        .height(size)
        .clip(shape = parentShape)
        .clickable { onClick() }
        .background(backgroundColor)
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .offset(x = offset)
                .padding(all = padding)
                .clip(shape = toggleShape)
                .background(Color.White)
        ) {}
        Row(
            modifier = Modifier
                .border(
                    border = BorderStroke(
                        width = borderWidth,
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    shape = parentShape
                )
        ) {
            Box(
                modifier = Modifier.size(size),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(iconSize),
                    imageVector = Icons.Default.FiberManualRecord,
                    contentDescription = "Theme Icon",
                    tint = if (isOn) Color.White
                    else textInOffMode
                )
            }
            Box(
                modifier = Modifier.size(size),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(iconSize),
                    imageVector = Icons.Default.FiberManualRecord,
                    contentDescription = "Theme Icon",
                    tint = if (isOn) MaterialTheme.colorScheme.secondary
                    else textInOffMode
                )
            }

        }
    }
}

