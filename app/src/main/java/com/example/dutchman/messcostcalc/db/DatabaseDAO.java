package com.example.dutchman.messcostcalc.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by dutchman on 8/9/17.
 */

public class DatabaseDAO {

    protected SQLiteDatabase database;
    private DBHandler handler;
    private Context mContext;

    public DatabaseDAO(Context context) {
        this.mContext = context;
        handler = DBHandler.getInstance(mContext);
        open();

    }

    public void open() throws SQLException {
        if(handler == null)
            handler = DBHandler.getInstance(mContext);
        database = handler.getWritableDatabase();
    }
}
