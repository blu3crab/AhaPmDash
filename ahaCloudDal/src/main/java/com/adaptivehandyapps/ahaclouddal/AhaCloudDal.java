// Project: AHA PM Admin
// Contributor(s): M.A.Tucker, Nathan A. Tucker
// Copyright � 2014 Adaptive Handy Apps, LLC.  All Rights Reserved.

// request URI format: 
// /ahadioassam/admin/org/oid/add/nickname:nickname/longname:longname/address:address/site:site/staff:staff
// /ahadioassam/admin/org/oid/update/nickname:nickname/longname:longname/address:address/site:site/staff:staff
// /ahadioassam/admin/org/oid/list
// /ahadioassam/admin/org/oid/delete

// /ahadioassam/admin/org/oid/staff/sid/add/nickname:nickname/longname:longname/role:role/email:email/phone:phone
// /ahadioassam/admin/org/oid/staff/sid/update/nickname:nickname/longname:longname/role:role/email:email/phone:phone
// /ahadioassam/admin/org/oid/staff/sid/list
// /ahadioassam/admin/org/oid/staff/sid/delete
		
// /ahadioassam/admin/org/oid/site/sid/add/nickname:nickname/longname:longname/address:address/location:location
// /ahadioassam/admin/org/oid/site/sid/update/nickname:nickname/longname:longname/address:address/location:location
// /ahadioassam/admin/org/oid/site/sid/list
// /ahadioassam/admin/org/oid/site/sid/delete
// /ahadioassam/admin/org/oid/site/sid/device/did/list
// /ahadioassam/admin/org/oid/site/sid/device/did/delete
// /ahadioassam/admin/org/oid/site/sid/device/did/historymin/count:count
// /ahadioassam/admin/org/oid/site/sid/device/did/historyhour/count:count
// /ahadioassam/admin/org/oid/site/sid/device/did/historyday/count:count

// /ahadioassam/admin/org/oid/site/sid/opsstage/update/stage:stage/activity:activity/pstime:time/astime:time/pdtime:time/adtime:time/note:note
// /ahadioassam/admin/org/oid/site/sid/opsstage/list

// /ahadioassam/admin/org/oid/site/sid/sitestate/list

package com.adaptivehandyapps.ahaclouddal;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

public class AhaCloudDal {
	private final static String TAG = "AhaCloudDal";
	
	private static String resultString = null;
	private final static Object monitor = new Object();
	
	private static AhaSession mAhaSession = null;
	private static String mSessionToken = AhaDalShare.NULL_MARKER;

	////////////////////////////////////////////////////////
	// developer settings: local or cloud target
	private static String AceUrlGae = "https://1-dot-ahadiossam000.appspot.com/ahadiossam";
	private static String AceUrlLocal = "http://192.168.1.5:8888/ahadiossam";
	//////////////////////////////////////////////////////////////////////////////////////////
	// AhaDiosSam 
	private static String AceUrl = AceUrlGae;
	
	private static final String GrantUrl = "/grant/";
	private static final String AccessUrl = "access/";
	private static final String ReleaseUrl = "release/";
	private static final String SessionKey = "session";
	
	private static final String OrgUrl = "/admin/org/";
	private static final String OrgAllUrl = "oid";
	private static final String StaffUrl = "/staff/";
	private static final String StaffAllUrl = "sid";
	private static final String SiteUrl = "/site/";
	private static final String SiteAllUrl = "sid";
	private static final String OpsStageUrl = "/opsstage";
//	private static final String SiteStateUrl = "/sitestate";
	private static final String SiteLifeUrl = "/sitelife";
	private static final String DeviceUrl = "/device/";
	private static final String DeviceAllUrl = "did";

	private static final String AddUrl = "/add";
	private static final String UpdateUrl = "/update";
	private static final String ListUrl = "/list";
	private static final String DeleteUrl = "/delete";

