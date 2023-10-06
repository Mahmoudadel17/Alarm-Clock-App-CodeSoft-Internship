package com.example.alarm.presintation.navigation

sealed class Screens(val route:String){
    object Home:Screens(
        route = "home"
    )
    object NewAlarm:Screens(
        route = "newAlarm"
    )
    object UpdateAlarm:Screens(
        route = "newAlarm"
    )
}
