package com.adaptivehandyapps.ahaopsactivity;

import com.adaptivehandyapps.ahapmdash.R;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class SettingsActivity extends PreferenceActivity {
	
	/* I don't see a point of creating an activity to wrap a fragment, so suppress these dep compliants until they bite us in the ass. */
	
	@SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences_ops);

        PreferenceManager.setDefaultValues(this, R.xml.preferences_ops, false);
    }
}
