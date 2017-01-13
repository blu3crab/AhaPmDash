package com.adaptivehandyapps.common;


import java.math.BigInteger;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.adaptivehandyapps.ahaclouddal.AhaOpsStage;
import com.adaptivehandyapps.ahapmdash.R;
import com.adaptivehandyapps.dal.PreferenceManagerHelper;
import com.adaptivehandyapps.entities.ColorPreferences;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class StageListAdapter extends ArrayAdapter<AhaOpsStage> {
	private final Context context;
	private final ArrayList<AhaOpsStage> values;

    private ColorPreferences colorPreferences;

	public StageListAdapter(Context context, ArrayList<AhaOpsStage> values) {
		super(context, R.layout.stage_list_item, values);
		this.context = context;
		this.values = values;

        colorPreferences = PreferenceManagerHelper.getColorPreferences(context);
	}

	@SuppressLint("SimpleDateFormat")
	private String getDateTimeDisplay(long time)
	{
		SimpleDateFormat curDateFormatter = new SimpleDateFormat("hh:mm a");
		SimpleDateFormat pastDateFormatter = new SimpleDateFormat("MM/dd");

		Date date = new Date(time);

		Calendar pastDate = Calendar.getInstance();
		pastDate.setTimeInMillis( time );

		Calendar curDate = Calendar.getInstance();

		if( curDate.get(Calendar.MONTH) == pastDate.get( Calendar.MONTH ) && 
				curDate.get(Calendar.DATE) == pastDate.get(Calendar.DATE) && 
				curDate.get(Calendar.YEAR) == pastDate.get(Calendar.YEAR) )
		{
			return curDateFormatter.format( date );
		}
		else
		{
			return pastDateFormatter.format( date );
		}
	}

	public void setStageRowDisplay( View rowView, BigInteger time, int color, int imageResource, String stateText )
	{
		TextView stateTextView = (TextView) rowView.findViewById(R.id.txtState);

		TextView timeTextView = (TextView) rowView.findViewById(R.id.txtTime);

		LinearLayout background = (LinearLayout) rowView.findViewById( R.id.LinearLayout1);
		ImageView status = (ImageView) rowView.findViewById( R.id.imgStageState );
		
		if( time.signum() == 1 )
		{
			timeTextView.setText("" + getDateTimeDisplay( time.longValue() * 1000 ) );
		}

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

		View rowView = inflater.inflate(R.layout.stage_list_item, parent, false);

		TextView textView = (TextView) rowView.findViewById(R.id.txtStage);
		textView.setText("" + values.get(position).getStage());

        switch( values.get(position).getOnTrackKpi() )
        {
            case DELAY:
                setStageRowDisplay( rowView, values.get(position).getActualStartTime(), colorPreferences.getAlarmColor(), R.drawable.ic_action_time, "Problem");
                break;
            case RISK:
                setStageRowDisplay( rowView, values.get(position).getActualDoneTime(), colorPreferences.getWarningColor(), R.drawable.ic_action_time, "Risk");
                break;
            case PLAN:
                setStageRowDisplay( rowView, values.get(position).getPlanStartTime(), 0, 0, "Plan");
                break;
            case GOOD:
                setStageRowDisplay( rowView, values.get(position).getActivityKpi() == AhaOpsStage.OpsActivityKPI.DONE ?
                                values.get(position).getActualDoneTime() : values.get(position).getActualStartTime(),
                        colorPreferences.getGoodColor(), R.drawable.ic_action_play, values.get(position).getActivityKpi() == AhaOpsStage.OpsActivityKPI.DONE ? "Done" : "Start");
                break;
        }

		return rowView;
	}
}
