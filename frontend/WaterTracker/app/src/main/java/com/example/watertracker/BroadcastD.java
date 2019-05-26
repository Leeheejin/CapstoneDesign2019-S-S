package com.example.watertracker;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class BroadcastD extends BroadcastReceiver {
        String INTENT_ACTION = Intent.ACTION_BOOT_COMPLETED;




        @Override
        public void onReceive(Context context, Intent intent) {

            SharedPreferences prefs = context.getSharedPreferences("PrefName", Context.MODE_PRIVATE);

            NotificationManager notificationmanager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
            Notification.Builder builder = new Notification.Builder(context);
            builder.setSmallIcon(R.drawable.icon1).setTicker("HETT").setWhen(System.currentTimeMillis())
                    .setNumber(1).setContentTitle("물 마실 시간입니다").setContentText("마시는걸 추천드려요")
                    .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE).setContentIntent(pendingIntent).setAutoCancel(true);


            boolean isAlram = prefs.getBoolean("Alarm", true);

            if(isAlram) {
                notificationmanager.notify(1, builder.build());
            }

        }
    }
