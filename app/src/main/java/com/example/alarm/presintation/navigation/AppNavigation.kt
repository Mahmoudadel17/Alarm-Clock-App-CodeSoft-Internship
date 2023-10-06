package com.example.alarm.presintation.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.alarm.data.local.Alarm
import com.example.alarm.presintation.HomeScreen
import com.example.alarm.presintation.screensPreview.ClockScreen
import com.example.alarm.presintation.screensPreview.alarmScreen.AlarmScreen
import com.example.alarm.presintation.screensPreview.alarmScreen.AlarmScreenViewModel
import com.example.alarm.presintation.screensPreview.newAlarmScreen.NewAlarmScreen
import com.example.alarm.presintation.screensPreview.newAlarmScreen.NewAlarmScreenViewModel
import com.example.alarm.presintation.screensPreview.updateScreen.UpdateAlarmScreen
import com.example.alarm.presintation.screensPreview.updateScreen.UpdateScreenViewModel


@Composable
fun AppNavigate(
    darkTheme: Boolean,
    alarmScreenViewModel: AlarmScreenViewModel,
    newAlarmScreenViewModel: NewAlarmScreenViewModel,
    updateScreenViewModel: UpdateScreenViewModel,
    onThemeSwitchChange: () -> Unit
) {
    val navController  = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.Home.route){
        composable(route = Screens.Home.route,){
            HomeScreen(
                alarmScreenViewModel,navController,darkTheme, onThemeSwitchChange)
        }
        composable(route = Screens.NewAlarm.route){
            NewAlarmScreen(newAlarmScreenViewModel,navController)
        }
        composable(route = "${Screens.UpdateAlarm.route}/{alarmId}",arguments = listOf(
            navArgument("alarmId"){type = NavType.IntType}
        )){
            val id = it.arguments?.getInt("alarmId")
            id?.let {
                UpdateAlarmScreen(
                    updateScreenViewModel, navController, id)
            }
        }
    }
}


@Composable
fun BottomNavigation(
    alarmScreenViewModel: AlarmScreenViewModel,
    bottomNavController: NavHostController,
    appNavController: NavHostController,
) {
    NavHost(navController = bottomNavController, startDestination = NavigationScreen.Clock.route ){
        composable(route = NavigationScreen.Clock.route){
            ClockScreen()
        }
        composable(route = NavigationScreen.Alarm.route){
            AlarmScreen(alarmScreenViewModel,appNavController)
        }
    }

}