package com.example.alarm.domain.alarmManagerRing


import android.content.Context
import android.graphics.PixelFormat
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.compose.ui.platform.*
import com.example.alarm.domain.repositorys.AlarmRepository
import com.example.alarm.presintation.components.AlarmConfirmationDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Window(context: Context, ringtoneString: String, alarmId: String, repo: AlarmRepository) {
    private var overlayView: View
    private var overlayParams: WindowManager.LayoutParams
    private var windowManager: WindowManager
    private lateinit var ringtone:Ringtone
    private val tag:String = "Window"


    init {
        try {
            // Parse the ringtone Uri string to a Uri
            val ringtoneUri: Uri = Uri.parse(ringtoneString)

            // Now you have a Uri object representing the ringtone
            // You can use this Uri with RingtoneManager to get a Ringtone instance if needed
            ringtone = RingtoneManager.getRingtone(context, ringtoneUri)

        } catch (e: Exception) {
            // Handle any parsing or other exceptions that may occur
            Log.d(tag,"onHandleIntent: 3333333333333333333333333333" )
        }
        // Create a ComposeView to host the Composable content
        val composeView = ComposeView(context)
        composeView.setContent {
            AlarmConfirmationDialog({
                offAlarmAction(alarmId.toInt(),repo)
                close()
            },{
                snoozeAction()
                close()
            })
        }

        // Create the overlay view
        overlayView = composeView


        // Set the layout parameters for the overlay view
        overlayParams =
            WindowManager.LayoutParams(
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
            )

        overlayParams.gravity = Gravity.CENTER
        windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

    }

    fun open() {


        try {
            if (overlayView.windowToken == null) {
                if (overlayView.parent == null) {
                    windowManager.addView(overlayView, overlayParams)
                    // Start the ringtone
                    ringtone.play()
                    ringtone.isLooping = true
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun close() {
        try {
            windowManager.removeView(overlayView)

            // close the ringtone
            if (ringtone.isPlaying) {
                ringtone.stop()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun offAlarmAction(alarmId:Int,repo: AlarmRepository){
        CoroutineScope(Dispatchers.IO).launch {
            // get alarm from database
            val alarm = repo.getAlarm(alarmId)
            repo.removeAlarmById(alarmId)

            // if alarm not repeated make it off
            val isRepeating = alarm.isRepeated
            if (!isRepeating){
                alarm.isOn = alarm.isOn.not()
                // update alarm in database
                repo.addAlarm(alarm)
            }
        }
    }


    private fun snoozeAction(){

    }



}
