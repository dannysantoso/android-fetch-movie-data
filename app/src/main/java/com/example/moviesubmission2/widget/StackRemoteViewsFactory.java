package com.example.moviesubmission2.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.moviesubmission2.R;

import java.util.concurrent.ExecutionException;

import static com.example.moviesubmission2.DatabaseContract.CONTENT_URI;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;

    private Cursor cursor;

    public StackRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
    }

    @Override
    public void onCreate() {
        cursor = mContext.getContentResolver().query(
                CONTENT_URI,
                null,
                null,
                null,
                null
        );

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        WidgetItem item = getItem(position);
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);

        String url_image = "https://image.tmdb.org/t/p/w185" + item.getmImage();

        Bitmap bitmap = null;
        try {
            bitmap = Glide.with(mContext)
                    .asBitmap()
                    .load(url_image)
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        views.setImageViewBitmap(R.id.imageView, bitmap);

        Bundle extras = new Bundle();
        extras.putInt(FavoriteWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        views.setOnClickFillInIntent(R.id.imageView, fillInIntent);
        return views;
    }

    private WidgetItem getItem(int position) {
        if (!cursor.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid!");
        }

        return new WidgetItem(cursor);
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
