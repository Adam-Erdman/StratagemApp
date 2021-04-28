package com.example.stratagemapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
        public static final String DATABASE_NAME = "Stratagem.db";
        public static final String TABLE_NAME = "stratagem_table";
        public static final String COL_1 = "_id";
        public static final String COL_2 = "name";
        public static final String COL_3 = "desc";
        public static final String COL_4 = "phase";
        public static final String COL_5 = "unit";
        public static final String COL_6 = "cost";
        public static final String COL_7 = "source";

        public DatabaseHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL("create table " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT,name STRING,desc STRING,phase STRING,unit STRING, cost STRING, source STRING)");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
            onCreate(db);

        }

        public void truncateData(){
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_NAME,null,null);
        }

        public boolean insertData(String name, String desc, String phase, String unit, String cost, String source) {
            //this.truncateData();
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_2,name);
            contentValues.put(COL_3,desc);
            contentValues.put(COL_4,phase);
            contentValues.put(COL_5,unit);
            contentValues.put(COL_6,cost);
            contentValues.put(COL_7,source);
            long result = db.insert(TABLE_NAME,null,contentValues);
            if(result == -1)
                return false;
            else
                return true;
        }


}
