package com.example.practice_9_task.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.practice_9_task.LoginFragment;
import com.example.practice_9_task.Model.Model;
import com.example.practice_9_task.database.Dbhelper;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.practice_9_task.database.DbScema.TABLE;
import static com.example.practice_9_task.database.DbScema.USER;

public class Repositroy {
    public static Integer flag=1;
    public static final String TAG = "TAG";
    private ArrayList<Model> mMaptodo = new ArrayList<>();
    private ArrayList<Model> mMapdone = new ArrayList<>();
    private ArrayList<Model> mMapding = new ArrayList<>();
    private Context mContext;
    private SQLiteDatabase mSQLiteDatabase;

    public ArrayList<Model> getArrayList(String name, String password, String state) {
        ArrayList<Model> mArrayList = new ArrayList<>();
        String idUser = sendIdUser(name, password);
        if (idUser.equals("-1")) {
            return new ArrayList<>();

        }

        Cursor cursor2 = mSQLiteDatabase.rawQuery("SELECT  * FROM  " + TABLE.TABLE_NAME + " WHERE " + TABLE.Cols.IDUSER + " = ?", new String[]{idUser}
        );
        cursor2.moveToFirst();
        if (cursor2 == null || cursor2.getCount() == 0) {
            return mArrayList;
        }
        Model model = new Model();
        mMapding.clear();
        mMapdone.clear();
        mMaptodo.clear();
        try {

            while (!cursor2.isAfterLast()) {

                model = new Model();
                model.setTitle(cursor2.getString(cursor2.getColumnIndex(TABLE.Cols.Title)));
                model.setDescription(cursor2.getString(cursor2.getColumnIndex(TABLE.Cols.DESCRIPTION)));
                model.setTime(cursor2.getString(cursor2.getColumnIndex(TABLE.Cols.TIME)));
                model.setDate(cursor2.getString(cursor2.getColumnIndex(TABLE.Cols.DATE)));
                model.setmStatebool(Boolean.getBoolean((cursor2.getString(cursor2.getColumnIndex(TABLE.Cols.STATEBOOL)))));
                model.ismStatebool();
                model.setMstate(cursor2.getInt(cursor2.getColumnIndex(TABLE.Cols.SPINNERSTATE)));
                if (model.getMstate() == 0)
                    mMaptodo.add(model);
                else if (model.getMstate() == 2)
                    mMapdone.add(model);
                else
                    mMapding.add(model);
                cursor2.moveToNext();

            }
        } finally {
            cursor2.close();

        }
        if (model.getMstate() == 0 )
            return mMaptodo;
        else if (model.getMstate() == 2)
        return mMapdone;
        else if (model.getMstate() == 1)
            return mMapding;
        else
            return new ArrayList<>();



    }


    private static Repositroy Instance;

    public static Repositroy newInstance(Context context) {
        if (Instance == null)
            Instance = new Repositroy(context);
        return Instance;
    }

    private Repositroy(Context context) {
        mContext = context.getApplicationContext();
        Dbhelper dbhelper = new Dbhelper(mContext);
        mSQLiteDatabase = dbhelper.getWritableDatabase();

    }


    public void add(String name, String password, String title, String desc, boolean state, String date, String time, int intstate) {

        Model model = new Model();
        model.setTitle(title);
        model.setDescription(desc);
        model.setDate(date);
        model.setTime(time);
        model.setmStatebool(state);
        model.setMstate(intstate);
        ContentValues contentValues = getcontentvalue(model, name, password);
        mSQLiteDatabase.insert(TABLE.TABLE_NAME, null, contentValues);


    }

    public void addAcount(String name, String password) {
        Cursor cursor = mSQLiteDatabase.rawQuery("SELECT *  FROM " + USER.TABLE_NAME + " WHERE "
                + USER.Cols.NAME + " = ? AND " + USER.Cols.PASSWIRDID + " = ?", new String[]{name, password});
        cursor.moveToFirst();
        if (cursor == null || cursor.getCount() == 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(USER.Cols.NAME, name);
            contentValues.put(USER.Cols.PASSWIRDID, password);
            mSQLiteDatabase.insert(USER.TABLE_NAME, null, contentValues);

        }
        return;


    }

    public boolean find(String name, String password) {
        Cursor cursor = mSQLiteDatabase.rawQuery("SELECT  * FROM " + USER.TABLE_NAME + " WHERE " + USER.Cols.NAME + " = ? and "
                + USER.Cols.PASSWIRDID + " = ?", new String[]{name, password});
        cursor.moveToFirst();
        try {
            if (cursor == null || cursor.getCount() == 0) {
                return false;
            }
        } finally {
            cursor.close();
        }
        return true;
    }

    public void replace(String key, Model lastmodel, Model newmodel) {

    }


    public boolean delete(Model model, String key) {
        return ((mSQLiteDatabase.delete(TABLE.TABLE_NAME, model.getTitle() + "= ?", new String[]{TABLE.Cols.Title})) > 0);

    }

    private ContentValues getcontentvalue(Model model, String name, String password) {
        ContentValues values = new ContentValues();
        values.put(TABLE.Cols.Title, model.getTitle());
        values.put(TABLE.Cols.DESCRIPTION, model.getDescription());
        values.put(TABLE.Cols.TIME, model.getTime());
        values.put(TABLE.Cols.DATE, model.getDate());
        values.put(TABLE.Cols.SPINNERSTATE, model.getMstate());
        values.put(TABLE.Cols.STATE, model.getMstate());
        values.put(TABLE.Cols.STATEBOOL, model.ismStatebool());
        values.put(TABLE.Cols.IDUSER, sendIdUser(name, password));
        return values;
    }

    private String sendIdUser(String name, String password) {
        Cursor cursor1 = mSQLiteDatabase.rawQuery(" SELECT  " + USER.Cols.ID + "  FROM " + USER.TABLE_NAME + " WHERE " + USER.Cols.NAME +
                " = ? AND " + USER.Cols.PASSWIRDID + " = ?", new String[]{name, password});
        cursor1.moveToFirst();
        if (cursor1 == null
                || cursor1.getCount() == 0) {
            cursor1.close();
            return "-1";
        }

        return cursor1.getString(cursor1.getColumnIndex(USER.Cols.ID));

    }

}
