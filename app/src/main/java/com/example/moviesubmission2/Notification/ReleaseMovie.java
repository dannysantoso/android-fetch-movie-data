package com.example.moviesubmission2.Notification;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.moviesubmission2.Home.MainActivity;
import com.example.moviesubmission2.R;
import com.example.moviesubmission2.Search.MovieSearch;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class ReleaseMovie extends BroadcastReceiver {
    private final static String GROUP_KEY_EMAILS = "group_movie_keys";
    private final static int NOTIFICATION_REQUEST_CODE = 104;
    private final static int MAX_NOTIFICATION = 24;
    public static CharSequence CHANNEL_NAME = "Film Submission";


    private List<NotificationItem> NotificationItems = new ArrayList<>();
    ArrayList<MovieSearch> listMovie;
    private int movieId;

    @Override
    public void onReceive(Context context, Intent intent) {
        getMovieData(context);

    }

    private void getMovieData(final Context context) {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDate = dateFormat.format(date);


        listMovie = new ArrayList<>();

        String apiKey = "127c768e99e7a9f8faa335e999d8a920";
        String url = "https://api.themoviedb.org/3/discover/movie?api_key="+apiKey+"&primary_release_date.gte="+currentDate+"&primary_release_date.lte="+currentDate;


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    //parsing json
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject object = list.getJSONObject(i);
                        MovieSearch searchItems = new MovieSearch();
                        searchItems.setmTitle(object.getString("title"));
                        searchItems.setmDesc(object.getString("overview"));
                        searchItems.setmDate(object.getString("release_date"));
                        searchItems.setmImage(object.getString("poster_path"));
                        searchItems.setmRate(object.getString("vote_average"));
                        listMovie.add(searchItems);
                    }

                    for (int i = 0; i < listMovie.size(); i++) {
                        NotificationItems.add(new NotificationItem(i,listMovie.get(i).getmTitle(), listMovie.get(i).getmDesc()));
                        movieId++;
                    }

                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());

                }
                                showAlarmNotification(context);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }

    private void showAlarmNotification(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_movie);

        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder;
        for (movieId = 1; movieId < listMovie.size(); movieId++) {
            String CHANNLE_ID = "release_movie_chanel";
            if (movieId < MAX_NOTIFICATION) {
                builder = new NotificationCompat.Builder(context, CHANNLE_ID)
                        .setContentTitle(NotificationItems.get(movieId).getSender())
                        .setContentText(NotificationItems.get(movieId).getMessage())
                        .setSmallIcon(R.drawable.ic_movie)
                        .setLargeIcon(largeIcon)
                        .setGroup(GROUP_KEY_EMAILS)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);
            } else {
                int jum = listMovie.size() - 2;
                NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle()
                        .addLine(NotificationItems.get(movieId).getSender() + ":" + NotificationItems.get(movieId).getMessage())
                        .addLine(NotificationItems.get(movieId - 1).getSender() + ":" + NotificationItems.get(movieId).getMessage())
                        .setBigContentTitle(movieId + 1 + context.getString(R.string.release_movie) + "")
                        .setSummaryText("+" + jum + "More");

                builder = new NotificationCompat.Builder(context, CHANNLE_ID)
                        .setContentTitle(NotificationItems.get(movieId).getSender() + "- Release" + setFormatDateNow())
                        .setContentText(NotificationItems.get(movieId).getMessage())
                        .setSmallIcon(R.drawable.ic_movie)
                        .setGroup(GROUP_KEY_EMAILS)
                        .setGroupSummary(true)
                        .setContentIntent(pendingIntent)
                        .setStyle(inboxStyle)
                        .setAutoCancel(true);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(CHANNLE_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
                builder.setChannelId(CHANNLE_ID);
                if (notificationManager != null) {
                    notificationManager.createNotificationChannel(channel);
                }
            }
            Notification notification = builder.build();
            if (notificationManager != null) {
                notificationManager.notify(movieId, notification);
            }
        }


    }

    public void setRepeatingReminder(Context context) {
        cancelNotification(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReleaseMovie.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 102, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        assert alarmManager != null;
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        Toast.makeText(context, "Release Movie Reminder On", Toast.LENGTH_SHORT).show();

    }

    public void cancelNotification(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReleaseMovie.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 102, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        assert alarmManager != null;
        alarmManager.cancel(pendingIntent);

    }

    private String setFormatDateNow() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return df.format(new Date());
    }

}

