// Project: AHA PM Dashboard
// Contributor(s): Nathan A. Tucker
// Copyright © 2014 Adaptive Handy Apps, LLC.  All Rights Reserved.

package com.adaptivehandyapps.common;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;

import com.adaptivehandyapps.ahapmdash.R;

public class ExpandableListFragment extends Fragment {
	
	public ExpandableListView elv = null;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.expandable_fragment, null);
        elv = (ExpandableListView) v.findViewById(R.id.list);
        elv.setAdapter(new GroupSiteListAdapter(getActivity()));
        
        final EditText txtSearch = (EditText)v.findViewById(R.id.txtSearch);
        ImageButton clearText = (ImageButton)v.findViewById(R.id.clear_txtSearch);
        clearText.setOnClickListener( new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				txtSearch.setText("");
			}
        	
        });
        
        return v;
    }
	
	public void SetExpandableListAdapter( GroupSiteListAdapter gsla )
	{
		if( elv != null )
		{
			elv.setAdapter(gsla);
			for( int i = 0; i < gsla.getGroupCount(); i++ )
			{
				elv.expandGroup(i);
			}
		}
	}
	
}