	private static final String NicknameUrl = "/nickname:";
	private static final String LongnameUrl = "/longname:";
	private static final String AddressUrl = "/address:";
	private static final String LocationUrl = "/location:";
	private static final String SiteListUrl = "/site:";
	private static final String StaffListUrl = "/staff:";
	private static final String TagListUrl = "/tag:";
	private static final String RoleUrl = "/role:";
	private static final String EmailUrl = "/email:";
	private static final String SuperEmailUrl = "/super:";
	private static final String StageUrl = "/stage:";
	private static final String ActivityUrl = "/activity:";
	private static final String PsTimeUrl = "/pstime:";
	private static final String PdTimeUrl = "/pdtime:";
	private static final String AsTimeUrl = "/astime:";
	private static final String AdTimeUrl = "/adtime:";
	private static final String NoteUrl = "/note:";
	private static final String CountUrl = "/count:";

	// AceCollectActivty run commands
	private static final String AceRunUrl = "/run/org/";
	private static final String AceLocatorUrl = "/locator";
	private static final String AceSummaryUrl = "/summary/";

	// AcePulse commands
	private static final String AcePulseUrl = "/pulse";
	private static final String AcePulseResetUrl = "/reset";
	private static final String AcePulseFlushUrl = "/flush";

	//////////////////////////////////////////////////////////////////////////////////////////
	// AhaDiosSam 
	public static boolean init(Context context) {
        // establish DiagTools for AhaCloudDal
		DiagTools.setContext(context);
		DiagTools.registerHandler();
		return true;
	}
	// get/set ACE target URL
	public static String getAceUrl() {
		return AceUrl;		
	}
	public static void setAceUrl( String newUrl )
	{
		if( !newUrl.contains("https://" ))
		{
			newUrl = "https://" + newUrl;
		}
		
//		if( !newUrl.endsWith("/"))
//		{
//			newUrl += "/";
//		}
		
//		AceUrl = newUrl;
//		Log.v(TAG, "setAceUrl url: " + AceUrl);
	}
	//////////////////////////////////////////////////////////////////////////////////////////
	// grant AHA access
	public static AhaSession getAhaSession() { return mAhaSession; }

    public static void setAhaSession(AhaSession ahaSession)
    {
        //only set if we are coming back from a paused state
        if( mAhaSession == null )
        {
            mAhaSession = ahaSession;
        }

        if( mSessionToken.equals(AhaDalShare.NULL_MARKER) )
        {
            mSessionToken = mAhaSession.getToken();
        }
    }
	
