package com.adaptivehandyapps.dal;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.adaptivehandyapps.ahaclouddal.AhaCloudDal;
import com.adaptivehandyapps.entities.ColorPreferences;

/**
 * Created by CowGod on 12/7/2014.
 */
public class PreferenceManagerHelper {

    private static final String ONTRACK_SUCCESS_KEY = "pref_key_color_ontrack_success";
    private static final String ONTRACK_RISK_KEY = "pref_key_color_ontrack_risk";
    private static final String ONTRACK_DELAY_KEY = "pref_key_color_ontrack_delay";
    private static final String ACE_URL_KEY = "pref_key_ace_url";

    private static final int ONTRACK_SUCCESS_DEFAULT_VALUE = 0x7F62CA2D;
    private static final int ONTRACK_RISK_DEFAULT_VALUE = 0x7FCFDE29;
    private static final int ONTRACK_DELAY_DEFAULT_VALUE = 0x7FF77754;

    public static ColorPreferences getColorPreferences(Context context)
    {
        ColorPreferences colorPreferences = null;

        if( context == null )
            return null;

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        colorPreferences = new ColorPreferences( sharedPreferences.getInt(ONTRACK_SUCCESS_KEY, ONTRACK_SUCCESS_DEFAULT_VALUE),
                sharedPreferences.getInt(ONTRACK_RISK_KEY, ONTRACK_RISK_DEFAULT_VALUE),
                sharedPreferences.getInt(ONTRACK_DELAY_KEY, ONTRACK_DELAY_DEFAULT_VALUE));

        return colorPreferences;
    }

    public static String getAceUrlPreferences(Context context)
    {
        if( context == null )
            return null;

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        return sharedPreferences.getString(ACE_URL_KEY, AhaCloudDal.getAceUrl());
    }

    public static void setAceUrlPreferences(Context context, String newAceUrl)
    {
        if( context == null )
            return;

        SharedPreferences.Editor sharedPreferencesEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();

        sharedPreferencesEditor.putString(ACE_URL_KEY, newAceUrl );
        sharedPreferencesEditor.commit();
    }
}
