package com.adaptivehandyapps.ahaopsactivity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.adaptivehandyapps.ahaclouddal.AhaCloudDal;
import com.adaptivehandyapps.ahapmdash.R;
import com.adaptivehandyapps.dal.PreferenceManagerHelper;
import com.adaptivehandyapps.entities.ColorPreferences;
import com.adaptivehandyapps.entities.Site;
import com.adaptivehandyapps.common.SitesWrapper;
import com.adaptivehandyapps.common.StageListAdapter;
/**
 * A fragment representing a single Site detail screen. This fragment is either
 * contained in a {@link SiteListActivity} in two-pane mode (on tablets) or a
 * {@link SiteDetailActivity} on handsets.
 */
public class SiteDetailFragment extends Fragment implements OnItemClickListener {
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	private View rootView;
	private Site site;
	private StageListAdapter adapter;
	private ListView listview;
	private ColorPreferences colorPreferences;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public SiteDetailFragment() {
	}

    public static SiteDetailFragment create(Site s) {
        SiteDetailFragment fragment = new SiteDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("site", s);
        fragment.setArguments(args);
        return fragment;
    }

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (getArguments().containsKey("site")) {
			site = (Site) getArguments().getParcelable("site");
		}

        colorPreferences = PreferenceManagerHelper.getColorPreferences(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.rootView = inflater.inflate(R.layout.fragment_site_detail_ops, container, false);

		if( getActivity() instanceof SiteDetailActivity)
		{
			((SiteDetailActivity)getActivity()).refreshStagesFromServer();
		}
		
		TextView addressBanner = (TextView)rootView.findViewById(R.id.txtAddress);
		
		addressBanner.setText(site.getAddress());
		
		switch( site.getBaseCondition() )
		{
		case ALARM:
			addressBanner.setBackgroundColor( colorPreferences.getAlarmColor() );
			break;
		case GOOD:
			addressBanner.setBackgroundColor( colorPreferences.getGoodColor() );
			break;
		case WARNING:
			addressBanner.setBackgroundColor( colorPreferences.getWarningColor() );
			break;
		default:
			break;
		}
		
		((ImageView) rootView.findViewById(R.id.imgHome)).setImageResource(site.getProfilePicture());

		listview = (ListView) rootView.findViewById(R.id.lvDashItems);

		if( site.getOpsStages() != null )
		{
			adapter = new StageListAdapter( getActivity(), site.getOpsStages() );
			listview.setAdapter(adapter);

			listview.setOnItemClickListener(this);
		}
		
		return this.rootView;
	}
	
	public void refreshSiteList(String result)
	{
		SitesWrapper.refreshStages( result, site );
		
		refreshSiteList();
	}
	
	public void refreshSiteList()
	{
		TextView addressBanner = (TextView)rootView.findViewById(R.id.txtAddress);
		
		switch( site.getBaseCondition() )
		{
		case ALARM:
			addressBanner.setBackgroundColor( colorPreferences.getAlarmColor() );
			break;
		case GOOD:
			addressBanner.setBackgroundColor( colorPreferences.getGoodColor() );
			break;
		case WARNING:
			addressBanner.setBackgroundColor( colorPreferences.getWarningColor() );
			break;
		default:
			break;
		}

		adapter = new StageListAdapter( getActivity(), site.getOpsStages() );
		listview.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		
		FragmentTransaction ft = getFragmentManager().beginTransaction();
	    Fragment prev = getFragmentManager().findFragmentByTag("dialog");
	    if (prev != null) {
	        ft.remove(prev);
	    }
	    ft.addToBackStack(null);

	    SiteDetailChangeDialogFragment newFragment = SiteDetailChangeDialogFragment.newInstance(site.getSiteId(), site.getOpsStages().get(position));
	    newFragment.show(ft, "dialog");
	}
	
}
