package com.adaptivehandyapps.ahaclouddal;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;


public class AhaSiteWrapper {
	@SerializedName("sites")
	public ArrayList<AhaSite> sites;
	
	public AhaSiteWrapper()
	{
		
	}
}
