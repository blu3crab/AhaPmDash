package com.adaptivehandyapps.ahaopsactivity;

import java.util.concurrent.Callable;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.adaptivehandyapps.ahaclouddal.AhaCloudDal;
import com.adaptivehandyapps.ahaclouddal.AhaSession;
import com.adaptivehandyapps.ahaclouddal.DiagTools;
import com.adaptivehandyapps.ahapmdash.R;
import com.adaptivehandyapps.common.DiagToolsUI;
import com.adaptivehandyapps.dal.PreferenceManagerHelper;
import com.adaptivehandyapps.dal.SitesDAO;
import com.adaptivehandyapps.entities.Site;
import com.adaptivehandyapps.common.SitesWrapper;
import com.adaptivehandyapps.dal.SessionDAO;


/**
 * An activity representing a list of Sites. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of items, which when touched, lead to a
 * {@link SiteDetailActivity} representing item details. On tablets, the
 * activity presents the list of items and item details side-by-side using two
 * vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link SiteListFragment} and the item details (if present) is a
 * {@link SiteDetailFragment}.
 * <p>
 * This activity also implements the required {@link SiteListFragment.Callbacks}
 * interface to listen for item selections.
 */
public class SiteListActivity extends Activity implements SiteListFragment.Callbacks, Callable<Void> {

	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;
	public static SiteListFragment curList = null;
	
	private final int SIGNOUT_INT = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		if(getResources().getBoolean(R.bool.portrait_only)){
			setContentView(R.layout.activity_site_list_ops);
			
	        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	    }
		else
		{
			setContentView(R.layout.activity_site_twopane);
			
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}
		
		if (findViewById(R.id.site_detail_container) != null) {
			mTwoPane = true;
		}
		
		curList = ((SiteListFragment)getFragmentManager().findFragmentById(R.id.site_list));

		AhaCloudDal.setAceUrl(PreferenceManagerHelper.getAceUrlPreferences(this));
		
		if( savedInstanceState == null )
		{	
			DiagTools.setContext(this);
			DiagTools.registerHandler();
			
			if( SitesWrapper.getSites() == null )
			{
				SitesWrapper.loadSitesTask(this, null, this);
			}
			else
			{
				curList.initGroupSiteListAdapter();
			}
			
			curList.initSearchBar("");
		}
		else
		{
			curList.initSearchBar(savedInstanceState.getString("searchText", ""));
		}

		if( mTwoPane )
		{
			showOverviewFragment();
		}
	}
	
	@Override
	protected void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putString("searchText", curList.getSearchText());
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {     
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        return true;
    }

	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_refresh:
				SitesWrapper.loadSitesTask(this, item, this);
				break;
			case R.id.action_logout:
				ConfirmAndLogout();
				break;
			case R.id.action_settings:
				Intent intent = new Intent(this, SettingsActivity.class);
		    	startActivity(intent);
				break;
			case R.id.action_diag_info:
				DiagToolsUI.getDisplayDiagInfo(this);
				break;
		}
        return super.onOptionsItemSelected(item);
    }

	/**
	 * Callback method from {@link SiteListFragment.Callbacks} indicating that
	 * the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(final int groupPosition, final int childPosition) {
		final Site s = (Site)curList.gsla.getChild(groupPosition, childPosition);
		
		if (mTwoPane) {
			showDetailFragment( s, groupPosition, childPosition );
			
		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			final ProgressDialog pDialog = new ProgressDialog( this );
			pDialog.setTitle(R.string.dialogue_loading_sites_title);
			pDialog.setMessage(getResources().getString(R.string.dialogue_loading_sites_message));
			pDialog.setCancelable(false);
			
			final Intent detailIntent = new Intent(this, SiteDetailActivity.class);
			
			detailIntent.putExtra("site", s);
			detailIntent.putExtra("groupPosition", groupPosition);
			detailIntent.putExtra("childPosition", childPosition);

			startActivityForResult( detailIntent, SIGNOUT_INT );
			overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_left);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if(resultCode == SIGNOUT_INT){
	        finish();
	    }
	}
	
	public void showDetailFragment( Site s, int groupPosition, int childPosition )
	{
		SiteDetailFragment fragment = new SiteDetailFragment();
		
		Bundle arguments = new Bundle();
		arguments.putParcelable("site", s);
		arguments.putInt("groupPosition", groupPosition);
		arguments.putInt("childPosition", childPosition);
		fragment.setArguments(arguments);
		
		getFragmentManager().beginTransaction().replace(R.id.site_detail_container, fragment).commit();
	}
	
	public void showOverviewFragment()
	{
		SiteOverviewFragment fragment = new SiteOverviewFragment();
		
		getFragmentManager().beginTransaction().replace(R.id.site_detail_container, fragment).commit();
	}
	
	private void setUserInfoDisplay()
	{
        WelcomeFragment welcomeFragment = new WelcomeFragment();

        getFragmentManager().beginTransaction().replace(R.id.fragment_welcome, welcomeFragment).commit();
	}
	
	@Override
	protected void onResume() {
		super.onResume();

        if( AhaCloudDal.getAhaSession() == null ) {
            //restore the ahasession object
            SessionDAO sessionDAO = new SessionDAO(this);
            sessionDAO.open();

            AhaSession savedSession = sessionDAO.GetLastSession();

            sessionDAO.DeleteSession();
            sessionDAO.close();

            AhaCloudDal.init(this);
            AhaCloudDal.setAhaSession(savedSession);

            //restore the sites
            SitesDAO sitesDAO = new SitesDAO(this);
            sitesDAO.open();

            SitesWrapper.setSites(sitesDAO.GetSavedSites());
            curList.initGroupSiteListAdapter();

            sitesDAO.DeleteSites();
            sitesDAO.close();
        }
		
		setUserInfoDisplay();
	}

    @Override
    protected void onPause() {
        super.onPause();

        //Save the session
        SessionDAO sessionDAO = new SessionDAO(this);
        sessionDAO.open();
        sessionDAO.DeleteSession();
        sessionDAO.SaveSession(AhaCloudDal.getAhaSession());
        sessionDAO.close();

        //Save the sites
        SitesDAO sitesDAO = new SitesDAO(this);
        sitesDAO.open();
        sitesDAO.DeleteSites();
        sitesDAO.SaveSites(SitesWrapper.getSites());
        sitesDAO.close();
    }
	
	private void ConfirmAndLogout()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		builder.setTitle("Sign Out?");
	    builder.setMessage("Are you sure you want to sign out?");

	    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

	        public void onClick(DialogInterface dialog, int which) {
	    		finish();
	            dialog.dismiss();
	        }

	    });

	    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
	        @Override
	        public void onClick(DialogInterface dialog, int which) {
	            dialog.dismiss();
	        }
	    });

	    AlertDialog alert = builder.create();
	    alert.show();
	}
	
	@Override
	public void onBackPressed() {
		ConfirmAndLogout();
	}

	@Override
	public Void call() throws Exception {
		curList.initGroupSiteListAdapter();

		setUserInfoDisplay();
		
		if( mTwoPane )
		{
			showOverviewFragment();
		}
		
		return null;
	}

}
