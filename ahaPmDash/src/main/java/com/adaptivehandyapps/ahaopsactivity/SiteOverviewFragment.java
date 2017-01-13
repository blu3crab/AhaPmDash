package com.adaptivehandyapps.ahaopsactivity;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import com.adaptivehandyapps.ahapmdash.R;
import com.adaptivehandyapps.common.SiteOverviewAdapter;
import com.adaptivehandyapps.common.SitesWrapper;
import com.adaptivehandyapps.entities.Site;

import java.util.ArrayList;

public class SiteOverviewFragment extends Fragment {
	
	public SiteOverviewFragment()
	{
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_overview, container, false);
		GridView overviewGrid = (GridView)rootView.findViewById(R.id.overviewGrid);

        ImageView iv = (ImageView)rootView.findViewById(R.id.imgAccountOverview);
        ArrayList<Site> sites = SitesWrapper.getSites();

        if (!sites.isEmpty()) {
            Site s = sites.get(0);
            iv.setImageResource(s.getAccountPicture());
        }

        SiteOverviewAdapter adapter = new SiteOverviewAdapter( getActivity(), SitesWrapper.getSites());
		overviewGrid.setAdapter(adapter);
		
		return rootView;
	}
}
