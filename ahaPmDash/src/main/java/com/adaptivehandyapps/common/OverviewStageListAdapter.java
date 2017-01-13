package com.adaptivehandyapps.common;

import java.math.BigInteger;
import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adaptivehandyapps.ahaclouddal.AhaOpsStage;
import com.adaptivehandyapps.ahapmdash.R;
import com.adaptivehandyapps.dal.PreferenceManagerHelper;
import com.adaptivehandyapps.entities.ColorPreferences;

public class OverviewStageListAdapter extends BaseAdapter{

	private Context context;
	private ArrayList<AhaOpsStage> values;
	
	private ColorPreferences colorPreferences;
	
	public OverviewStageListAdapter(Context context, ArrayList<AhaOpsStage> values)
	{
		this.context = context;
		this.values = values;

        colorPreferences = PreferenceManagerHelper.getColorPreferences(context);
	}
	
	@Override
	public int getCount() {
		return values.size();
	}

	@Override
	public AhaOpsStage getItem(int position) {
		return position < values.size() ? values.get(position) : null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	public void setStageRowDisplay( View rowView, BigInteger time, int color, int imageResource, String stateText )
	{
		TextView stateTextView = (TextView) rowView.findViewById(R.id.txtState);

		LinearLayout background = (LinearLayout) rowView.findViewById( R.id.LinearLayout1);
		ImageView status = (ImageView) rowView.findViewById( R.id.imgStageState );

		if( color != 0 )
		{
			background.setBackgroundColor(color);
		}
		
		if( imageResource != 0 )
		{
			status.setImageResource(imageResource);
		}
		else
		{
			status.setImageBitmap( null );
		}
		
		stateTextView.setText(stateText);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View overviewView = null;
		
		if( convertView == null )
		{
			overviewView = inflater.inflate(R.layout.overview_list_stage_item, null);
			
			TextView v = (TextView)overviewView.findViewById(R.id.txtStage);
			v.setText("" + getItem(position).getStage());
			
			switch( values.get(position).getOnTrackKpi() )
			{
			case DELAY:
				setStageRowDisplay( overviewView, values.get(position).getActualStartTime(), colorPreferences.getAlarmColor(), R.drawable.ic_action_time, "Problem");
				break;
			case RISK:
				setStageRowDisplay( overviewView, values.get(position).getActualDoneTime(), colorPreferences.getWarningColor(), R.drawable.ic_action_time, "Risk");
				break;
			case PLAN:
				setStageRowDisplay( overviewView, values.get(position).getPlanStartTime(), 0, 0, "Plan");
				break;
			case GOOD:
				setStageRowDisplay( overviewView, values.get(position).getActivityKpi() == AhaOpsStage.OpsActivityKPI.DONE ? 
						values.get(position).getActualDoneTime() : values.get(position).getActualStartTime(), 
						colorPreferences.getGoodColor(), R.drawable.ic_action_play, values.get(position).getActivityKpi() == AhaOpsStage.OpsActivityKPI.DONE ? "Done" : "Start");
				break;
			}
			
		}
		else
		{
			overviewView = convertView;
		}
		
		return overviewView;
	}

}
