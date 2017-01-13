package com.adaptivehandyapps.ahaopsactivity;


import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.adaptivehandyapps.ahaclouddal.AhaCloudDal;
import com.adaptivehandyapps.ahaclouddal.AhaOpsStage;
import com.adaptivehandyapps.ahaclouddal.AhaOpsStage.OpsActivityKPI;
import com.adaptivehandyapps.ahaclouddal.AhaOpsStage.OpsStage;
import com.adaptivehandyapps.ahapmdash.R;
import com.adaptivehandyapps.common.RoleHelper;

public class SiteDetailChangeDialogFragment extends DialogFragment implements OnCheckedChangeListener, OnClickListener {
	
	private String mSerial;
	private AhaOpsStage mStage;
	private View rootView;
	
	private Bitmap mPhoto;
	
	static SiteDetailChangeDialogFragment newInstance( String serial, AhaOpsStage stage ) {
		SiteDetailChangeDialogFragment f = new SiteDetailChangeDialogFragment();

        Bundle args = new Bundle();
        args.putString( "serial", serial );
        args.putParcelable("stage", stage );
        f.setArguments(args);

        return f;
    }
	
	@SuppressLint("SimpleDateFormat")
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		this.rootView = getActivity().getLayoutInflater().inflate(R.layout.stage_detail_dialogue, null);
		
        TabHost tabs = (TabHost)this.rootView.findViewById(R.id.TabHost01);
		tabs.setup();

		TabHost.TabSpec tab1 = tabs.newTabSpec("tab1");
		tab1.setContent(R.id.overallStateLayout);
		tab1.setIndicator("General");
		tabs.addTab(tab1);

		TabHost.TabSpec tab2 = tabs.newTabSpec("tab2");
		tab2.setContent(R.id.planningLayout);
		tab2.setIndicator("Planning");
		tabs.addTab(tab2);
		
		TabHost.TabSpec tab3 = tabs.newTabSpec("tab3");
		tab3.setContent(R.id.pictureLayout);
		tab3.setIndicator("Picture");
		tabs.addTab(tab3);
		
		if (savedInstanceState != null) {
	        mPhoto = savedInstanceState.getParcelable("picture");
		}
		

        ImageView imageView = (ImageView)this.rootView.findViewById(R.id.imageView1);
		
		if( mPhoto != null )
		{
			tabs.setCurrentTab(2);

            imageView.setImageBitmap(mPhoto);
		}
		
		EditText noteField = (EditText)this.rootView.findViewById(R.id.eTxtNotes);
		noteField.setHint( mStage.getNote() );
        
		ToggleButton startButton = (ToggleButton)this.rootView.findViewById(R.id.toggleStartButton);
		ToggleButton blockedButton = (ToggleButton)this.rootView.findViewById(R.id.toggleBlockedButton);
		ToggleButton doneButton = (ToggleButton)this.rootView.findViewById(R.id.toggleDoneButton);
		ToggleButton extraPayButton = (ToggleButton)this.rootView.findViewById(R.id.toggleExtraPayButton);
		
		startButton.setOnCheckedChangeListener(this);
		blockedButton.setOnCheckedChangeListener(this);
		doneButton.setOnCheckedChangeListener(this);
		extraPayButton.setOnCheckedChangeListener(this);
		
		if( RoleHelper.hasAdvancedRights(AhaCloudDal.getAhaSession().getRole()) )
		{
			blockedButton.setVisibility( View.VISIBLE );
			doneButton.setVisibility( View.VISIBLE );
			
			if( mStage.getActivityKpi() == OpsActivityKPI.INACTIVE){
				startButton.setVisibility( View.VISIBLE );
			}
		}
		else
		{
			switch( mStage.getActivityKpi() )
			{
			case BLOCK:
				doneButton.setVisibility( View.VISIBLE );
				break;
			case DONE:
				break;
			case INACTIVE:
				startButton.setVisibility( View.VISIBLE );
				break;
			case START:
				blockedButton.setVisibility( View.VISIBLE );
				doneButton.setVisibility( View.VISIBLE );
				break;
			}
		}
		
		if( mStage.getStage() == OpsStage.LINEN )
		{
			startButton.setVisibility(View.GONE);
			
			if( mStage.getActivityKpi() != OpsActivityKPI.DONE )
				doneButton.setVisibility(View.VISIBLE);
			
			doneButton.setText("DELIVERED?");
			doneButton.setTextOn("DELIVERED!");
			doneButton.setTextOff("DELIVERED?");
		}
		else if( mStage.getStage() == OpsStage.CLEAN )
		{
			extraPayButton.setVisibility(View.VISIBLE);
		}
		
		if( !RoleHelper.hasBasicRights(AhaCloudDal.getAhaSession().getRole(), mStage.getStage()) )
		{
			noteField.setEnabled( false );
			
			blockedButton.setVisibility( View.GONE );
			doneButton.setVisibility( View.GONE );
			startButton.setVisibility( View.GONE );
			extraPayButton.setVisibility(View.GONE);
		}
		
		TextView plannedStartTime = (TextView)this.rootView.findViewById(R.id.txtPlannedStart);
		TextView actualStartTime = (TextView)this.rootView.findViewById(R.id.txtActualStart);
		TextView plannedDoneTime = (TextView)this.rootView.findViewById(R.id.txtPlannedDone);
		TextView actualDoneTime = (TextView)this.rootView.findViewById(R.id.txtActualDone);
		
		SimpleDateFormat curDateFormatter = new SimpleDateFormat("hh:mm a MM/dd");
		
