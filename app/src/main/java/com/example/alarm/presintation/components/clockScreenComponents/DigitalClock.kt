package com.example.alarm.presintation.components.clockScreenComponents


import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun DigitalClock(
    hour: String,
    minute: String,
    amOrPm: String,
) {
    val currentDateTime = LocalDateTime.now()
    var newHour = if (hour.length==2 && hour[0] == '0') hour[1]else hour
    if(newHour=='0'){newHour = "12" }
    Text(
        text = "Cairo, Egypt", style = MaterialTheme.typography.displaySmall.merge(
            TextStyle(
                color = MaterialTheme.colorScheme.tertiary
            )
        )
    )
    Text(
        text = "$newHour:$minute $amOrPm", style = MaterialTheme.typography.headlineLarge,
        color = MaterialTheme.colorScheme.tertiary
    )

    Text(
        text = currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
        style = MaterialTheme.typography.bodyLarge.merge(
            TextStyle(
                color = MaterialTheme.colorScheme.tertiary.copy(
                    alpha = 0.6f
                )
            )
        )
    )
//    Text(text = .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
//        color = MaterialTheme.colorScheme.onSurface)
}