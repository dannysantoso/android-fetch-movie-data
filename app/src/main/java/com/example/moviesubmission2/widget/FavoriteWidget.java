package com.example.moviesubmission2.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.moviesubmission2.R;

/**
 * Implementation of App Widget functionality.
 */
public class FavoriteWidget extends AppWidgetProvider {

    private static final String TOAST_ACTION = "com.example.moviesubmission2.TOAST_ACTION";
    public static final String EXTRA_ITEM = "com.example.moviesubmission2.EXTRA_ITEM";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Intent i = new Intent(context, StackWidgetService.class);
        i.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        i.setData(Uri.parse(i.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.favorite_widget);
        remoteViews.setRemoteAdapter(R.id.stack_view, i);
        remoteViews.setEmptyView(R.id.stack_view, R.id.empty_view);

        Intent toastIntent = new Intent(context, FavoriteWidget.class);
        toastIntent.setAction(FavoriteWidget.TOAST_ACTION);
        toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        i.setData(Uri.parse(i.toUri(Intent.URI_INTENT_SCHEME)));
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, toastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setPendingIntentTemplate(R.id.stack_view, pi);

        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent i) {
        if (i.getAction().equals(TOAST_ACTION)) {
            int viewIndex = i.getIntExtra(EXTRA_ITEM, 0);
            Toast.makeText(context, "Choosed layer " + viewIndex, Toast.LENGTH_SHORT).show();
        }
        super.onReceive(context, i);
    }


}

