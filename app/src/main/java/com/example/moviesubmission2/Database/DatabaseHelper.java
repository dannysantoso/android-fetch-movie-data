package com.example.moviesubmission2.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.moviesubmission2.DatabaseContract;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "movieCatalogue";
    private static final String SQL_CREATE_TABEL = String.format("CREATE TABLE %s"+
                    "(%s INTEGER PRIMARY KEY,"+
                    "%s TEXT NOT NULL,"+
                    "%s TEXT NOT NULL,"+
                    "%s TEXT NOT NULL,"+
                    "%s TEXT NOT NULL,"+
                    "%s TEXT NOT NULL)",
            DatabaseContract.TABEL,
            DatabaseContract.MovieColumns._ID,
            DatabaseContract.MovieColumns.JUDUL,
            DatabaseContract.MovieColumns.DESKRIPSI,
            DatabaseContract.MovieColumns.RILIS,
            DatabaseContract.MovieColumns.GAMBAR,
            DatabaseContract.MovieColumns.RATE
    );

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABEL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+DatabaseContract.TABEL);
        onCreate(db);
    }

//    public boolean favorited(String id, String title,String date, String rate, String image, String desc, String type){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put("ID",id);
//        cv.put("Title",title);
//        cv.put("Date",date);
//        cv.put("Rate",rate);
//        cv.put("Image",image);
//        cv.put("Description",desc);
//        cv.put("Type",type);
//        long result = db.replace("Favorite", null, cv);
//        if (result == -1){
//            return false;
//        }
//        return true;
//    }
//
//    public Cursor getFavoriteMovie(){
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor result = db.rawQuery("SELECT ID, Image, Title, Date, Rate, Description FROM Favorite WHERE Type = 'movie'", null);
//
//        return result;
//    }
//
//    public Cursor getFavoriteTvshow(){
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor result = db.rawQuery("SELECT ID, Image, Title, Date, Rate, Description FROM Favorite WHERE Type = 'tvshow'", null);
//
//        return result;
//    }
//
//    public Integer removeFavorite(String id){
//        SQLiteDatabase db = this.getWritableDatabase();
//        return db.delete("Favorite","ID = ?",new String[]{id});
//    }
}
