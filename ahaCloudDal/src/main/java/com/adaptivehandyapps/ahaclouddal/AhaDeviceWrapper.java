// Project: AHA PM Admin
// Contributor(s): M.A.Tucker
// Copyright © 2014 Adaptive Handy Apps, LLC.  All Rights Reserved.
package com.adaptivehandyapps.ahaclouddal;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;


public class AhaDeviceWrapper {
	@SerializedName("devices")
	public ArrayList<AhaDevice> devices;
	
	public AhaDeviceWrapper()
	{
		
	}
}