	public static String grantAhaAccess(String email, String pwd) {
		String url = getAceUrl() + GrantUrl + AccessUrl + email + ":" + pwd;
		// TODO: remove pwd log!!
		Log.v(TAG, "grantAhaAccess url: " + url);
		// get grant result
		String result = getResultString( url );
		
		if (!result.startsWith(AhaDalShare.STATUS_CODE_MARKER)) {
			Log.v(TAG, "grantAhaAccess result: " + result);
			mAhaSession = decodeSession(result);
			
			if (mAhaSession != null) {
				// retain session token
				mSessionToken = mAhaSession.getToken();
				
				Log.v(TAG, "grantAhaAccess org: " + mAhaSession.getOrgId() + ", staff: " + mAhaSession.getStaffId() + 
						", role: " + mAhaSession.getRole() + ", token: " + mSessionToken);
			}
		}
		return result;
	}
	public static String releaseAhaAccess(String email) {
		String url = getAceUrl() + GrantUrl + ReleaseUrl + email;
		Log.v(TAG, "releaseAhaAccess url: " + url);
		// get release result
		String result = getResultString( url );
		// clear session & token
		mAhaSession = null;
		mSessionToken = AhaDalShare.NULL_MARKER;
		return result;
	}
	//////////////////////////////////////////////////////////////////////////////////////////
	// ACE pulse
	public static String getAcePulse() {
		// ahadiossam/pulse/list  (metrics)
		// ahadiossam/pulse/reset (metrics)
		// ahadiossam/pulse/flush (sessions)
		String url = getAceUrl() + AcePulseUrl + ListUrl;
		Log.v(TAG, "getAcePulse url: " + url);
		return getResultString( url );
	}
	//////////////////////////////////////////////////////////////////////////////////////////
	// run ACE collector update
	public static String runAceLocator(String account, boolean update) {
		// ahadiossam/run/org/ahasuper8/locator/update
		if (account.equals("*")) account = "ahasuper8";
		String opUrl;
		if (update) {
			opUrl = UpdateUrl;
		}
		else {
			opUrl = ListUrl;		
		}
		String url = getAceUrl() + AceRunUrl + account + AceLocatorUrl + opUrl;
		Log.v(TAG, "Ace Locator url: " + url);
		return getResultString( url );
	}
	public static String runAceSummary(String account, String serialNumber, boolean update) {
		// ahadiossam/run/org/ahasuper8/summary/35020765/update
		if (account.equals("*")) account = "ahasuper8";
		String opUrl;
		if (update) {
			opUrl = UpdateUrl;
		}
		else {
			opUrl = ListUrl;		
		}
		if (serialNumber.equals("*")) serialNumber = "35020765";
		String url = getAceUrl() + AceRunUrl + account + AceSummaryUrl + serialNumber + opUrl;
		Log.v(TAG, "Ace Summary url: " + url);
		return getResultString( url );
	}
	//////////////////////////////////////////////////////////////////////////////////////////
	// get object lists
	public static String getOrgList(String oid) {
		if (oid.equals("*")) oid = OrgAllUrl;
		String url = getAceUrl() + OrgUrl + oid + ListUrl;
		Log.v(TAG, "Org list url: " + url);
		return getResultString( url );
	}
	public static String getSiteList(String oid, String sid) {
		if (oid.equals("*")) oid = OrgAllUrl;
		if (sid.equals("*")) sid = SiteAllUrl;
		String url = getAceUrl() + OrgUrl + oid + SiteUrl + sid + ListUrl;
		Log.v(TAG, "Site list url: " + url);
		return getResultString( url );
	}
	public static String getStaffList(String oid, String sid) {
		if (oid.equals("*")) oid = OrgAllUrl;
		if (sid.equals("*")) sid = StaffAllUrl;
		String url = getAceUrl() + OrgUrl + oid + StaffUrl + sid + ListUrl;
		Log.v(TAG, "Staff list url: " + url);
		return getResultString( url );
	}
	public static String getOpsStageList(String oid, String sid) {
		if (oid.equals("*")) oid = OrgAllUrl;
		if (sid.equals("*")) sid = SiteAllUrl;
		String url = getAceUrl() + OrgUrl + oid + SiteUrl + sid + OpsStageUrl + ListUrl;
		Log.v(TAG, "OpsStage list url: " + url);
		return getResultString( url );
	}
//	public static String getSiteStateList(String oid, String sid) {
//		if (oid.equals("*")) oid = OrgAllUrl;
//		if (sid.equals("*")) sid = SiteAllUrl;
//		String url = getAceUrl() + OrgUrl + oid + SiteUrl + sid + SiteStateUrl + ListUrl;
//		Log.v(TAG, "SiteState list url: " + url);
//		return getResultString( url );
//	}
	public static String getSiteLifeList(String oid, String sid) {
		if (oid.equals("*")) oid = OrgAllUrl;
		if (sid.equals("*")) sid = SiteAllUrl;
		String url = getAceUrl() + OrgUrl + oid + SiteUrl + sid + SiteLifeUrl + ListUrl;
		Log.v(TAG, "SiteList list url: " + url);
		return getResultString( url );
	}
	public static String getDeviceList(String oid, String sid) {
		if (oid.equals("*")) oid = OrgAllUrl;
		if (sid.equals("*")) sid = SiteAllUrl;
		String url = getAceUrl() + OrgUrl + oid + SiteUrl + sid + DeviceUrl + DeviceAllUrl + ListUrl;
		Log.v(TAG, "Device list url: " + url);
		return getResultString( url );
	}
	//////////////////////////////////////////////////////////////////////////////////////////
	// decode object lists
	public static AhaSession decodeSession(String json) {
		AhaSession ahaSession = null;
		if( json == null || json.isEmpty() ) return ahaSession;
		Gson gson = new Gson();
		try {
			ahaSession = gson.fromJson(json, AhaSession.class);
		}
		catch( JsonSyntaxException e )
		{
			Log.e(TAG, "Session decode exception: " + e.getMessage());
			return ahaSession;
		}
		return ahaSession;
	}
	public static AhaOrgWrapper decodeOrgList(String json) {
		AhaOrgWrapper ahaOrgWrapper = null;
		if( json == null || json.isEmpty() ) return ahaOrgWrapper;
		Gson gson = new Gson();
		try {
			ahaOrgWrapper = gson.fromJson(json, AhaOrgWrapper.class);
		}
		catch( JsonSyntaxException e )
		{
			Log.e(TAG, "Org decode exception: " + e.getMessage());
			return ahaOrgWrapper;
		}
		return ahaOrgWrapper;
	}
	public static AhaStaffWrapper decodeStaffList(String json) {
		AhaStaffWrapper ahaStaffWrapper = null;
		if( json == null || json.isEmpty() ) return ahaStaffWrapper;
		Gson gson = new Gson();
		try {
			ahaStaffWrapper = gson.fromJson(json, AhaStaffWrapper.class);
		}
		catch( JsonSyntaxException e )
		{
			Log.e(TAG, "Staff decode exception: " + e.getMessage());
			return ahaStaffWrapper;
		}
		return ahaStaffWrapper;
	}
	public static AhaSiteWrapper decodeSiteList(String json) {
		AhaSiteWrapper ahaSiteWrapper = null;
		if( json == null || json.isEmpty() ) return ahaSiteWrapper;
		Gson gson = new Gson();
		try {
			ahaSiteWrapper = gson.fromJson(json, AhaSiteWrapper.class);
		}
		catch( JsonSyntaxException e )
		{
			Log.e(TAG, "Site decode exception: " + e.getMessage());
			return ahaSiteWrapper;
		}
		return ahaSiteWrapper;
	}
	public static AhaOpsStageWrapper decodeOpsStageList(String json) {
		AhaOpsStageWrapper ahaOpsStageWrapper = null;
		if( json == null || json.isEmpty() ) return ahaOpsStageWrapper;

		Gson gson = new Gson();
		try {
			ahaOpsStageWrapper = gson.fromJson(json, AhaOpsStageWrapper.class);
		}
		catch( JsonSyntaxException e )
		{
			Log.e(TAG, "OpsStage decode exception: " + e.getMessage());
			return ahaOpsStageWrapper;
		}
		return ahaOpsStageWrapper;
	}
//	public static AhaSiteStateWrapper decodeSiteStateList(String json) {
//		AhaSiteStateWrapper ahaSiteStateWrapper = null;
//		if( json == null || json.isEmpty() ) return ahaSiteStateWrapper;
//
//		Gson gson = new Gson();
//		try {
//			ahaSiteStateWrapper = gson.fromJson(json, AhaSiteStateWrapper.class);
//		}
//		catch( JsonSyntaxException e )
//		{
//			Log.e(TAG, "SiteState decode exception: " + e.getMessage());
//			return ahaSiteStateWrapper;
//		}
//		return ahaSiteStateWrapper;
//	}
	public static AhaSiteLifeWrapper decodeSiteLifeList(String json) {
		AhaSiteLifeWrapper ahaSiteLifeWrapper = null;
		if( json == null || json.isEmpty() ) return ahaSiteLifeWrapper;

		Gson gson = new Gson();
		try {
			ahaSiteLifeWrapper = gson.fromJson(json, AhaSiteLifeWrapper.class);
		}
		catch( JsonSyntaxException e )
		{
			Log.e(TAG, "SiteLife decode exception: " + e.getMessage());
			return ahaSiteLifeWrapper;
		}
		return ahaSiteLifeWrapper;
	}
	public static AhaDeviceWrapper decodeDeviceList(String json) {
		AhaDeviceWrapper ahaDeviceWrapper = null;
		if( json == null || json.isEmpty() ) return ahaDeviceWrapper;
		Gson gson = new Gson();
		try {
			ahaDeviceWrapper = gson.fromJson(json, AhaDeviceWrapper.class);
		}
		catch( JsonSyntaxException e )
		{
			Log.e(TAG, "Device decode exception: " + e.getMessage());
			return ahaDeviceWrapper;
		}
		return ahaDeviceWrapper;
	}
	//////////////////////////////////////////////////////////////////////////////////////////
	// org operations
	public static String addOrg (String oid, AhaOrg ahaOrg) {
		// TODO: refactor common add/update url construction
		if (oid.equals("*")) oid = OrgAllUrl;
		String url = getAceUrl() + OrgUrl + oid + AddUrl + 
				NicknameUrl + ahaOrg.getNickname() + LongnameUrl + ahaOrg.getLongname() +
				AddressUrl + ahaOrg.getAddress();
		List<String> list;
		list = ahaOrg.getSiteList();
		for (String s : list) {
			url = url.concat(SiteListUrl + s);
		}
		list = ahaOrg.getStaffList();
		for (String s : list) {
			url = url.concat(StaffListUrl + s);
		}
		list = ahaOrg.getTagList();
		for (String s : list) {
			url = url.concat(TagListUrl + s);
		}
		url = url.replace(" ", "%20");
		Log.v(TAG, "Org add url: " + url);
		return getResultString( url );
	}
	public static String updateOrg (AhaOrg ahaOrg) {
		String url = getAceUrl() + OrgUrl + ahaOrg.getAhaId() + UpdateUrl + 
				NicknameUrl + ahaOrg.getNickname() + 
				LongnameUrl + ahaOrg.getLongname() +
				AddressUrl + ahaOrg.getAddress();
		List<String> list;
		list = ahaOrg.getSiteList();
		for (String s : list) {
			url = url.concat(SiteListUrl + s);
		}
		list = ahaOrg.getStaffList();
		for (String s : list) {
			url = url.concat(StaffListUrl + s);
		}
		list = ahaOrg.getTagList();
		for (String s : list) {
			url = url.concat(TagListUrl + s);
		}
		url = url.replace(" ", "%20");
		Log.v(TAG, "Org update url: " + url);
		return getResultString( url );
	}
	public static String deleteOrg (String oid) {
		if (oid.equals("*")) oid = OrgAllUrl;
		String url = getAceUrl() + OrgUrl + oid + DeleteUrl;
		Log.v(TAG, "Site delete url: " + url);
		return getResultString( url );
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////
	// staff operations
	public static String addStaff (String oid, AhaStaff ahaStaff) { 
		if (oid.equals("*")) oid = OrgAllUrl;
		String sid = StaffAllUrl;
		String url = getAceUrl() + OrgUrl + oid + StaffUrl + sid + AddUrl + 
				NicknameUrl + ahaStaff.getNickname() + LongnameUrl + ahaStaff.getLongname() +
				RoleUrl + ahaStaff.getRole() + EmailUrl + ahaStaff.getEmail() + 
				SuperEmailUrl + ahaStaff.getSuperEmail();
		List<String> list = ahaStaff.getTagList();
		for (String s : list) {
			url = url.concat(TagListUrl + s);
		}
		url = url.replace(" ", "%20");
		Log.v(TAG, "Staff add url: " + url);
		return getResultString( url );
	}
	public static String updateStaff (String oid, AhaStaff ahaStaff) {
		if (oid.equals("*")) oid = OrgAllUrl;
		String sid = ahaStaff.getAhaId();
		String url = getAceUrl() + OrgUrl + oid + StaffUrl + sid + UpdateUrl + 
				NicknameUrl + ahaStaff.getNickname() + LongnameUrl + ahaStaff.getLongname() +
				RoleUrl + ahaStaff.getRole() + EmailUrl + ahaStaff.getEmail() + 
				SuperEmailUrl + ahaStaff.getSuperEmail();
		List<String> list = ahaStaff.getTagList();
		for (String s : list) {
			url = url.concat(TagListUrl + s);
		}
		url = url.replace(" ", "%20");
		Log.v(TAG, "Staff update url: " + url);
		return getResultString( url );
	}
	public static String deleteStaff (String oid, String sid) {
		if (oid.equals("*")) oid = OrgAllUrl;
		if (sid.equals("*")) sid = StaffAllUrl;
		String url = getAceUrl() + OrgUrl + oid + StaffUrl + sid + DeleteUrl;
		Log.v(TAG, "Staff delete url: " + url);
		return getResultString( url );
	}
	//////////////////////////////////////////////////////////////////////////////////////////
	// site operations
	public static String addSite (String oid, AhaSite ahaSite) { 
		if (oid.equals("*")) oid = OrgAllUrl;
		String sid = SiteAllUrl;
		String url = getAceUrl() + OrgUrl + oid + SiteUrl + sid + AddUrl + 
				NicknameUrl + ahaSite.getNickname() + LongnameUrl + ahaSite.getLongname() +
				AddressUrl + ahaSite.getAddress() + LocationUrl + ahaSite.getLocation() +
				EmailUrl + ahaSite.getOwnerEmail() + CountUrl + ahaSite.getOwnerPinCount().toString();
		List<String> list = ahaSite.getTagList();
		for (String s : list) {
			url = url.concat(TagListUrl + s);
		}
		url = url.replace(" ", "%20");
		Log.v(TAG, "Site add url: " + url);
		return getResultString( url );
	}
	public static String updateSite (String oid, AhaSite ahaSite) {
		if (oid.equals("*")) oid = OrgAllUrl;
		String sid = ahaSite.getAhaId();
		String url = getAceUrl() + OrgUrl + oid + SiteUrl + sid + UpdateUrl + 
				NicknameUrl + ahaSite.getNickname() + LongnameUrl + ahaSite.getLongname() +
				AddressUrl + ahaSite.getAddress() + LocationUrl + ahaSite.getLocation() +
				EmailUrl + ahaSite.getOwnerEmail() + CountUrl + ahaSite.getOwnerPinCount().toString();
		List<String> list = ahaSite.getTagList();
		for (String s : list) {
			url = url.concat(TagListUrl + s);
		}
		url = url.replace(" ", "%20");
		Log.v(TAG, "Site update url: " + url);
		return getResultString( url );
	}
	public static String deleteSite (String oid, String sid) {
		if (oid.equals("*")) oid = OrgAllUrl;
		if (sid.equals("*")) sid = SiteAllUrl;
		String url = getAceUrl() + OrgUrl + oid + SiteUrl + sid + DeleteUrl;
		Log.v(TAG, "Site delete url: " + url);
		return getResultString( url );
	}
	//////////////////////////////////////////////////////////////////////////////////////////
	// site operations
	public static String updateOpsStage (String oid, AhaOpsStage ahaOpsStage) {
		if (oid.equals("*")) oid = OrgAllUrl;
		String sid = ahaOpsStage.getSiteId();
//		String url = getAceUrl() + OrgUrl + oid + SiteUrl + sid + OpsStageUrl + UpdateUrl + 
//				StageUrl + ahaOpsStage.getStage() + ActivityUrl + ahaOpsStage.getActivityKpi() +
//				PsTimeUrl + ahaOpsStage.getPlanStartTime() + AsTimeUrl + ahaOpsStage.getActualStartTime() + 
//				PdTimeUrl + ahaOpsStage.getPlanDoneTime() + AdTimeUrl + ahaOpsStage.getActualDoneTime();
		String url = getAceUrl() + OrgUrl + oid + SiteUrl + sid + OpsStageUrl + UpdateUrl + 
				StageUrl + ahaOpsStage.getStage() + ActivityUrl + ahaOpsStage.getActivityKpi(); 
		// append times if defined		
		if (!ahaOpsStage.getPlanStartTime().equals(BigInteger.valueOf(0))) {
			url = url.concat(PsTimeUrl + ahaOpsStage.getPlanStartTime());
		}
		if (!ahaOpsStage.getActualStartTime().equals(BigInteger.valueOf(0))) {
			url = url.concat(AsTimeUrl + ahaOpsStage.getActualStartTime());
		}
		if (!ahaOpsStage.getPlanDoneTime().equals(BigInteger.valueOf(0))) {
			url = url.concat(PdTimeUrl + ahaOpsStage.getPlanDoneTime());
		}
		if (!ahaOpsStage.getActualDoneTime().equals(BigInteger.valueOf(0))) {
			url = url.concat(AdTimeUrl + ahaOpsStage.getActualDoneTime());
		}
		// append notes if defined		
		if (!ahaOpsStage.getNote().isEmpty()) {
			url = url.concat(NoteUrl + ahaOpsStage.getNote());
		}
		url = url.replace(" ", "%20");
		Log.v(TAG, "Site update url: " + url);
		return getResultString( url );
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////
	// post url returning resultString
	private static String getResultString( String url )
	{
		resultString = null;
		
		GetServerJsonAsync task = new GetServerJsonAsync(url);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void[])null);
		else
			task.execute((Void[])null);
		
		synchronized( monitor )
		{
			while( resultString == null )
			{
				try {
					monitor.wait();
				} catch (InterruptedException e) {
					Log.e(TAG, "getResultString exception: " + e.getMessage());
					e.printStackTrace();
				}
			}
			
			return resultString;
		}
	}
	