		plannedStartTime.setText( mStage.getPlanStartTime().signum() > 0 ? 
				curDateFormatter.format( new Date( mStage.getPlanStartTime().longValue() * 1000)) : "--" );
		actualStartTime.setText( mStage.getActualStartTime().signum() > 0 ?
				curDateFormatter.format( new Date( mStage.getActualStartTime().longValue() * 1000)) : "--" );
		plannedDoneTime.setText( mStage.getPlanDoneTime().signum() > 0 ?
				curDateFormatter.format( new Date( mStage.getPlanDoneTime().longValue() * 1000)) : "--" );
		actualDoneTime.setText( mStage.getActualDoneTime().signum() > 0 ? 
				curDateFormatter.format( new Date( mStage.getActualDoneTime().longValue() * 1000)) : "--" );
		
		imageView.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
                startActivityForResult(cameraIntent, 1); 
            }
            
        });

        String plannedActivityName = mStage.getOnTrackKpi().toString();
        if( plannedActivityName.equals("DELAY"))
            plannedActivityName = "PROBLEM";

		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
	    .setView(this.rootView)
        .setTitle(mStage.getStage().toString() + " (" + plannedActivityName + ")")
        .setNegativeButton(android.R.string.cancel,
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                	dialog.dismiss();
                }
            }
        );
        
        if( RoleHelper.hasBasicRights(AhaCloudDal.getAhaSession().getRole(), mStage.getStage()) )
        	builder.setPositiveButton(R.string.dialogue_save, this);
        
	    return builder.create();
	}
	
    @Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	
    	if (requestCode == 1 && data != null ) {  
            mPhoto = (Bitmap) data.getExtras().get("data"); 
            
            ImageView imageView = (ImageView)this.rootView.findViewById(R.id.imageView1);
            imageView.setImageBitmap(mPhoto);
        }  
    	
	}
    
    @Override
	public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        
        outState.putParcelable("picture", mPhoto);
    }

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mSerial = getArguments().getString("serial");
        mStage = (AhaOpsStage) getArguments().getParcelable("stage");
    }

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		ToggleButton startButton = (ToggleButton)this.rootView.findViewById(R.id.toggleStartButton);
		ToggleButton blockedButton = (ToggleButton)this.rootView.findViewById(R.id.toggleBlockedButton);
		ToggleButton doneButton = (ToggleButton)this.rootView.findViewById(R.id.toggleDoneButton);
		ToggleButton extraPayButton = (ToggleButton)this.rootView.findViewById(R.id.toggleExtraPayButton);
		
		blockedButton.setChecked(false);
		doneButton.setChecked( false );
		startButton.setChecked(false);
		extraPayButton.setChecked(false);
		
		buttonView.setChecked( isChecked );
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		
		ToggleButton startButton = (ToggleButton)this.rootView.findViewById(R.id.toggleStartButton);
		ToggleButton blockedButton = (ToggleButton)this.rootView.findViewById(R.id.toggleBlockedButton);
		ToggleButton doneButton = (ToggleButton)this.rootView.findViewById(R.id.toggleDoneButton);
		ToggleButton extraPayButton = (ToggleButton)this.rootView.findViewById(R.id.toggleExtraPayButton);
		
		EditText noteField = (EditText)this.rootView.findViewById(R.id.eTxtNotes);

		// capture time
		Calendar curDate = Calendar.getInstance();	
		Long time = curDate.getTimeInMillis() / 1000;		

		final AhaOpsStage ahaOpsStage = new AhaOpsStage();
		ahaOpsStage.setSiteId(mSerial);
		ahaOpsStage.setStage(mStage.getStage());
		ahaOpsStage.setNote(noteField.getText().toString());

		if( doneButton.isChecked() )
		{
			ahaOpsStage.setActivityKpi(OpsActivityKPI.DONE);
			ahaOpsStage.setActualDoneTime(BigInteger.valueOf(time));
		}
		else if ( startButton.isChecked() )
		{
			ahaOpsStage.setActivityKpi(OpsActivityKPI.START);
			ahaOpsStage.setActualStartTime(BigInteger.valueOf(time));
		}
		else if( blockedButton.isChecked() )
		{
			ahaOpsStage.setActivityKpi(OpsActivityKPI.BLOCK);
		}
		// TODO: add PROBLEM to activity enum?
//		else if( extraPayButton.isChecked() )
//		{
//			activity = "EXTRAPAY";
//		}
		else
		{
			ahaOpsStage.setActivityKpi(mStage.getActivityKpi());
		}
		
		
		/*String result = AhaCloudDal.updateOpsStage(AhaCloudDal.getAhaSession().getOrgId(), ahaOpsStage);
		
		((SiteDetailActivity)getActivity()).refreshSiteList( result );*/
		
		final SiteDetailActivity parentActivity = (SiteDetailActivity)getActivity();
		
		AsyncTask<Void, String, String > task = new AsyncTask<Void, String, String>(){
			@Override
			protected String doInBackground(Void... params) {
				// TODO: performance - load specific site
				String result = AhaCloudDal.updateOpsStage(AhaCloudDal.getAhaSession().getOrgId(), ahaOpsStage);
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				return result;
			}

			@Override
			protected void onPostExecute(String result){
				if( parentActivity.welcomeFragment.getProgressBar() != null )
					parentActivity.welcomeFragment.getProgressBar().setVisibility(View.GONE);
				
				parentActivity.refreshSiteList( result );
			}
			
			@Override
			protected void onPreExecute(){
				if( parentActivity.welcomeFragment.getProgressBar() != null )
					parentActivity.welcomeFragment.getProgressBar().setVisibility(View.VISIBLE);
			}
		};

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void[])null);
		else
			task.execute((Void[])null);

		dialog.dismiss();
	}


}
