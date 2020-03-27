package com.example.moviesubmission2.Notification;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.moviesubmission2.R;

import java.util.Calendar;

import static com.example.moviesubmission2.SettingFragment.REMINDER;

public class DailyReminder extends BroadcastReceiver {
    private static final String REPEATING_REMINDER = "Repeating Alarm";
    private static final int ID_REPEAT_TIME = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        String message = "Hello, Let's Continue your journey in our application !!!";
        String title = "Movie Submission 5";
        showAlarmNotification(context, title, message);
    }

    private void showAlarmNotification(Context context, String title, String message) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, REPEATING_REMINDER)
                .setSmallIcon(R.drawable.ic_movie)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000})
                .setSound(alarmSound);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(REPEATING_REMINDER, REMINDER, NotificationManager.IMPORTANCE_DEFAULT);
            builder.setChannelId(REPEATING_REMINDER);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();
        if (notificationManager != null) {
            notificationManager.notify(DailyReminder.ID_REPEAT_TIME, notification);
        }
    }

    public void setRepeatingReminder(Context context) {
        cancelNotification(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DailyReminder.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_REPEAT_TIME, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 7);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        assert alarmManager != null;
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        Toast.makeText(context, "Reminder Setup", Toast.LENGTH_SHORT).show();
    }

    public void cancelNotification(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DailyReminder.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 102, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        assert alarmManager != null;
        alarmManager.cancel(pendingIntent);

    }
}
