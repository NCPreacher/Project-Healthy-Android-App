package com.example.projekt;

import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;
import android.os.SystemClock;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static com.example.projekt.App.CHANNEL_ID;
import static com.example.projekt.App.notificationManager;

public class IntentServiceNotifications extends IntentService
{
    private ArrayList<Integer> id = new ArrayList<>();
    private ArrayList<String> day = new ArrayList<>();
    private ArrayList<String> time = new ArrayList<>();
    private ArrayList<String> text = new ArrayList<>();
    private ArrayList<Integer> active = new ArrayList<>();
    private ArrayList<Integer> repeat = new ArrayList<>();

    public boolean isRunning = false;
    private PowerManager.WakeLock wakeLock;

    @Override
    public void onCreate()
    {
        super.onCreate();

        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "app:WakeLock");
        wakeLock.acquire();

        startForegroundNotification();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        isRunning = false;
        wakeLock.release();
    }

    public IntentServiceNotifications()
    {
        super("IntentServiceNotifications");
        setIntentRedelivery(true);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent)
    {
        if(isRunning == false) { isRunning = true; }

        getValues(intent);

        while(day.size() > 0)
        {
            for (int i = 0; i < day.size(); i++)
            {
                if (active.get(i) == 1)
                {
                    if (day.get(i).equals(Integer.toString(getCurrentDay())) == true)
                    {
                        if (time.get(i).equals(getCurrentTime()) == true)
                        {
                            createNotification(text.get(0));
                            if(repeat.get(i) == 0)
                            {
                                DataBaseHelper db = new DataBaseHelper(this);
                                db.setNotificationActive(Integer.toString(id.get(i)), false);
                            }
                        }
                    }
                }
            }
            SystemClock.sleep(60000);
        }
    }

    private void getValues(Intent intent)
    {
        id = intent.getIntegerArrayListExtra("id");
        day = intent.getStringArrayListExtra("day");
        time = intent.getStringArrayListExtra("time");
        text = intent.getStringArrayListExtra("text");
        active = intent.getIntegerArrayListExtra("active");
        repeat = intent.getIntegerArrayListExtra("repeat");
    }

    private void startForegroundNotification()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("Project_Healthy")
                    .setContentText("Powiadomienia")
                    .setSmallIcon(R.drawable.menu_background)
                    .build();

            startForeground(1, notification);
        }
    }

    private void createNotification(String Text)
    {
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Powiadomienie")
                .setContentText(Text)
                .setSmallIcon(R.drawable.menu_background)
                .build();

        notificationManager.notify(5, notification);
    }

    private String getCurrentTime()
    {
        DateFormat df = new SimpleDateFormat("HH:mm");
        String date = df.format(Calendar.getInstance().getTime());

        return date;
    }

    public int getCurrentDay()
    {
        Calendar rightNow = Calendar.getInstance();
        int day = rightNow.get(Calendar.DAY_OF_WEEK); // <1, 7>

        if(day == 1) { return 6; }
        else { return day - 2; }
    }
}
