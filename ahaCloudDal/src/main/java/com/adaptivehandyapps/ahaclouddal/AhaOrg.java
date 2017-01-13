// Project: AHA Device IO Service
// Contributor(s): M.A.Tucker
// Origination: MAR 2014
// Copyright © 2014 Adaptive Handy Apps, LLC.  All Rights Reserved.
package com.adaptivehandyapps.ahaclouddal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class AhaOrg implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String JSON_ORGS_CONTAINER = "orgs";

	@SerializedName("ahaId")
	private String ahaId;
	@SerializedName("nickname")
	private String nickname;
	@SerializedName("longname")
	private String longname;
	@SerializedName("address")
	private String address;
	@SerializedName("siteList")
	private List<String> siteList;
	@SerializedName("staffList")
	private List<String> staffList;
	@SerializedName("tagList")
	private List<String> tagList;
	@SerializedName("reserve1")
	private String reserve1;

	@SerializedName("active")
	private Integer active;

	public AhaOrg() {
		this.ahaId = AhaDalShare.NULL_MARKER;
		this.nickname = AhaDalShare.NULL_MARKER;
		this.longname = AhaDalShare.NULL_MARKER;
		this.address = AhaDalShare.NULL_MARKER;
		this.siteList = new ArrayList<String>();
		this.staffList = new ArrayList<String>();
		this.tagList = new ArrayList<String>();
		this.reserve1 = AhaDalShare.NULL_MARKER;
		this.active = AhaDalShare.OBJ_ACTIVE;
	}

	public AhaOrg(
			String ahaId,
			String nickname,
			String longname,
			String address,
			List<String> siteList,
			List<String> staffList,			
			List<String> tagList,
			String reserve1,
			Integer active
			) {
		this.ahaId = ahaId;
		this.nickname = nickname;
		this.longname = longname;
		this.address = address;
		this.siteList = siteList;
		this.staffList = staffList;
		this.tagList = tagList;
		this.reserve1 = reserve1;
		this.active = active;
	}

	/////////////////////////////helpers//////////////////////////////////
	public String toString() {
		return ahaId + ", " + nickname + ", " + longname + ", " + address + ", " + reserve1 + ", " + siteList + ", " + staffList + ", " + tagList + ", " + reserve1 + ", " + active;
	}

	public String getAhaId() {
		return ahaId;
	}

	public void setAhaId(String ahaId) {
		this.ahaId = ahaId;
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

	public List<String> getSiteList() {
		return siteList;
	}

	public void setSiteList(List<String> siteList) {
		this.siteList = siteList;
	}

	public List<String> getStaffList() {
		return staffList;
	}

	public void setStaffList(List<String> staffList) {
		this.staffList = staffList;
	}

	public List<String> getTagList() {
		return tagList;
	}

	public void setTagList(List<String> tagList) {
		this.tagList = tagList;
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