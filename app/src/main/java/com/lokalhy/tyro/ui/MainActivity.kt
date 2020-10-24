package com.lokalhy.tyro.ui

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.airbnb.mvrx.BaseMvRxActivity
import com.lokalhy.tyro.R
import com.lokalhy.tyro.RSSPullService


class MainActivity : BaseMvRxActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this, RSSPullService::class.java)
        intent.data = Uri.parse("http://joeroganexp.joerogan.libsynpro.com/rss")
        val pintent = PendingIntent.getService(this, 0, intent, 0)
        val calendar = java.util.Calendar.getInstance().time.time
        val alarm =
            getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarm.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
             calendar,
            60000,
            pintent
        )

        val prefs =
            getSharedPreferences("pubDatePoll", Context.MODE_PRIVATE)
        val lastPubDate = prefs.getString("pubDate", "")

        Log.d("lastPubDate:",lastPubDate)

    }


}