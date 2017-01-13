// Project: AHA PM Dashboard
// Contributor(s): Nathan A. Tucker
// Copyright � 2014 Adaptive Handy Apps, LLC.  All Rights Reserved.

package com.adaptivehandyapps.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.adaptivehandyapps.ahapmdash.R;
import com.adaptivehandyapps.dal.PreferenceManagerHelper;
import com.adaptivehandyapps.entities.ColorPreferences;
import com.adaptivehandyapps.entities.Site;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class GroupSiteListAdapter extends BaseExpandableListAdapter implements Filterable {

	private transient Context context;
	public List<String> accountHeaders; 
	public HashMap<String, List<Site>> accountChildren;
	public HashMap<String, List<Site>> accountChildrenFiltered;

	private ColorPreferences colorPreferences;

	public GroupSiteListAdapter(Context c) 
	{
		context = c;
		accountHeaders = new ArrayList<String>();
		accountChildren = new HashMap<String, List<Site>>();
		accountChildrenFiltered = null;

        colorPreferences = PreferenceManagerHelper.getColorPreferences(context);
	}

	@Override
	public Object getChild(int groupPosition, int childPosititon) {
		if( accountChildrenFiltered == null || accountChildrenFiltered.size() == 0 )
		{
			return this.accountChildren.get(this.accountHeaders.get(groupPosition)).get(childPosititon);
		}
		else
		{
			return this.accountChildrenFiltered.get(this.accountHeaders.get(groupPosition)).get(childPosititon);
		}
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		final String childAddress = ((Site)getChild(groupPosition, childPosition)).getAddress();
		final String childDescription = ((Site)getChild(groupPosition, childPosition)).getDetailedCondition();
		final int childPicture = ((Site)getChild(groupPosition, childPosition)).getProfilePicture();
		final Site.Condition c = ((Site)getChild(groupPosition, childPosition)).getBaseCondition();
		
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.site_list_item, null);
		}

		TextView txtListChildAddress = (TextView) convertView.findViewById(R.id.title);
		txtListChildAddress.setText(childAddress);
		
		TextView txtListChildCond = (TextView) convertView.findViewById(R.id.description);
		txtListChildCond.setText(childDescription);
		
		RelativeLayout background = (RelativeLayout) convertView.findViewById( R.id.itemBackground );
		ImageView iListChildCondition = (ImageView) convertView.findViewById(R.id.basicState);
		//TODO: get other resources and implement them
		switch( c )
		{
			case GOOD:
				iListChildCondition.setImageResource(R.drawable.ic_action_accept);
				background.setBackgroundColor(colorPreferences.getGoodColor());
				break;
			case WARNING:
				iListChildCondition.setImageResource(R.drawable.ic_action_warning);
				background.setBackgroundColor(colorPreferences.getWarningColor());
				break;
			case ALARM:
				iListChildCondition.setImageResource(R.drawable.ic_action_backspace);
				background.setBackgroundColor(colorPreferences.getAlarmColor());
				break;
			case NOFEED:
				iListChildCondition.setImageBitmap( null );
				background.setBackgroundColor(0x00000000);
				break;
		}
		
		ImageView iListChildPicture = (ImageView) convertView.findViewById(R.id.imageAccount);
		iListChildPicture.setImageResource( childPicture ); 
		
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		if( accountChildrenFiltered == null || accountChildrenFiltered.size() == 0 )
		{
			if( this.accountChildren != null && groupPosition < this.accountHeaders.size())
			{
				return this.accountChildren.get(this.accountHeaders.get(groupPosition)).size();
			}
			else
			{
				return 0;
			}
		}
		else
		{
			if( this.accountChildrenFiltered != null && groupPosition < this.accountHeaders.size())
			{
				return this.accountChildrenFiltered.get(this.accountHeaders.get(groupPosition)).size();
			}
			else
			{
				return 0;
			}
		}
	}

	@Override
	public Object getGroup(int groupPosition) {
		return this.accountHeaders.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return this.accountHeaders.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		String headerTitle = (String) getGroup(groupPosition);
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.group_header, null);
		}

		TextView lblListHeader = (TextView) convertView.findViewById(R.id.title);
		//lblListHeader.setTypeface(null, Typeface.BOLD);
		lblListHeader.setText(headerTitle);

		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	/* For now we are only releasing in America */
	@SuppressLint("DefaultLocale")
	@Override
	public Filter getFilter() {
		return new Filter() {
			
			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				FilterResults results = new FilterResults();
				HashMap<String, List<Site>> siteResults = new HashMap<String, List<Site>>();
				
				if( constraint != null && constraint.toString().length() > 0 )
				{
					for( String curAccount : accountHeaders)
					{
						List<Site> curSites = accountChildren.get( curAccount );
						List<Site> returnSites = new ArrayList<Site>();
						for( Site s : curSites )
						{
							if( s.getAddress().toLowerCase().contains(constraint.toString().toLowerCase()))
							{
								returnSites.add(s);
							}
						}
						
						siteResults.put(curAccount, returnSites);
					}
				}
				
				results.values = siteResults;
				return results;
			}

			/* We need to be careful about how we cast this stuff, but since we are the only class controlling it, it should be fine */
			@SuppressWarnings("unchecked")
			@Override
			protected void publishResults(CharSequence constraint, FilterResults results) {
				accountChildrenFiltered = (HashMap<String, List<Site>>) results.values;
				notifyDataSetChanged();
			}
			
		};
	}

}
