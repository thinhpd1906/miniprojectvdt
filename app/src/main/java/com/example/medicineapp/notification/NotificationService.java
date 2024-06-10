package com.example.medicineapp.notification;

import android.Manifest;
import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.medicineapp.R;

public class NotificationService extends Service {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("description");
        int id = intent.getIntExtra("id", 10);
        String action = intent.getStringExtra("action");
        // Create a notification for the foreground service
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "urgent")
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("Foreground Service")
                .setContentText("Running...")
                .setSound(alarmSound)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        Notification foregroundNotification = builder.build();

        // Start the service in the foreground
        startForeground(1, foregroundNotification);

        // Create and display the actual notification

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.v("permission noti", "denied");
        } else {
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "urgent")
                    .setSmallIcon(R.drawable.notification_icon)
                    .setContentTitle(title)
                    .setContentText(description)
                    .setSound(alarmSound)
                    .setPriority(NotificationCompat.PRIORITY_HIGH);
            notificationManager.notify(id, notificationBuilder.build());

        }

        // Stop the service after displaying the notification
        stopSelf();
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
