package com.example.alarm.presintation.navigation


import com.example.alarm.R

sealed class NavigationScreen(val title:String,val icon:Int,val route:String){

    // bottom navigation screens
    object Clock:NavigationScreen(
        title = "Clock",
        icon =   R.drawable.baseline_access_time_24,
        route = "clock"
    )
    object Alarm:NavigationScreen(
        title = "Alarm",
        icon =   R.drawable.baseline_alarm_24,
        route = "alarm"
    )
}
