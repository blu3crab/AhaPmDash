package com.adaptivehandyapps.ahaopsactivity;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.adaptivehandyapps.ahaclouddal.AhaCloudDal;
import com.adaptivehandyapps.ahapmdash.R;
import com.adaptivehandyapps.common.SitesWrapper;
import com.adaptivehandyapps.entities.Site;

import java.util.ArrayList;

/**
 * Created by CowGod on 12/15/2014.
 */
public class WelcomeFragment extends Fragment {

    private View rootView;

    public WelcomeFragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_welcome, container, false);

        ImageView iv = (ImageView)rootView.findViewById(R.id.imgStageState);
        ArrayList<Site> sites = SitesWrapper.getSites();

        if (!sites.isEmpty()) {
            Site s = sites.get(0);
            iv.setImageResource(s.getAccountPicture());
        }

        TextView txtLoginInfo = (TextView)rootView.findViewById(R.id.txtLoginInfo);

        txtLoginInfo.setText( "Welcome, " + AhaCloudDal.getAhaSession().getEmail() + " " );

        return this.rootView;
    }

    public ProgressBar getProgressBar()
    {
        if( this.rootView == null )
            return null;

        return (ProgressBar)this.rootView.findViewById(R.id.refreshProgressBar);
    }

}
