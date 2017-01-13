package com.adaptivehandyapps.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.adaptivehandyapps.ahaclouddal.AhaSession;

import java.math.BigInteger;

/**
 * Created by CowGod on 12/6/2014.
 */
public class SessionDAO {

    private SQLiteDatabase db;
    private BaseDatabase baseDatabaseInstance;

    public SessionDAO( Context context )
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

    public void SaveSession(AhaSession mSession)
    {
        ContentValues values = new ContentValues();

        if( mSession == null )
            return;

        values.put(BaseDatabase.COLUMN_ORGID, mSession.getOrgId());
        values.put(BaseDatabase.COLUMN_STAFFID, mSession.getStaffId());
        values.put(BaseDatabase.COLUMN_ROLE, mSession.getRole());
        values.put(BaseDatabase.COLUMN_EMAIL, mSession.getEmail());
        values.put(BaseDatabase.COLUMN_IPADDRESS, mSession.getIpaddress());
        values.put(BaseDatabase.COLUMN_REGISTERED, mSession.getRegistered());
        values.put(BaseDatabase.COLUMN_TOKEN, mSession.getToken());
        values.put(BaseDatabase.COLUMN_STARTTIME, mSession.getStarttime().doubleValue());
        values.put(BaseDatabase.COLUMN_LASTREQUEST, mSession.getLasttime().doubleValue());

        db.insert(BaseDatabase.TABLE_SESSIONS, null, values);
    }

    public void DeleteSession()
    {
        db.delete(BaseDatabase.TABLE_SESSIONS, null, null );
    }

    public AhaSession GetLastSession()
    {
        AhaSession session = new AhaSession();

        Cursor cursor = db.query(BaseDatabase.TABLE_SESSIONS, null, null, null, null, null, null);

        cursor.moveToFirst();

        session.setOrgId(cursor.getString(1));
        session.setStaffId(cursor.getString(2));
        session.setRole(cursor.getString(3));
        session.setEmail(cursor.getString(4));
        session.setIpaddress(cursor.getString(5));
        session.setRegistered(cursor.getInt(6) > 0);
        session.setToken(cursor.getString(7));
        session.setStarttime(BigInteger.valueOf(cursor.getLong(8)));
        session.setLasttime(BigInteger.valueOf(cursor.getLong(9)));

        cursor.close();

        return session;
    }
}
