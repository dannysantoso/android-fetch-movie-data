package com.example.moviesubmission2;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.moviesubmission2.Database.FavMovieHelper;

import static com.example.moviesubmission2.DatabaseContract.AUTHORITY;
import static com.example.moviesubmission2.DatabaseContract.CONTENT_URI;

public class MyContentProvider extends ContentProvider {

    private static final int FAVORITE = 1;

    private static final int FAVORITE_ID = 2;

    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY, DatabaseContract.TABEL, FAVORITE);

        URI_MATCHER.addURI(AUTHORITY, DatabaseContract.TABEL+"/#",FAVORITE_ID);

    }

    private FavMovieHelper favMovieHelper;

    @Override
    public boolean onCreate() {
        favMovieHelper = new FavMovieHelper(getContext());

        favMovieHelper.open();

        return true;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int updated;
        switch (URI_MATCHER.match(uri)){
            case FAVORITE_ID:

                updated = favMovieHelper.updateProvider(uri.getLastPathSegment(),values);

                break;
            default:

                updated = 0;

                break;
        }
        if (updated>0){

            getContext().getContentResolver().notifyChange(uri, null);
        }
        return updated;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        switch (URI_MATCHER.match(uri)){
            case FAVORITE:

                cursor = favMovieHelper.queryProvider();

                break;
            case FAVORITE_ID:

                cursor = favMovieHelper.queryByIdProvider(uri.getLastPathSegment());

                break;
            default:

                cursor = null;

                break;
        }
        if (cursor!=null){

            cursor.setNotificationUri(getContext().getContentResolver(),uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        long added;
        switch (URI_MATCHER.match(uri)){
            case FAVORITE:
                added = favMovieHelper.insertProvider(values);
                break;
            default:
                added = 0;
                break;
        }
        if (added>0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return Uri.parse(CONTENT_URI+"/"+added);
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        int deleted;
        switch (URI_MATCHER.match(uri)){
            case FAVORITE_ID:
                deleted = favMovieHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }
        if (deleted>0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return deleted;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }
}
