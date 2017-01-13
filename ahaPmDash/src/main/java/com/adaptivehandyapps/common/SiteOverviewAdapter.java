package com.adaptivehandyapps.common;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.adaptivehandyapps.ahapmdash.R;
import com.adaptivehandyapps.dal.PreferenceManagerHelper;
import com.adaptivehandyapps.entities.ColorPreferences;
import com.adaptivehandyapps.entities.Site;

public class SiteOverviewAdapter extends BaseAdapter{
	
	private Context context;
	private ArrayList<Site> sites;
	
	private ColorPreferences colorPreferences;
	
	public SiteOverviewAdapter(Context context, ArrayList<Site> sites)
	{
		this.context = context;
		this.sites = sites;

        colorPreferences = PreferenceManagerHelper.getColorPreferences(context);
	}

	@Override
	public int getCount() {
		return sites.size();
	}

	@Override
	public Site getItem(int position) {
		return position < sites.size() ? sites.get(position) : null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View overviewView = null;
				
		if( convertView == null )
		{
			overviewView = inflater.inflate(R.layout.overview_list_item, null);
			
			Site site = getItem(position);
			
			if( site != null )
			{
				TextView addressBanner = (TextView)overviewView.findViewById(R.id.txtAddress);
				
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
				
				((ImageView) overviewView.findViewById(R.id.imgHome)).setImageResource(site.getProfilePicture());
				
				OverviewStageListAdapter adapter = new OverviewStageListAdapter( context, site.getOpsStages() );
				
				GridView stageGrid = (GridView)overviewView.findViewById(R.id.siteStageGridView);
				stageGrid.setAdapter(adapter);
			}
		}
		else
		{
			overviewView = convertView;
		}
		
		return overviewView;
	}
}
