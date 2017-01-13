// Project: AHA Device IO Service
// Contributor(s): M.A.Tucker
// Origination: MAR 2014
// Copyright © 2014 Adaptive Handy Apps, LLC.  All Rights Reserved.
package com.adaptivehandyapps.ahaclouddal;

import java.io.Serializable;
import java.math.BigInteger;

import com.google.gson.annotations.SerializedName;

public class AhaSession implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String JSON_SESSION_CONTAINER = "session";

	@SerializedName("orgId")			
	private String orgId;				// id of org containing staff member
	@SerializedName("staffId")			
	private String staffId;				// unique AHA staff id 
	@SerializedName("role")			
	private String role;				// staff role 
	@SerializedName("email")
	private String email;				// email address
	@SerializedName("ipaddress")
	private String ipaddress;			// IP address
	@SerializedName("registered")			
	private Boolean registered;			// registered?
	@SerializedName("token")			
	private String token;				// token placeholder
	@SerializedName("starttime")
	private BigInteger starttime;		// session start time
	@SerializedName("lasttime")
	private BigInteger lasttime;		// last request time

	@SerializedName("active")
	private Integer active;

	public AhaSession() {
		this.orgId = AhaDalShare.NULL_MARKER;
		this.staffId = AhaDalShare.NULL_MARKER;
		this.role = AhaDalShare.NULL_MARKER;
		this.email = AhaDalShare.NULL_MARKER;
		this.ipaddress = AhaDalShare.NULL_MARKER;
		this.registered = false;
		this.token = AhaDalShare.NULL_MARKER;
		this.starttime = BigInteger.valueOf(0);
		this.lasttime = BigInteger.valueOf(0);
		this.active = AhaDalShare.OBJ_ACTIVE;
	}

	public AhaSession(
			String orgId,
			String staffId,
			String role,
			String email,
			String ipaddress,
			Boolean registered,
			String token,
			BigInteger starttime,
			BigInteger lasttime,
			Integer active
			) {
		this.orgId = orgId;
		this.staffId = staffId;
		this.role = role;
		this.email = email;
		this.email = ipaddress;
		this.registered = registered;
		this.token = token;
		this.starttime = starttime;
		this.lasttime = lasttime;
		this.active = active;
	}


	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public Boolean getRegistered() {
		return registered;
	}

	public void setRegistered(Boolean registered) {
		this.registered = registered;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public BigInteger getStarttime() {
		return starttime;
	}

	public void setStarttime(BigInteger starttime) {
		this.starttime = starttime;
	}

	public BigInteger getLasttime() {
		return lasttime;
	}

	public void setLasttime(BigInteger lasttime) {
		this.lasttime = lasttime;
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