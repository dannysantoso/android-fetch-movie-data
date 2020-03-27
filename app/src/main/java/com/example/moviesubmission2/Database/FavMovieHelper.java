package com.example.moviesubmission2.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.example.moviesubmission2.DatabaseContract.TABEL;

public class FavMovieHelper {
    private static String DATABASE_TABLE = TABEL;
    private Context mContext;
    private DatabaseHelper mDatabaseHelper;
    private SQLiteDatabase mDatabase;
    private static FavMovieHelper INSTANCE;

    public FavMovieHelper(Context mContext){
        this.mContext = mContext;
    }

    public static FavMovieHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FavMovieHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        mDatabaseHelper = new DatabaseHelper(mContext);
        mDatabase = mDatabaseHelper.getWritableDatabase();
    }

    public long insertProvider(ContentValues contentValues){
        return mDatabase.insert(DATABASE_TABLE, null, contentValues);
    }

    public void close(){
        mDatabaseHelper.close();
    }

    public Cursor queryByIdProvider(String id){
        return mDatabase.query(DATABASE_TABLE,null,_ID+"=?",new String[]{id},null,null,null,null);
    }

    public int updateProvider(String id, ContentValues contentValues){
        return mDatabase.update(DATABASE_TABLE, contentValues,_ID+"=?",new String[]{id});
    }

    public int deleteProvider(String id){
        return mDatabase.delete(DATABASE_TABLE,_ID+"=?",new String[]{id});
    }

    public Cursor queryProvider(){
        return mDatabase.query(DATABASE_TABLE, null,null,null,null,null,_ID+" DESC");
    }
}
