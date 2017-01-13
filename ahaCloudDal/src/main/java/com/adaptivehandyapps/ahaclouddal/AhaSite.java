// Project: AHA Device IO Service
// Contributor(s): M.A.Tucker
// Origination: MAR 2014
// Copyright © 2014 Adaptive Handy Apps, LLC.  All Rights Reserved.
package com.adaptivehandyapps.ahaclouddal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class AhaSite implements Comparable<AhaSite>, Serializable {

	private static final long serialVersionUID = 1L;

	public static final String JSON_SITES_CONTAINER = "sites";

	@SerializedName("ahaId")			
	private String ahaId;				// unique AHA site id 
	@SerializedName("orgId")			
	private String orgId;				// Org AHA Id - id of org containing site
	@SerializedName("nickname")			 
	private String nickname;			// gateway reported name - idents site	 
	@SerializedName("longname")			
	private String longname;			// formal name or imported info: name, location
	@SerializedName("address")			
	private String address;				// site address
	@SerializedName("location")			
	private String location;			// site lat/lon "39.547838,-79.351205"
	@SerializedName("ownerEmail")			
	private String ownerEmail;			// site owner email
	@SerializedName("ownerPinCount")			
	private Integer ownerPinCount;		// # PINs for owner's use
	@SerializedName("imagename")
	private String imagename;			// site image name
	@SerializedName("tagList")
	private List<String> tagList;		// tag list

	@SerializedName("account")			
	private String account;				// MIOS account used to fetch the site data
	@SerializedName("serialNumber")		
	private String serialNumber;		// gateway serial number
	@SerializedName("firmwareVersion")	
	private String firmwareVersion;		// gateway firmware version
	@SerializedName("tempunits")		
	private String tempunits;			// gateway temperature units (F, C)
	@SerializedName("activeServer")		
	private String activeServer;		// primary MIOS server

	@SerializedName("reserve1")
	private String reserve1;			// reserve

	@SerializedName("active")
	private Integer active;

	public AhaSite() {
		this.ahaId = AhaDalShare.NULL_MARKER;
		this.orgId = AhaDalShare.NULL_MARKER;
		this.nickname = AhaDalShare.NULL_MARKER;
		this.longname = AhaDalShare.NULL_MARKER;
		this.address = AhaDalShare.NULL_MARKER;
		this.location = AhaDalShare.NULL_MARKER;
		this.ownerEmail = AhaDalShare.NULL_MARKER;
		this.ownerPinCount = 1;
		this.imagename = AhaDalShare.NULL_MARKER;
		this.tagList = new ArrayList<String>();
		this.account = AhaDalShare.NULL_MARKER;
		this.serialNumber = AhaDalShare.NULL_MARKER;
		this.firmwareVersion = AhaDalShare.NULL_MARKER;
		this.tempunits = AhaDalShare.NULL_MARKER;
		this.activeServer = AhaDalShare.NULL_MARKER;
		this.reserve1 = AhaDalShare.NULL_MARKER;
		this.active = AhaDalShare.OBJ_ACTIVE;

	}

	public AhaSite(
			String ahaId,
			String orgId,
			String nickname,
			String longname,
			String address,
			String location,
			String ownerEmail,
			Integer ownerPinCount,
			String imagename,
			List<String> tagList,
			String account, 
			String serialNumber,
			String firmwareVersion,
			String tempunits,
			String activeServer,
			String reserve1,
			Integer active
			) {
		this.ahaId = ahaId;
		this.orgId = orgId;
		this.nickname = nickname;
		this.longname = longname;
		this.address = address;
		this.location = location;
		this.ownerEmail = ownerEmail;
		this.ownerPinCount = ownerPinCount;
		this.imagename = imagename;
		this.tagList = tagList;
		this.account = account;
		this.serialNumber = serialNumber;
		this.firmwareVersion = firmwareVersion;
		this.tempunits = tempunits;
		this.activeServer = activeServer;
		this.reserve1 = reserve1;
		this.active = active;
	}

	/////////////////////////////helpers//////////////////////////////////
	public String toString() {
		return ahaId + ", " + orgId + ", " + nickname + ", " + longname + ", " + address + ", " + 
				location + ", " + ownerEmail + ", " + ownerPinCount + ", " + imagename + ", " + tagList + ", " 
				+ account + ", " + serialNumber + ", " + firmwareVersion + ", " + tempunits + ", " + activeServer
				+ ", " + reserve1 + ", " + active;
	}
	///////////////////////////uncommon fun/////////////////////////////
	@Override
	public int compareTo(AhaSite another) {
		return this.nickname.compareTo( another.nickname );
	}
	/////////////////////////////setters/getters//////////////////////////////////
	public String getAhaId() {
		return ahaId;
	}

	public void setAhaId(String ahaId) {
		this.ahaId = ahaId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getLongname() {
		return longname;
	}

	public void setLongname(String longname) {
		this.longname = longname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getOwnerEmail() {
		return ownerEmail;
	}

	public void setOwnerEmail(String ownerEmail) {
		this.ownerEmail = ownerEmail;
	}

	public Integer getOwnerPinCount() {
		return ownerPinCount;
	}

	public void setOwnerPinCount(Integer ownerPinCount) {
		this.ownerPinCount = ownerPinCount;
	}

	public String getImagename() {
		return imagename;
	}

	public void setImagename(String imagename) {
		this.imagename = imagename;
	}

	public List<String> getTagList() {
		return tagList;
	}

	public void setTagList(List<String> tagList) {
		this.tagList = tagList;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getFirmwareVersion() {
		return firmwareVersion;
	}

	public void setFirmwareVersion(String firmwareVersion) {
		this.firmwareVersion = firmwareVersion;
	}

	public String getTempUnits() {
		return tempunits;
	}

	public void setTempUnits(String tempunits) {
		this.tempunits = tempunits;
	}

	public String getActiveServer() {
		return activeServer;
	}

	public void setActiveServer(String activeServer) {
		this.activeServer = activeServer;
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
