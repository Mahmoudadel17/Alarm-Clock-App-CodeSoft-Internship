package com.example.alarm.presintation


import android.os.Handler
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.alarm.data.local.Alarm
import com.example.alarm.presintation.components.ThemeSwitcher
import com.example.alarm.presintation.components.clockScreenComponents.shadowCircular
import com.example.alarm.presintation.navigation.BottomNavigation
import com.example.alarm.presintation.navigation.NavigationScreen
import com.example.alarm.presintation.navigation.Screens
import com.example.alarm.presintation.screensPreview.alarmScreen.AlarmScreenViewModel


@Composable
fun HomeScreen(
    alarmScreenViewModel: AlarmScreenViewModel,
    navController: NavHostController,
    darkTheme: Boolean,
    onThemeSwitchChange: () -> Unit,
    addAlarm: (Alarm) -> Unit,
    removeAlarm: (Alarm) -> Unit
) {
    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        MainAppScreen(alarmScreenViewModel,navController,darkTheme,addAlarm,removeAlarm){
            onThemeSwitchChange()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppScreen(
    alarmScreenViewModel: AlarmScreenViewModel,
    appNavController: NavHostController,
    darkTheme: Boolean,
    addAlarm: (Alarm) -> Unit,
    removeAlarm: (Alarm) -> Unit,
    onThemeSwitchChange: () -> Unit
) {
    var doubleBackToExitPressedOnce = false
    val activity = LocalOnBackPressedDispatcherOwner.current as ComponentActivity
    val context = LocalContext.current

    val navController = rememberNavController()
    val screens = listOf(
        NavigationScreen.Clock,
        NavigationScreen.Alarm
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(

        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.largeTopAppBarColors(containerColor = MaterialTheme.colorScheme.background),
                title = {
                    var title = NavigationScreen.Clock.title
                    screens.forEach {screen ->
                        if (currentRoute == screen.route){
                            title = screen.title
                        }
                    }
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically){
                            Text(
                                text = title,
                                fontSize = 35.sp,
                                color = MaterialTheme.colorScheme.tertiary,
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            ThemeSwitcher(darkTheme = darkTheme,size = 30.dp, padding = 5.dp)
                            { onThemeSwitchChange() }
                            Spacer(modifier = Modifier.padding(end = 20.dp))
                        }
                        Divider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 5.dp, bottom = 5.dp, end = 18.dp),
                            color = MaterialTheme.colorScheme.surface
                        )
                    }


                },
            )
        },
        floatingActionButton = {
            if (currentRoute == NavigationScreen.Alarm.route){
                AnimatedVisibility(visible = true) {
                    FloatingActionButton(
                        modifier = Modifier
                            .shadowCircular(
                                blurRadius = 7.dp,
                                color = MaterialTheme.colorScheme.secondary
                            ),
                        containerColor = MaterialTheme.colorScheme.secondary,
                        shape = CircleShape,
                        onClick = {
                            // navigate to new alarm screen
                            appNavController.navigate(Screens.NewAlarm.route)
                        }) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            tint = MaterialTheme.colorScheme.background,
                            contentDescription = "Add",
                        )
                    }
                }
            }
        },
        bottomBar = {
            Column {
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp, horizontal = 15.dp),
                    color = MaterialTheme.colorScheme.surface
                )


                BottomAppBar(
                    containerColor = MaterialTheme.colorScheme.background
                ) {

                    screens.forEach{screen->
                        NavigationBarItem(
                            selected = false,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id)
                                    launchSingleTop = true
                                }
                            },
                            icon = {
                                Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)){
                                    Icon(
                                        painter = painterResource(id = screen.icon) ,
                                        contentDescription = "bottom nav icon",
                                        tint = if(screen.route == currentRoute) MaterialTheme.colorScheme.secondary
                                        else MaterialTheme.colorScheme.onBackground,

                                        )
                                }
                            },
                            label = {
                                Text(
                                    text = screen.title,
                                    color = if(screen.route == currentRoute) MaterialTheme.colorScheme.secondary
                                    else MaterialTheme.colorScheme.onBackground
                                )
                            }
                        )
                    }
                }

            }

        }

    ) {
        Box(modifier = Modifier.padding(it)){
            BottomNavigation(alarmScreenViewModel , navController,appNavController,addAlarm,removeAlarm)
        }
        //Back Handler
        BackHandler(onBack = {
            if (doubleBackToExitPressedOnce) {
                finishAffinity(activity)
            } else {
                doubleBackToExitPressedOnce = true
                Toast.makeText(context, "Press again to exit", Toast.LENGTH_SHORT).show()
                Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
            }
        })
    }
}


