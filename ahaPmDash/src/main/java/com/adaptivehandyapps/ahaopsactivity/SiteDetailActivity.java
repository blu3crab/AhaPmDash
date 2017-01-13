package com.adaptivehandyapps.ahaopsactivity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.adaptivehandyapps.ahaclouddal.AhaCloudDal;
import com.adaptivehandyapps.ahapmdash.R;
import com.adaptivehandyapps.common.DiagToolsUI;
import com.adaptivehandyapps.common.GroupSiteListAdapter;
import com.adaptivehandyapps.entities.Site;
import com.adaptivehandyapps.common.SitesWrapper;

import java.util.concurrent.Callable;


/**
 * An activity representing a single Site detail screen. This activity is only
 * used on handset devices. On tablet-size devices, item details are presented
 * side-by-side with a list of items in a {@link SiteListActivity}.
 * <p>
 * This activity is mostly just a 'shell' activity containing nothing more than
 * a {@link SiteDetailFragment}.
 */
public class SiteDetailActivity extends FragmentActivity implements Callable<Void> {

	private final int SIGNOUT_INT = 1;

	private GroupSiteListAdapter gsla;
	private int groupPosition;
	private int childPosition;
	public SiteDetailFragment fragment;
	private Site site;

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    public WelcomeFragment welcomeFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_site_detail);

		if(getResources().getBoolean(R.bool.portrait_only)){
	        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	    }
		
		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			site = (Site) extras.getParcelable("site");
			groupPosition = extras.getInt("groupPosition");
			childPosition = extras.getInt("childPosition");

			gsla = SiteListActivity.curList.gsla;
		}

        setUserInfoDisplay();

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new SiteDetailPagerAdapter(getFragmentManager());
        mPager.setAdapter(mPagerAdapter);
	}

    private void setUserInfoDisplay()
    {
        welcomeFragment = new WelcomeFragment();

        getFragmentManager().beginTransaction().replace(R.id.fragment_welcome, welcomeFragment).commit();
    }

	public void refreshSiteList( String result )
	{
		fragment.refreshSiteList(result);
	}

	public void refreshSiteList()
	{
		fragment.refreshSiteList();
	}

    public void refreshStagesFromServer()
    {
        SitesWrapper.loadStagesTask(this, null, this, site, welcomeFragment.getProgressBar());
    }

	@Override
	public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        
        outState.putParcelable("site", site);
    }
	

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		
		SiteListActivity.curList.gsla.notifyDataSetChanged();

		overridePendingTransition( R.anim.slide_in_left, R.anim.slide_out_right);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.detail_menu, menu);
		
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_back:
			//TODO: tell the service to kill our token
			setResult(SIGNOUT_INT);
			finish();
			break;
		case R.id.action_refresh:
			SitesWrapper.loadStagesTask(this, item, this, site, welcomeFragment.getProgressBar());
			break;
		case R.id.action_settings:
			Intent intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);
			break;
		case R.id.action_diag_info:
			DiagToolsUI.getDisplayDiagInfo(this);
			break;
		case android.R.id.home:
            NavUtils.navigateUpTo(this, new Intent(this, SiteListActivity.class));
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public Void call() throws Exception {
		refreshSiteList();
		
		return null;
	}

    private class SiteDetailPagerAdapter extends FragmentStatePagerAdapter {
        public SiteDetailPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            int truePosition = childPosition + position;

            if( truePosition > getCount() - 1 )
            {
                truePosition = (childPosition + position) - getCount();
            }

            return SiteDetailFragment.create((Site)gsla.getChild(groupPosition, truePosition));
        }

        @Override
        public int getCount() {
            return gsla.accountChildren.get(gsla.accountHeaders.get(groupPosition)).size();
        }

        @Override
        public void setPrimaryItem (ViewGroup container, int position, Object object)
        {
            fragment = (SiteDetailFragment)object;
        }
    }
}