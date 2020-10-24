package com.lokalhy.tyro.base

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.lokalhy.tyro.DataSource
import com.lokalhy.tyro.retrofit.Api

class UserApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }
    var source = DataSource()

    val homeRepository = HomeRepository(source)

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "channel_name_tyro"
            val descriptionText = "this is notify channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("notify", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}