	private static class GetServerJsonAsync extends AsyncTask<Void, Void, Void> {

		private String url;
		private long startTime;
		
		public GetServerJsonAsync(String u)
		{
			super();
			this.url = u;
		}
		
		@Override
		protected Void doInBackground(Void... arg0) {
			synchronized( monitor )
			{
				// if session token undefined & not a grant request
				if (mSessionToken.equals(AhaDalShare.NULL_MARKER) &&	!url.contains(GrantUrl)) {
					// short-circuit request
			        resultString = AhaDalShare.STATUS_CODE_MARKER + "401: " + "Please signin.";
					Log.e(TAG, resultString);						
					return null;
				}
		        HttpEntity httpEntity = null;
		        HttpResponse httpResponse = null;
		        HttpGet httpGet = new HttpGet(this.url);
		        httpGet.setHeader(SessionKey, mSessionToken);
		        Log.v(TAG, SessionKey + " token: " + mSessionToken);
		        
		        DefaultHttpClient httpClient = new DefaultHttpClient();
		        HttpParams params = httpClient.getParams();
		        
		        HttpConnectionParams.setConnectionTimeout(params, 10000);
		        HttpConnectionParams.setSoTimeout(params, 10000);
		        
		        try {
					httpResponse = httpClient.execute(httpGet);
			        httpEntity = httpResponse.getEntity();
			        int httpStatusCode = httpResponse.getStatusLine().getStatusCode();
					if ( httpStatusCode == HttpStatus.SC_OK) {
				        resultString = EntityUtils.toString(httpEntity);
					}
					else {
						// TODO: update DiagTools with SC
				        resultString = AhaDalShare.STATUS_CODE_MARKER + httpStatusCode + ": " + EntityUtils.toString(httpEntity);
					}
			        monitor.notify();
				} 
		        catch (ClientProtocolException e) {
					// TODO: on protocol error, save latest message
					Log.e(TAG, "doInBackground exception: ClientProtocolException");
					if (e.getMessage() != null) {
						Log.e(TAG, "doInBackground ClientProtocolException exception:" + e.getMessage());						
					}
					resultString = "SC 500: doInBackground ClientProtocolException exception.";
					DiagTools.updateFailure();
					monitor.notify();
				} 
		        catch (IOException e) {
					Log.e(TAG, "doInBackground exception: IOException - cloud timeout?");
					// test getMessage results - NULL if socket timeout
					if (e.getMessage() != null) {
						Log.e(TAG, "doInBackground exception: " + e.getMessage());						
					}
					resultString = "SC 502: doInBackground exception: IOException.";
					DiagTools.updateFailure();
					monitor.notify();
				} 
		        catch (Exception e) {
					Log.e(TAG, "doInBackground exception: Exception - cloud timeout?");
					// test getMessage results - NULL if socket timeout
					if (e.getMessage() != null) {
						Log.e(TAG, "doInBackground exception: " + e.getMessage());						
					}
					resultString = "SC 504: doInBackground exception: Exception.";
					DiagTools.updateFailure();
					monitor.notify();
				}
			}
	        
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result){
			DiagTools.updateSuccess((System.currentTimeMillis() - startTime));
		}
		
		@Override
		protected void onPreExecute(){
			startTime = System.currentTimeMillis();
		}
		
	}
}
