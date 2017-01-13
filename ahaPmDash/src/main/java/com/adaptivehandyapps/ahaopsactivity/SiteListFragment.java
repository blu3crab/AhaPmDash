package com.adaptivehandyapps.ahaopsactivity;

import java.util.ArrayList;

import com.adaptivehandyapps.ahapmdash.R;
import com.adaptivehandyapps.common.ExpandableListFragment;
import com.adaptivehandyapps.common.GroupSiteListAdapter;
import com.adaptivehandyapps.entities.Site;
import com.adaptivehandyapps.common.SitesWrapper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageButton;
import android.widget.ListView;

public class SiteListFragment extends ExpandableListFragment implements OnChildClickListener {

	public GroupSiteListAdapter gsla;
	private static final String STATE_ACTIVATED_POSITION = "activated_position";
	private Callbacks mCallbacks = sDummyCallbacks;
	private int mActivatedPosition = ListView.INVALID_POSITION;

	ProgressDialog pDialogNetwork;

	public interface Callbacks {
		/**
		 * Callback for when an item has been selected.
		 */
		public void onItemSelected(int groupPosition, int childPosition);
	}

	private static Callbacks sDummyCallbacks = new Callbacks() {
		@Override
		public void onItemSelected(int groupPosition, int childPosition) {
		}
	};

	public SiteListFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		pDialogNetwork = new ProgressDialog( getActivity() );
	}

	public String getSearchText()
	{
		EditText searchBar = (EditText) getActivity().findViewById( R.id.txtSearch );
		return searchBar.getText().toString();
	}

	public void initSearchBar(String startText)
	{
		final ImageButton clear_searchBar = (ImageButton) getActivity().findViewById( R.id.clear_txtSearch );
		EditText searchBar = (EditText) getActivity().findViewById( R.id.txtSearch );
		searchBar.setText(startText);

		if( startText != null && !startText.isEmpty())
		{
			gsla.getFilter().filter(startText);
		}

		if( searchBar.getText().length() > 0 )
		{
			clear_searchBar.setVisibility(View.VISIBLE);
		}

		searchBar.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                if( gsla != null ) {
                    gsla.getFilter().filter(cs);
                }
				if( cs.length() > 0 )
				{
					clear_searchBar.setVisibility(View.VISIBLE);
				}
				else
				{
					clear_searchBar.setVisibility(View.INVISIBLE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {

			}
		});
	}

	public void initGroupSiteListAdapter()
	{
		gsla = new GroupSiteListAdapter( getActivity() );

		for( Site s: SitesWrapper.getSites() )
		{
			if( !gsla.accountHeaders.contains(s.getAccountName()) )
			{
				gsla.accountHeaders.add(s.getAccountName());
				gsla.accountChildren.put(s.getAccountName(), new ArrayList<Site>());
			}

			ArrayList<Site> tempSites = (ArrayList<Site>) gsla.accountChildren.get(s.getAccountName());
			tempSites.add(s);

			gsla.accountChildren.put(s.getAccountName(), tempSites);
			
			/*for( String tag: s.getTagList() )
			{
				if( !gsla.accountHeaders.contains(tag) )
				{
					gsla.accountHeaders.add(tag);
					gsla.accountChildren.put(tag, new ArrayList<Site>());
				}

				ArrayList<Site> tempSites = (ArrayList<Site>) gsla.accountChildren.get(tag);
				tempSites.add(s);

				gsla.accountChildren.put(tag, tempSites);
			}*/
		}

		super.SetExpandableListAdapter(gsla);
		super.elv.setOnChildClickListener(this);

	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// Activities containing this fragment must implement its callbacks.
		if (!(activity instanceof Callbacks)) {
			throw new IllegalStateException(
					"Activity must implement fragment's callbacks.");
		}

		mCallbacks = (Callbacks) activity;
	}

	@Override
	public void onDetach() {
		super.onDetach();

		// Reset the active callbacks interface to the dummy implementation.
		mCallbacks = sDummyCallbacks;
	}


	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (mActivatedPosition != ListView.INVALID_POSITION) {
			// Serialize and persist the activated item position.
			outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
		}
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
			int childPosition, long id) {
		mCallbacks.onItemSelected(groupPosition, childPosition);
		return false;
	}

}
