package com.example.medicineapp.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.medicineapp.R;

import java.util.Objects;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("description");
        String priority = intent.getStringExtra("priority");
        int priorityNoti;
        String channelId;
        switch(priority) {
            case "High Important":
                priorityNoti = NotificationCompat.PRIORITY_HIGH;
                channelId = "urgent";
                break;
            case "Medium Important":
                priorityNoti = NotificationCompat.PRIORITY_DEFAULT;
                channelId = "important";
                break;
            case "Low Important":
                priorityNoti = NotificationCompat.PRIORITY_LOW;
                channelId = "medium";
                break;
            case "Not Important":
                priorityNoti = NotificationCompat.PRIORITY_MIN;
                channelId = "low";
                break;
            default:
                priorityNoti = NotificationCompat.PRIORITY_HIGH;
                channelId = "urgent";
        }
        int id = intent.getIntExtra("id", 10);
        String action =intent.getStringExtra("action");
        long when = System.currentTimeMillis();
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        if(action.equals("update")) {
//            Log.v("action", "update");
        }
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                    .setSmallIcon(R.drawable.notification_icon)
                    .setContentTitle(title)
                    .setContentText(description)
                    .setPriority(priorityNoti);;
            notificationManager.notify(id, builder.build());

//        Intent serviceIntent = new Intent(context, NotificationService.class);
//        serviceIntent.putExtras(Objects.requireNonNull(intent.getExtras()));
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            context.startForegroundService(serviceIntent);
//        }
    }
}
