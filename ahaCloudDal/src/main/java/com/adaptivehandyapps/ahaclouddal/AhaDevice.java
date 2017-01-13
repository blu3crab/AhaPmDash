// Project: AHA Device IO Service
// Contributer(s): M.A.Tucker
// Origination: MAR 2014
// Copyright © 2014 Adaptive Handy Apps, LLC.  All Rights Reserved.

package com.adaptivehandyapps.ahaclouddal;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class AhaDevice implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String JSON_DEVICES_CONTAINER = "devices";

	@SerializedName("ahaId")			
	private String ahaId;			// unique AHA device id 
	@SerializedName("siteId")			
	private String siteId;			// site AHA Id - id of site containing device
	@SerializedName("serialNumber")	
	private String serialNumber;	// gateway serial number
	@SerializedName("devName")		
	private String devName;			// name
	@SerializedName("devId")		
	private String localId;			// local gateway device id
	@SerializedName("devCategory")	
	private String devCategory;		// device category
	@SerializedName("reserve1")
	private String reserve1;		// reserve

	@SerializedName("active")
	private Integer active;

	public AhaDevice() {
		this.ahaId = AhaDalShare.NULL_MARKER;
		this.siteId = AhaDalShare.NULL_MARKER;
		this.serialNumber = AhaDalShare.NULL_MARKER;
		this.devName = AhaDalShare.NULL_MARKER;
		this.localId = AhaDalShare.NULL_MARKER;
		this.devCategory = AhaDalShare.NULL_MARKER;
		this.reserve1 = AhaDalShare.NULL_MARKER;
		this.active = AhaSettings.OBJ_ACTIVE;
	}

	public AhaDevice(
			String ahaId,
			String siteId,
			String serialNumber,
			String devName,
			String localId,
			String devCategory,
			String reserve1,
			Integer active
			) {
		this.ahaId = ahaId;
		this.siteId = siteId;
		this.serialNumber = serialNumber;
		this.devName = devName;
		this.localId = localId;
		this.devCategory = devCategory;
		this.reserve1 = reserve1;
		this.active = active;
	}

	public String getAhaId() {
		return ahaId;
	}

	public void setAhaId(String ahaId) {
		this.ahaId = ahaId;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getDevName() {
		return devName;
	}

	public void setDevName(String devName) {
		this.devName = devName;
	}

	public String getLocalId() {
		return localId;
	}

	public void setLocalId(String localId) {
		this.localId = localId;
	}

	public String getDevCategory() {
		return devCategory;
	}

	public void setDevCategory(String devCategory) {
		this.devCategory = devCategory;
	}
	public String getReserve1() {
		return reserve1;
	}

	public void setReserve1(String reserve1) {
		this.reserve1 = reserve1;
	}

	public Integer getActive() {
		return active;
	}

	public void setActive(Integer active) {
		this.active = active;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
}