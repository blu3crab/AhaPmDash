package com.adaptivehandyapps.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Callable;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.adaptivehandyapps.ahaclouddal.AhaCloudDal;
import com.adaptivehandyapps.ahaclouddal.AhaOpsStage;
import com.adaptivehandyapps.ahaclouddal.AhaOpsStage.OpsStage;
import com.adaptivehandyapps.ahaclouddal.AhaOpsStageWrapper;
import com.adaptivehandyapps.ahaclouddal.AhaOrgWrapper;
import com.adaptivehandyapps.ahaclouddal.AhaSite;
import com.adaptivehandyapps.ahaclouddal.AhaSiteWrapper;
import com.adaptivehandyapps.ahapmdash.R;
import com.adaptivehandyapps.entities.Site;
import com.adaptivehandyapps.entities.Site.Condition;
import com.google.gson.JsonSyntaxException;
//import com.adaptivehandyapps.gsonbindings.Account;
//import com.adaptivehandyapps.gsonbindings.AccountsWrapper;
//import com.adaptivehandyapps.gsonbindings.AhaOpsState;
//import com.adaptivehandyapps.gsonbindings.AhaOpsStateWrapper;
//import com.adaptivehandyapps.gsonbindings.SiteStateWrapper;

public class SitesWrapper {

	private static final String TAG = "SitesWrapper";

	private static ArrayList<Site> sites = null;

	private static String orgResultString = null;
	private static String siteResultString = null;
	private static String stageResultString = null;
	private static ProgressDialog pDialog = null;

	private static AsyncTask<Void, String, Void > mLoginTask = null;

	public static ArrayList<Site> getSites()
	{
		return sites;
	}

    public static void setSites( ArrayList<Site> newSites)
    {
        sites = newSites;
    }

	public static boolean loadSites(String oid)
	{
		orgResultString = AhaCloudDal.getOrgList(oid);
		siteResultString = AhaCloudDal.getSiteList(oid, "*");
		stageResultString = AhaCloudDal.getOpsStageList(oid, "*");
		// error indicated if result starts with "SC"
//		orgResultString = "SC";
		if (orgResultString.startsWith("SC") ||
			siteResultString.startsWith("SC") ||
			stageResultString.startsWith("SC")) {
			// TODO: results in password incorrect error or hang on retry
			Log.w(TAG, "loadSites fails with non-SC_OK incoming result strings.");		
			return false;
		}
		// init the wrapper
		return loadSitesIntoWrapper(orgResultString, siteResultString, stageResultString);
	}

