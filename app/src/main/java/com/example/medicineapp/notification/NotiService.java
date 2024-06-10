package com.example.medicineapp.notification;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.medicineapp.MainActivity;
import com.example.medicineapp.R;
import com.example.medicineapp.RoomDB.noti.NotiEntity;
import com.example.medicineapp.RoomDB.noti.NotiRepository;
import com.example.medicineapp.utils.Time;

import java.util.List;
import java.util.Objects;

public class NotiService extends Service {
    private static final String CHANNEL_ID = "NotiServiceChannel";
    NotiRepository notiRepo;
    @Override
    public void onCreate() {
        super.onCreate();
        notiRepo = new NotiRepository(this);
        createNotificationChannel();
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "low")
                .setContentTitle("Medicine Alarm")
                .setContentText("")
                .setSmallIcon(R.drawable.baseline_calendar_month_24)
                .setPriority(NotificationCompat.PRIORITY_MIN);
        startForeground(1, notificationBuilder.build());
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String action = intent.getStringExtra("action");
            if(Objects.equals(action, "create") || Objects.equals(action, "update")) {
                String title = intent.getStringExtra("title");
                String description = intent.getStringExtra("description");
                String repeat = intent.getStringExtra("repeat");
                String priority = intent.getStringExtra("priority");
                int taskid = intent.getIntExtra("id", 0);
                long time = intent.getLongExtra("time", 0);
                createcNotification(taskid, title, description, repeat, time, action, priority);
            }
            if(Objects.equals(action, "reboot")) {
                String now = Time.getCurrentTimeStamp();
                List<NotiEntity> notiList = notiRepo.getActiveNotis(now);
                for (NotiEntity noti : notiList) {
                    String title = noti.title;
                    String description = noti.description;
                    String repeat = noti.repeat;
                    String priority = noti.priority;
                    int taskid = noti.id;
                    long time = Time.getTimeMilSeconds(noti.time);
                    createcNotification(taskid, title, description, repeat, time, action, priority);
                }
            }
        }
        return START_STICKY;
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Noti Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(serviceChannel);
            }
        }
    }
    public void createcNotification(int taskid, String title, String description, String repeat, long time, String action, String priority) {
        Intent intent1 = new Intent(this, AlarmReceiver.class);
        intent1.putExtra("title", title);
        intent1.putExtra("description", description);
        intent1.putExtra("action", action);
        intent1.putExtra("id", taskid);
        intent1.putExtra("priority", priority);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, taskid, intent1, PendingIntent.FLAG_MUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        if (am != null) {
            switch(repeat) {
                case "":
                    am.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent);
                    break;
                case "1p":
                    am.setRepeating(AlarmManager.RTC_WAKEUP, time, 60*1000, pendingIntent);
                    break;
                case "1h":
                    am.setInexactRepeating(AlarmManager.RTC_WAKEUP, time, AlarmManager.INTERVAL_HOUR, pendingIntent);
                    break;
                case "1d":
                    am.setInexactRepeating(AlarmManager.RTC_WAKEUP, time, AlarmManager.INTERVAL_DAY, pendingIntent);
                    break;
                case "7d":
                    am.setInexactRepeating(AlarmManager.RTC_WAKEUP, time, 7*AlarmManager.INTERVAL_DAY, pendingIntent);
                    break;
                default:
                    am.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent);
            }
        }
    }
}
