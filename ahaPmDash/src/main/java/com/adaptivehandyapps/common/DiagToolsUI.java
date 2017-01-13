// Project: AHA PM Data Access Layer
// Contributor(s): Nathan A. Tucker, M.A.Tucker
// Copyright � 2014 Adaptive Handy Apps, LLC.  All Rights Reserved.

package com.adaptivehandyapps.common;

import com.adaptivehandyapps.ahaclouddal.AhaCloudDal;
import com.adaptivehandyapps.ahaclouddal.DiagTools;
import com.adaptivehandyapps.ahapmdash.R;
import com.adaptivehandyapps.dal.PreferenceManagerHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class DiagToolsUI {
	
	public static void getDisplayDiagInfo( final Activity callerContext )
	{
		View view = callerContext.getLayoutInflater().inflate(R.layout.diag_info_detail_dialogue, null);
		
		final EditText etAceUrl = (EditText)view.findViewById(R.id.etAceUrl);
		etAceUrl.setText(AhaCloudDal.getAceUrl());
		
		TextView tvHealth = (TextView)view.findViewById(R.id.tvHealth);
		tvHealth.setText(DiagTools.getDiagInfo());
		
		new AlertDialog.Builder(callerContext)
		.setTitle("Diagnosis Info")
		.setView( view )
		.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String newAceUrl = etAceUrl.getText().toString();
				AhaCloudDal.setAceUrl(newAceUrl);

                PreferenceManagerHelper.setAceUrlPreferences(callerContext, newAceUrl);
				
				dialog.dismiss();
			}
		})
		.show();
	}
}
