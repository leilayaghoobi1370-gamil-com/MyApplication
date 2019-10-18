package com.example.practice_9_task.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import static com.example.practice_9_task.database.DbScema.*;
import static com.example.practice_9_task.database.DbScema.DB_NAME;


public class Dbhelper extends SQLiteOpenHelper {

    public static final int version = 1;

    public Dbhelper(@Nullable Context context) {
        super(context, DB_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE.TABLE_NAME +
                "( " +
                TABLE.Cols.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TABLE.Cols.kEYMODLE + "," +
                TABLE.Cols.Title + "," +
                TABLE.Cols.DESCRIPTION + "," +
                TABLE.Cols.TIME + "," +
                TABLE.Cols.DATE + "," +
                TABLE.Cols.STATE + "," +
                TABLE.Cols.STATEBOOL+ "," +
                TABLE.Cols.SPINNERSTATE +","+
                TABLE.Cols.IDUSER+
                ")"


        );
        sqLiteDatabase.execSQL("CREATE TABLE " + USER.TABLE_NAME +
                "( " +
                USER.Cols.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                USER.Cols.NAME+ " STRING " + "," +
                USER.Cols.PASSWIRDID + " STRING "+
                ")"

        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
