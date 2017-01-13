package com.adaptivehandyapps.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.adaptivehandyapps.entities.Site;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CowGod on 12/7/2014.
 */
public class SitesDAO {
    private SQLiteDatabase db;
    private BaseDatabase baseDatabaseInstance;

    public SitesDAO(Context context)
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

    public void SaveSites( ArrayList<Site> sites)
    {
        if( sites == null )
            return;

        for(Site s: sites)
        {
            ContentValues values = new ContentValues();

            values.put(BaseDatabase.COLUMN_DETAILEDCONDITION, s.getDetailedCondition());
            values.put(BaseDatabase.COLUMN_BASECONDITION, s.getBaseCondition().ordinal());
            values.put(BaseDatabase.COLUMN_PROFILEPICTURE, s.getProfilePicture());
            values.put(BaseDatabase.COLUMN_ACCOUNTPICTURE, s.getAccountPicture());
            values.put(BaseDatabase.COLUMN_SITEID, s.getSiteId());
            values.put(BaseDatabase.COLUMN_ADDRESS, s.getAddress());
            values.put(BaseDatabase.COLUMN_ORGID, s.getOrgId());
            values.put(BaseDatabase.COLUMN_ACCOUNTNAME, s.getAccountName());

            db.insert(BaseDatabase.TABLE_SITES, null, values);
        }
    }

    public void DeleteSites()
    {
        db.delete(BaseDatabase.TABLE_SITES, null, null );
    }

    public ArrayList<Site> GetSavedSites()
    {
        ArrayList<Site> sites = new ArrayList<Site>();

        Cursor cursor = db.query(BaseDatabase.TABLE_SITES, null, null, null, null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Site temp = new Site();

            temp.setDetailedCondition(cursor.getString(1));
            temp.setBaseCondition(Site.Condition.values()[cursor.getInt(2)]);
            temp.setProfilePicture(cursor.getInt(3));
            temp.setAccountPicture(cursor.getInt(4));
            temp.setSiteId(cursor.getString(5));
            temp.setAddress(cursor.getString(6));
            temp.setOrgId(cursor.getString(7));
            temp.setAccountName(cursor.getString(8));

            sites.add(temp);

            cursor.moveToNext();
        }

        cursor.close();

        return sites;
    }
}
