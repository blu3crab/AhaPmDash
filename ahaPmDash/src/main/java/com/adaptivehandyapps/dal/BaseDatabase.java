package com.adaptivehandyapps.dal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by CowGod on 12/6/2014.
 */
public class BaseDatabase extends SQLiteOpenHelper {

    //Shared columns
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ORGID = "OrgId";

    //Sessions table
    public static final String TABLE_SESSIONS = "Session";

    public static final String COLUMN_STAFFID = "StaffId";
    public static final String COLUMN_ROLE = "Role";
    public static final String COLUMN_EMAIL = "Email";
    public static final String COLUMN_IPADDRESS = "IpAddress";
    public static final String COLUMN_REGISTERED = "Registered";
    public static final String COLUMN_TOKEN = "Token";
    public static final String COLUMN_STARTTIME = "StartTime";
    public static final String COLUMN_LASTREQUEST = "LastRequest";

    //PreviousLogins table
    public static final String TABLE_PREVIOUS_LOGINS = "PreviousLogins";

    public static final String COLUMN_USERNAME = "UserName";

    //Sites table
    public static final String TABLE_SITES = "Sites";

    public static final String COLUMN_DETAILEDCONDITION = "DetailedCondition";
    public static final String COLUMN_BASECONDITION = "BaseCondition";
    public static final String COLUMN_PROFILEPICTURE = "ProfilePicture";
    public static final String COLUMN_ACCOUNTPICTURE = "AccountPicture";
    public static final String COLUMN_SITEID = "SiteId";
    public static final String COLUMN_ADDRESS = "Address";
    public static final String COLUMN_ACCOUNTNAME = "AccountName";

    //Database
    private static final String DATABASE_NAME = "AhaOps.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE_SESSIONS = "create table " +
            TABLE_SESSIONS + "(" +
            COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_ORGID + " text not null, " +
            COLUMN_STAFFID + " text not null, " +
            COLUMN_ROLE + " text, " +
            COLUMN_EMAIL + " text, " +
            COLUMN_IPADDRESS + " text, " +
            COLUMN_REGISTERED + " integer, " +
            COLUMN_TOKEN + " text, " +
            COLUMN_STARTTIME + " real, " +
            COLUMN_LASTREQUEST + " real);";

    private static final String CREATE_TABLE_PREVIOUS_LOGINS = "create table " +
            TABLE_PREVIOUS_LOGINS + "(" +
            COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_USERNAME + " text unique);";

    private static final String CREATE_TABLE_SITES = "create table " +
            TABLE_SITES + "(" +
            COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_DETAILEDCONDITION + " text, " +
            COLUMN_BASECONDITION + " text, " +
            COLUMN_PROFILEPICTURE + " integer, " +
            COLUMN_ACCOUNTPICTURE + " integer, " +
            COLUMN_SITEID + " text not null, " +
            COLUMN_ADDRESS + " text, " +
            COLUMN_ORGID + " text not null, " +
            COLUMN_ACCOUNTNAME + " text);";

    public BaseDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SESSIONS);
        db.execSQL(CREATE_TABLE_PREVIOUS_LOGINS);
        db.execSQL(CREATE_TABLE_SITES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SESSIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PREVIOUS_LOGINS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SITES);
        onCreate(db);
    }
}
