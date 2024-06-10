package com.example.medicineapp;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.example.medicineapp.RoomDB.noti.NotiRepository;
import com.example.medicineapp.interfaces.IDeleteNoti;
import com.example.medicineapp.interfaces.IFragmentListener;
import com.example.medicineapp.interfaces.IGetIdNoti;
import com.example.medicineapp.interfaces.INoti;
import com.example.medicineapp.model.NotiModel;
import com.example.medicineapp.notification.AlarmReceiver;
import com.example.medicineapp.notification.Channel;
import com.example.medicineapp.notification.NotiService;
import com.example.medicineapp.utils.ChannelDefault;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.android.gms.tasks.OnCompleteListener;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback, INoti, IDeleteNoti, IFragmentListener
//        , BottomNavigationView
//        .OnNavigationItemSelectedListener
{
    BottomNavigationView bottomNavigationView;
    private NotificationManager notificationManager;
    private static final int PERMISSION_REQUEST_POST_NOTIFICATION = 10;
    private View homeView;
    private NavController controller;
    private NotiRepository notiRepository;

    @SuppressLint("InlinedApi")
    protected void onCreate(Bundle savedInstanceState) {
        Log.v("main activity create", "ok");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controller = Navigation.findNavController(this, R.id.nav_host_fragment);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        NavigationUI.setupWithNavController(bottomNavigationView, controller);
        homeView = findViewById(R.id.home_layout);
        notiRepository = new NotiRepository(this);
        if(android.os.Build.VERSION.SDK_INT > 32) {
            checkPermission();
        }
        notificationManager = getSystemService(NotificationManager.class);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                controller.popBackStack(item.getItemId(), false);
                Log.v("itemselected","" + item.getItemId());
                NavOptions navOptions = new NavOptions.Builder()
                        .setPopUpTo(item.getItemId(), true)
                        .build();
                controller.navigate(item.getItemId(),null, navOptions);
                return true;
            }
        });
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (controller.getCurrentDestination().getId() == R.id.taskHome) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Are you want to exit?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    controller.popBackStack();
                }
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @SuppressLint("InlinedApi")
    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                == PackageManager.PERMISSION_GRANTED) {
            // Permission is already available, start camera preview
//            Snackbar.make(homeView, R.string.post_notification_permission_available, Snackbar.LENGTH_SHORT).show();
            if(android.os.Build.VERSION.SDK_INT > 32) {
                createAllNotifiChannel();
                getTokenFirebaseMessage();
            }
        } else {
            // Permission is missing and must be requested.
            requestPostNotificationPermission();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // BEGIN_INCLUDE(onRequestPermissionsResult)
        if (requestCode == PERMISSION_REQUEST_POST_NOTIFICATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Snackbar.make(homeView, R.string.post_notification_permission_granted, Snackbar.LENGTH_SHORT).show();
                if(android.os.Build.VERSION.SDK_INT > 32) {
                    createAllNotifiChannel();
                    getTokenFirebaseMessage();
                }
            } else {
                // Permission request was denied.
                Snackbar.make(homeView, R.string.post_notification_permission_denied, Snackbar.LENGTH_SHORT).show();
            }
        }
        // END_INCLUDE(onRequestPermissionsResult)
    }

    @SuppressLint("InlinedApi")
    void requestPostNotificationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.POST_NOTIFICATIONS)) {
            Snackbar.make(homeView, R.string.post_notification_access_required,
                    Snackbar.LENGTH_INDEFINITE).setAction(R.string.ok, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Request the permission
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.POST_NOTIFICATIONS},
                            PERMISSION_REQUEST_POST_NOTIFICATION);
                }
            }).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, PERMISSION_REQUEST_POST_NOTIFICATION);
        }
    }

    @SuppressLint("InlinedApi")
    private void createAllNotifiChannel() {
        createNotifiChannel(ChannelDefault.urgentChannel);
        createNotifiChannel(ChannelDefault.importantChannel);
        createNotifiChannel(ChannelDefault.mediumChannel);
        createNotifiChannel(ChannelDefault.lowChannel);
    }

    @SuppressLint("InlinedApi")
    private void createNotifiChannel(Channel channel) {
        notificationManager = getSystemService(NotificationManager.class);
        if (notificationManager.getNotificationChannel(channel.getName()) == null) {
            NotificationChannel newchannel = new NotificationChannel(channel.getName(), channel.getName(), (int) channel.getImportant().getValue());
            newchannel.setDescription(channel.getDescription());
            notificationManager.createNotificationChannel(newchannel);
        }
    }

    private void creataNofiApiSmall(NotiModel noti, Integer id) {
          long interval;
            switch(noti.getRepeat()) {
                case "":
                    interval = 0;
                    break;
                case "1p":
                    interval = 60*1000;
                    break;
                case "1h":
                    interval = 60*60*1000;
                    break;
                case "1d":
                    interval = 24*60*60*1000;
                    break;
                case "7d":
                    interval = 7*24*60*60*1000;
                    break;
                default:
                    interval = 0;
            }
          if(id == 0) {
              notiRepository.insert(noti, new IGetIdNoti() {
                  @Override
                  public void getIdNoti(long id) {
                      Calendar calendar = Calendar.getInstance();
                      calendar.set(Calendar.YEAR, noti.getYear());
                      calendar.set(Calendar.MONTH, noti.getMonth());
                      calendar.set(Calendar.DAY_OF_MONTH, noti.getDay());
                      calendar.set(Calendar.HOUR_OF_DAY, noti.getHour());
                      calendar.set(Calendar.MINUTE, noti.getMinute());
                      calendar.set(Calendar.SECOND, 0);
                      Integer taskid = (int) id;
                      long time = calendar.getTimeInMillis();
                      Intent serviceIntent = new Intent(MainActivity.this, NotiService.class);
                      serviceIntent.putExtra("id", (int) taskid);
                      serviceIntent.putExtra("title", noti.getTitle());
                      serviceIntent.putExtra("description", noti.getDescription());
                      serviceIntent.putExtra("action", "create");
                      serviceIntent.putExtra("time", calendar.getTimeInMillis());
                      serviceIntent.putExtra("interval", interval);
                      serviceIntent.putExtra("repeat", noti.getRepeat());
                      serviceIntent.putExtra("priority", noti.getPriority());
                      Context context = getApplicationContext();

                      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                          context.startForegroundService(serviceIntent);
                      } else {
                          startService(serviceIntent);
                      }
                  }
              });
          } else {
              notiRepository.update(noti, id);
              Calendar calendar = Calendar.getInstance();
              calendar.set(Calendar.YEAR, noti.getYear());
              calendar.set(Calendar.MONTH, noti.getMonth());
              calendar.set(Calendar.DAY_OF_MONTH, noti.getDay());
              calendar.set(Calendar.HOUR_OF_DAY, noti.getHour());
              calendar.set(Calendar.MINUTE, noti.getMinute());
              calendar.set(Calendar.SECOND, 0);
              Integer taskid =  id;
              long time = calendar.getTimeInMillis();
              Intent serviceIntent = new Intent(MainActivity.this, NotiService.class);
              serviceIntent.putExtra("id", (int) taskid);
              serviceIntent.putExtra("title", noti.getTitle());
              serviceIntent.putExtra("description", noti.getDescription());
              serviceIntent.putExtra("action", "update");
              serviceIntent.putExtra("time", calendar.getTimeInMillis());
              serviceIntent.putExtra("interval", interval);
              serviceIntent.putExtra("repeat", noti.getRepeat());
              serviceIntent.putExtra("priority", noti.getPriority());
              Context context = getApplicationContext();
              if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                  context.startForegroundService(serviceIntent);
              } else {
                  startService(serviceIntent);
              }
          }
     }

    @Override
    public void onDataPass(NotiModel notiData, Integer id) {
        creataNofiApiSmall(notiData, id);
    }

    @Override
    public void onDeleteClick(Integer id) {
        Intent intent1 = new Intent(MainActivity.this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, id, intent1, PendingIntent.FLAG_MUTABLE | PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_NO_CREATE);
        if (pendingIntent != null) {
            pendingIntent.cancel();
            AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
            if (am != null) {
                am.cancel(pendingIntent);
            }
        }
    }

    @Override
    public void onFragmentChanged(String fragmentName) {
        Menu menu = bottomNavigationView.getMenu();
        int menuId;
        switch(fragmentName) {
            case "fragmentAddTask":
                menuId = R.id.taskHome;
                break;
            case "fragmentSearchHistory":
                menuId = R.id.drugHome;
                break;
            default:
                menuId = bottomNavigationView.getSelectedItemId();
        }
        for (int i = 0; i < menu.size(); i++) {
            MenuItem menuItem = menu.getItem(i);
            if (menuItem.getItemId() == menuId) {
                menuItem.setChecked(true);
                break;
            }
        }
    }

    public void getTokenFirebaseMessage() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        String TAG = "firebase cloud message token";
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
//                        String msg = getString(TAG, token);
                        Log.d(TAG, token);
                    }
                });
    }

}




