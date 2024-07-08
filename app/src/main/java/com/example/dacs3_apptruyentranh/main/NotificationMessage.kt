package com.example.dacs3_apptruyentranh.main

import android.app.Notification
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.dacs3_apptruyentranh.FbViewModel

@Composable
fun NotificationMessage(vm: FbViewModel){
    val notifState = vm.popupNotification.value
    val notifMessage = notifState?.getContentOrNull()
    if(notifMessage!=null){
        Toast.makeText(LocalContext.current, notifMessage, Toast.LENGTH_SHORT).show()
    }
}