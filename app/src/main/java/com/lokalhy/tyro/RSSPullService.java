package com.lokalhy.tyro;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.lokalhy.tyro.model.RssModel;
import com.lokalhy.tyro.retrofit.PodCastApiClient;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RSSPullService extends Service {


    @Override
    public void onCreate() {
        super.onCreate();
    }

    private void showNotification(String authorTitle, String podCastTitle, Intent intent) {
        PendingIntent pendingIntent= PendingIntent.getActivity(this, 0, intent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "notify")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Hey PodCast Listener")
                .setContentText("A new podcast of " + authorTitle +" is released with" + podCastTitle)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(200, builder.build());
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        String url = intent.getDataString();

        Log.d("polling rss:","Started");

        PodCastApiClient.getmInstance().getApi().getData(url).enqueue(new Callback<RssModel>() {
            @Override
            public void onResponse(@NotNull Call<RssModel> call, @NotNull Response<RssModel> response) {
                if(response.isSuccessful()) {
                    String pubDate = response.body().channel.getPubDate();
                    SharedPreferences prefs = getSharedPreferences("pubDatePoll", MODE_PRIVATE);
                    String lastPubDate = prefs.getString("pubDate", "");

                    if(lastPubDate.equals("")) {
                        SharedPreferences.Editor editor = getSharedPreferences("pubDatePoll", MODE_PRIVATE).edit();
                        editor.putString("pubDate", pubDate);
                        editor.apply();
                        stopSelf();
                    } else if(lastPubDate.contentEquals(pubDate)) {
                        Log.d("Rss Pull Service","No Updation");
                        stopSelf();
                    } else  {
                        SharedPreferences.Editor editor = getSharedPreferences("pubDatePoll", MODE_PRIVATE).edit();
                        editor.putString("pubDate", pubDate);
                        editor.apply();
                        showNotification(response.body().channel.getTitle(),
                                response.body().channel.getItem().get(0).getTitle(),intent);
                        stopSelf();
                    }
                }

            }

            @Override
            public void onFailure(@NotNull Call<RssModel> call, @NotNull Throwable t) {
                Log.d("Failure Intent Service",t.getMessage());
            }
        });
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
