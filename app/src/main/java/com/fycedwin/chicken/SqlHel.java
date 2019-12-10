package com.fycedwin.chicken;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqlHel extends SQLiteOpenHelper {

    public SqlHel(Context context, String name, Integer ver) {
        super(context, name, null, ver);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SqlQry.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SqlQry.DROP_TABLE);
        db.execSQL(SqlQry.CREATE_TABLE);
    }
}