	private static void showProgressDialog( Context context )
	{
		pDialog = new ProgressDialog( context );
		pDialog.setTitle(R.string.dialogue_loading_sites_title);
		pDialog.setMessage(context.getResources().getString(R.string.dialogue_loading_sites_message));
		pDialog.setCancelable(false);
		pDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();

				//finish();
			}
		});

		if( pDialog != null )
			pDialog.show();
	}

	private static void hideProgressDialog()
	{
		if( pDialog != null && pDialog.isShowing() )
			pDialog.dismiss();
	}
	
	public static void loadStagesTask(final Context context, final MenuItem miRefresh, final Callable<Void> onPostExecuteCallback, final Site site, final ProgressBar progressBar)
	{
		if( mLoginTask == null )
		{
			AsyncTask<Void, String, Void > task = new AsyncTask<Void, String, Void>(){
				@Override
				protected Void doInBackground(Void... params) {
					// TODO: performance - load specific site
					String result = AhaCloudDal.getOpsStageList(site.getOrgId(), site.getSiteId());
					SitesWrapper.refreshStages( result, site );
					
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					return null;
				}

				@Override
				protected void onPostExecute(Void result){
					mLoginTask = null;

					if( progressBar != null )
						progressBar.setVisibility(View.GONE);
					
					if( miRefresh != null )
						miRefresh.setVisible( true );

					if( onPostExecuteCallback != null )
					{
						try {
							onPostExecuteCallback.call();
						}
						catch (Exception e)
						{
							Log.d("AHAOPS", "Load sites task callback failed");
						}
					}
				}

				@Override
				protected void onPreExecute(){
					if( progressBar != null )
						progressBar.setVisibility(View.VISIBLE);
					
					if( miRefresh != null )
						miRefresh.setVisible( false );
				}

			};

			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
				mLoginTask = task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void[])null);
			else
				mLoginTask = task.execute((Void[])null);
		}
	}

	//nt: a bit large, but basically exists to centralise the login and download logic
	public static void loadSitesTask(final Context context, final MenuItem miRefresh, final Callable<Void> onPostExecuteCallback, 
			final Callable<Void>... onProgressUpdateCallback)
	{
		if( mLoginTask == null )
		{		
			final Callable<Void> onProgressCallback = ( (onProgressUpdateCallback == null || onProgressUpdateCallback.length == 0) ? 
					null : onProgressUpdateCallback[ 0 ]);
			
			AsyncTask<Void, String, Void > task = new AsyncTask<Void, String, Void>(){
				boolean loadSitesReturn = false;

				@Override
				protected Void doInBackground(Void... params) {
					// TODO: performance - load specific site
					loadSitesReturn = SitesWrapper.loadSites(AhaCloudDal.getAhaSession().getOrgId());

					if( loadSitesReturn )
					{
						publishProgress(context.getResources().getString(R.string.dialogue_init_list));

						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					return null;
				}

				protected void onProgressUpdate(String... ui)
				{
					if(onProgressCallback != null)
					{
						try {
							onProgressCallback.call();
						} catch (Exception e) {
							Log.d("AHAOPS", "Load sites task update callback failed");
						}
					}
					else
					{
						if( pDialog != null )
						{
							pDialog.setMessage(ui[0]);
						}
					}
				}

				@Override
				protected void onPostExecute(Void result){
					if( onProgressCallback == null )
					{
						hideProgressDialog();
					}

					mLoginTask = null;

					if( miRefresh != null )
						miRefresh.setVisible( true );

					if( loadSitesReturn )
					{
						if( onPostExecuteCallback != null )
						{
							try {
								onPostExecuteCallback.call();
							}
							catch (Exception e)
							{
								Log.d("AHAOPS", "Load sites task callback failed");
							}
						}
					}
					else
					{
						ProgressDialog pDialogNetwork = new ProgressDialog( context );
						pDialogNetwork.setTitle(R.string.dialogue_loading_sites_title);
						pDialogNetwork.setMessage(context.getResources().getString(R.string.loading_sites_error_message));
						pDialogNetwork.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
							}
						});
						pDialogNetwork.setButton(DialogInterface.BUTTON_POSITIVE, "Reload", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();

								SitesWrapper.loadSitesTask(context, miRefresh, onPostExecuteCallback, onProgressUpdateCallback);
							}
						});

						pDialogNetwork.show();
					}
				}

				@Override
				protected void onPreExecute(){
					if( onProgressCallback == null )
					{
						showProgressDialog(context);
					}

					if( miRefresh != null )
						miRefresh.setVisible( false );
				}

			};

			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
				mLoginTask = task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void[])null);
			else
				mLoginTask = task.execute((Void[])null);
		}
	}

	public static void refreshStages( String stages, Site s )
	{
		AhaOpsStageWrapper ahaOpsStageFromJson = AhaCloudDal.decodeOpsStageList(stages);

		if(ahaOpsStageFromJson != null)
		{
			s.setOpsStages( ahaOpsStageFromJson.opsstage );
			// TODO: improve performance by not scanning all sites
			for( Site savedSite : SitesWrapper.getSites() )
			{
				if( savedSite.getSiteId().equals( s.getSiteId() ) )
				{
					savedSite.setOpsStages( ahaOpsStageFromJson.opsstage );
					
					savedSite.setBaseCondition(Condition.NOFEED);
					s.setBaseCondition(Condition.NOFEED);
							
					for( AhaOpsStage os : savedSite.getOpsStages() )
					{
						if( os.getStage() == OpsStage.RFO )
						{
							switch( os.getOnTrackKpi() )
							{
							case DELAY:
                                    savedSite.setBaseCondition( Condition.ALARM );
                                    savedSite.setDetailedCondition( "Delays Requested RFO" );

                                    s.setBaseCondition( Condition.ALARM );
                                    s.setDetailedCondition("Delays Requested RFO");
								break;
							case GOOD:
									savedSite.setBaseCondition( Condition.GOOD );
									savedSite.setDetailedCondition("Complete or On Track");
									
									s.setBaseCondition( Condition.GOOD);
									s.setDetailedCondition("Complete or On Track");
								break;
							case RISK:
									savedSite.setBaseCondition(Condition.WARNING);
									savedSite.setDetailedCondition("Places Requested RFO at Risk");
									
									s.setBaseCondition(Condition.WARNING);
									s.setDetailedCondition("Places Requested RFO at Risk");
								break;
							default:
								break;
							}
							
							break;
						}
					}
				}
			}
		}
	}

	private static boolean loadSitesIntoWrapper(String orgsString, String sitesString, String stageResultString )
	{
		if( orgsString == null || orgsString.isEmpty() ||
			sitesString == null || sitesString.isEmpty() ||
			stageResultString == null || stageResultString.isEmpty() )
		{
			Log.w(TAG, "loadSitesIntoWrapper rejects NULL/empty incoming result strings.");
			return false;
		}
		
		try {
			AhaOrgWrapper ahaOrgWrapper = AhaCloudDal.decodeOrgList(orgsString);
            if( ahaOrgWrapper == null )
                return false;

			AhaSiteWrapper ahaSiteWrapper = AhaCloudDal.decodeSiteList(sitesString);
            if( ahaSiteWrapper == null )
                return false;

			AhaOpsStageWrapper ahaOpsStageWrapper = AhaCloudDal.decodeOpsStageList(stageResultString);
            if( ahaOpsStageWrapper == null )
                return false;
			
			Collections.sort( ahaSiteWrapper.sites );

			sites = new ArrayList<Site>();

			for( AhaSite s : ahaSiteWrapper.sites)
			{
				Site temp = new Site( s.getNickname(), "", Site.Condition.GOOD);

				temp.setOrgId( s.getOrgId() );

				// TODO: change accountname to orgname (account is MIOS gateway account not org)
				temp.setAccountName( ahaOrgWrapper.orgs.get(0).getNickname() );

                if (ahaOrgWrapper.orgs.get(0).getNickname().contains("Railey")) {
                    temp.setAccountPicture(R.drawable.railey_logo250);
                }
                else {
                    temp.setAccountPicture(R.drawable.yourlogo);
                }
				temp.setSiteId( s.getAhaId() );

				if( s.getNickname().equals("Bear Creek Lodge"))
                    temp.setProfilePicture(R.drawable.bearlake);
				else if(s.getNickname().equals("Dock Holiday"))
                    temp.setProfilePicture(R.drawable.dockholiday);
				else if(s.getNickname().equals("Wine And Roses"))
                    temp.setProfilePicture(R.drawable.wineandroses);
				else if(s.getNickname().equals("Grandview"))
                    temp.setProfilePicture(R.drawable.grandview);
				else if(s.getNickname().equals("The Herrington"))
                    temp.setProfilePicture(R.drawable.theherrext_14);
				else if(s.getNickname().equals("Summit Ridge"))
                    temp.setProfilePicture(R.drawable.sumridgext_10);
				else if(s.getNickname().equals("Cedar Vista"))
                    temp.setProfilePicture(R.drawable.cedvistext_13);
				else if(s.getNickname().equals("Boulder Heights"))
                    temp.setProfilePicture(R.drawable.boheigext_11);
				else if(s.getNickname().equals("Blueberry Hill"))
                    temp.setProfilePicture(R.drawable.bluebhext_13);
				else if(s.getNickname().equals("Rapture"))
                    temp.setProfilePicture(R.drawable.raptureext_11);
                else if( s.getNickname().equals("Plum Lab"))
                    temp.setProfilePicture(R.drawable.plumlab09may14);
                else if( s.getNickname().equals("Washington Monument"))
                    temp.setProfilePicture(R.drawable.washingtonmonument);
                else if( s.getNickname().equals("Taj Mahal"))
                    temp.setProfilePicture(R.drawable.tajmahal);
                else if( s.getNickname().equals("The Tower"))
                    temp.setProfilePicture(R.drawable.toweroflondon);
                else if( s.getNickname().equals("Tokyo Tower"))
                    temp.setProfilePicture(R.drawable.tokyotower);
				else
                    temp.setProfilePicture(R.drawable.home);

//				temp.setAccountPicture(R.drawable.railey_logo250);
				
				temp.setOpsStages( new ArrayList<AhaOpsStage>() );
				
				if( ahaOpsStageWrapper != null && ahaOpsStageWrapper.opsstage != null)
				{
					for(AhaOpsStage aos : ahaOpsStageWrapper.opsstage)
					{
						if( aos.getSiteId().equals(temp.getSiteId()) )
						{
							temp.getOpsStages().add(aos);
						}
					}
				}

				temp.setBaseCondition( Condition.NOFEED );
				temp.setDetailedCondition( "Estimated" );
				
				for( AhaOpsStage os : temp.getOpsStages() )
				{
					if( os.getStage() == OpsStage.RFO )
					{
						switch( os.getOnTrackKpi() )
						{
						case DELAY:
							temp.setBaseCondition(Condition.ALARM);
							temp.setDetailedCondition( "Delays Requested RFO" );
							break;
						case GOOD:
							temp.setBaseCondition( Condition.GOOD );
							temp.setDetailedCondition( "Complete or On Track" );
							break;
						case RISK:
							temp.setBaseCondition( Condition.WARNING );
							temp.setDetailedCondition( "Places Requested RFO at Risk");
							break;
						default:
							break;
						}

						break;
					}
				}

				sites.add( temp );
			}
		} 
		catch( JsonSyntaxException e )
		{
			return false;
		}

		return true;
	}
}