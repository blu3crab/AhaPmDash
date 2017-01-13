package com.adaptivehandyapps.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CowGod on 12/7/2014.
 */
public class PreviousLoginsDAO {

    private SQLiteDatabase db;
    private BaseDatabase baseDatabaseInstance;

    public PreviousLoginsDAO(Context context)
    {
        baseDatabaseInstance = new BaseDatabase(context);
    }

    public void open()
    {
        db = baseDatabaseInstance.getWritableDatabase();
    }

    public void close()
    {
        db.close();
    }

    public void AddUserName( String userName )
    {
        ContentValues values = new ContentValues();

        if( userName == null )
            return;

        values.put(BaseDatabase.COLUMN_USERNAME, userName);

        db.insert(BaseDatabase.TABLE_PREVIOUS_LOGINS, null, values);
    }

    public List<String> GetAllPreviousUserNames( )
    {
        ArrayList<String> previousUserNames = new ArrayList<String>();

        Cursor cursor = db.query(BaseDatabase.TABLE_PREVIOUS_LOGINS, null, null, null, null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            previousUserNames.add(cursor.getString(1));

            cursor.moveToNext();
        }

        cursor.close();

        return previousUserNames;
    }
}
