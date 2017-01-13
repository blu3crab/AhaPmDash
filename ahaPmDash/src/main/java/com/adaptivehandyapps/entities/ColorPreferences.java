package com.adaptivehandyapps.entities;

/**
 * Created by CowGod on 12/7/2014.
 */
public class ColorPreferences {
    private int mGoodColor;
    private int mWarningColor;
    private int mAlarmColor;

    public int getGoodColor() {
        return mGoodColor;
    }

    public void setGoodColor(int mGoodColor) {
        this.mGoodColor = mGoodColor;
    }

    public int getWarningColor() {
        return mWarningColor;
    }

    public void setWarningColor(int mWarningColor) {
        this.mWarningColor = mWarningColor;
    }

    public int getAlarmColor() {
        return mAlarmColor;
    }

    public void setAlarmColor(int mAlarmColor) {
        this.mAlarmColor = mAlarmColor;
    }

    public ColorPreferences( int goodColor, int warningColor, int alarmColor )
    {
        mGoodColor = goodColor;
        mWarningColor = warningColor;
        mAlarmColor = alarmColor;
    }
}
