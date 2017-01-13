// Project: AHA PM Data Access Layer
// Contributor(s): Nathan A. Tucker, M.A.Tucker
// Copyright � 2014 Adaptive Handy Apps, LLC.  All Rights Reserved.

package com.adaptivehandyapps.ahaclouddal;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.Log;

public class DiagTools {

	private final static String TAG = "DiagTools";

	private static Context context = null;
	private static String DIAGTOOLS_PREFS_NAME = "DIAGTOOLS_PREFS";
	private static String DIAGTOOLS_KEY_MIN_REQUEST_TIME = "minRequestTime";
	private static String DIAGTOOLS_KEY_MAX_REQUEST_TIME = "maxRequestTime";
	private static String DIAGTOOLS_KEY_TOTAL_REQUEST_TIME = "totalRequestTime";
	private static String DIAGTOOLS_KEY_AVERAGE_REQUEST_TIME = "averageRequestTime";
	private static String DIAGTOOLS_KEY_AMOUNT_OF_REQUESTS = "amountOfRequests";
	private static String DIAGTOOLS_KEY_CLOUD_TIMEOUT = "CloudTimeout";
	private static String DIAGTOOLS_KEY_PHONE_TIMEOUT = "PhoneTimeout";
	private static String DIAGTOOLS_KEY_SEND_DIAG = "pref_key_send_diag";
	private static String DIAGTOOLS_KEY_ACE_URL = "pref_key_ace_url";

	// setter for context
	public static boolean setContext(Context app_context) {
		context = app_context;
		return true;
	}
	private static boolean isNetworkAvailable() {
		// ensure context defined
		if (context == null) {
			Log.e(TAG, "Context undefined.");
			return false;
		}
		
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null;
	}
	
	public static synchronized void updateSuccess(long time)
	{
		// ensure context defined
		if (context == null) {
			Log.e(TAG, "Context undefined.");
			return;
		}

		SharedPreferences settings = context.getSharedPreferences(DIAGTOOLS_PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		
		long minRequestTime = settings.getLong(DIAGTOOLS_KEY_MIN_REQUEST_TIME, Long.MAX_VALUE);
		if( time < minRequestTime )
		{
			editor.putLong(DIAGTOOLS_KEY_MIN_REQUEST_TIME, time);
		}
		
		long maxRequestTime = settings.getLong(DIAGTOOLS_KEY_MAX_REQUEST_TIME, 0);
		if( time > maxRequestTime )
		{
			editor.putLong(DIAGTOOLS_KEY_MAX_REQUEST_TIME, time);
		}

		int amountOfRequests = settings.getInt(DIAGTOOLS_KEY_AMOUNT_OF_REQUESTS, 0);
		long totalRequestTime = settings.getLong(DIAGTOOLS_KEY_TOTAL_REQUEST_TIME, 0);
		
		editor.putInt(DIAGTOOLS_KEY_AMOUNT_OF_REQUESTS, amountOfRequests + 1);
		editor.putLong(DIAGTOOLS_KEY_TOTAL_REQUEST_TIME, (totalRequestTime + time) );
		editor.putLong(DIAGTOOLS_KEY_AVERAGE_REQUEST_TIME, (totalRequestTime + time) / (amountOfRequests + 1 ) );

		editor.commit();
	}
	
	public static synchronized void updateFailure()
	{
		// ensure context defined
		if (context == null) {
			Log.e(TAG, "Context undefined.");
			return;
		}

		SharedPreferences settings = context.getSharedPreferences(DIAGTOOLS_PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();

		if( isNetworkAvailable() )
		{
			editor.putInt(DIAGTOOLS_KEY_CLOUD_TIMEOUT, settings.getInt(DIAGTOOLS_KEY_CLOUD_TIMEOUT, 0) + 1 );
		}
		else
		{
			editor.putInt(DIAGTOOLS_KEY_PHONE_TIMEOUT, settings.getInt(DIAGTOOLS_KEY_PHONE_TIMEOUT, 0) + 1 );
		}

		editor.commit();
	}
	
	public static String getDiagInfo()
	{
		// ensure context defined
		if (context == null) {
			Log.e(TAG, "Context undefined.");
			return "Context undefined.";
		}

		SharedPreferences settings = context.getSharedPreferences(DIAGTOOLS_PREFS_NAME, 0);
		
		return "Total Requests: " + settings.getInt(DIAGTOOLS_KEY_AMOUNT_OF_REQUESTS, 0) + 
				"\nMin. Request Time: " + settings.getLong(DIAGTOOLS_KEY_MIN_REQUEST_TIME, 0) + 
				"ms\nMax. Request Time: " + settings.getLong(DIAGTOOLS_KEY_MAX_REQUEST_TIME, 0) + 
				"ms\nAvg. Request Time: " + settings.getLong(DIAGTOOLS_KEY_AVERAGE_REQUEST_TIME, 0) + 
				"ms\nService Timeouts: " + settings.getInt(DIAGTOOLS_KEY_CLOUD_TIMEOUT, 0) + 
				"\nPhone Timeouts: " + settings.getInt(DIAGTOOLS_KEY_PHONE_TIMEOUT, 0);
	}
	
//	public static void getDisplayDiagInfo( final Activity callerContext )
//	{
//		View view = callerContext.getLayoutInflater().inflate(R.layout.diag_info_detail_dialogue, null);
//		
//		final EditText etAceUrl = (EditText)view.findViewById(R.id.etAceUrl);
//		etAceUrl.setText(AhaCloudDal.getAceUrl());
//		
//		TextView tvHealth = (TextView)view.findViewById(R.id.tvHealth);
//		tvHealth.setText(DiagTools.getDiagInfo());
//		
//		new AlertDialog.Builder(callerContext)
//		.setTitle("Diagnosis Info")
//		.setView( view )
//		.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//			public void onClick(DialogInterface dialog, int which) {
//				String newAceUrl = etAceUrl.getText().toString();
//				AhaCloudDal.setAceUrl(newAceUrl);
//				
//				SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(callerContext).edit();
//				editor.putString("pref_key_ace_url", newAceUrl );
//				editor.commit();
//				
//				dialog.dismiss();
//			}
//		})
//		.show();
//	}
	
	public static void registerHandler()
	{
		// ensure context defined
		if (context == null) {
			Log.e(TAG, "Context undefined.");
			return;
		}

		ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

		executorService.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
				
				if( sharedPref.getBoolean(DIAGTOOLS_KEY_SEND_DIAG, false) )
				{
					SharedPreferences settings = context.getSharedPreferences(DIAGTOOLS_PREFS_NAME, 0);

					//TODO: for now, just log the stuff. When DAL implemented, change this to a post
					
					Log.d(TAG + "ASD", "" + 
							settings.getLong(DIAGTOOLS_KEY_MIN_REQUEST_TIME, 0) + "," + 
							settings.getLong(DIAGTOOLS_KEY_MAX_REQUEST_TIME, 0) + "," + 
							settings.getLong(DIAGTOOLS_KEY_AVERAGE_REQUEST_TIME, 0) + "," + 
							settings.getInt(DIAGTOOLS_KEY_CLOUD_TIMEOUT, 0) + "," + 
							settings.getInt(DIAGTOOLS_KEY_PHONE_TIMEOUT, 0));
				}
			}    
		}, 60, 60, TimeUnit.SECONDS);
	}
}
