// Project: AHA Device IO Service
// Contributor(s): M.A.Tucker
// Origination: MAR 2014
// Copyright © 2014 Adaptive Handy Apps, LLC.  All Rights Reserved.
package com.adaptivehandyapps.ahaclouddal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class AhaStaff implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String JSON_STAFF_CONTAINER = "staff";

	@SerializedName("ahaId")			
	private String ahaId;				// unique AHA staff id 
	@SerializedName("orgId")			
	private String orgId;				// id of org containing staff member
	@SerializedName("nickname")
	private String nickname;			// display nickname
	@SerializedName("longname")
	private String longname;			// formal name
	@SerializedName("role")
	private String role;				// organizational role
	@SerializedName("email")
	private String email;				// email address
	@SerializedName("superEmail")			
	private String superEmail;			// site owner email
	@SerializedName("imagename")
	private String imagename;			// site image name
	@SerializedName("tagList")
	private List<String> tagList;		// tag list
	@SerializedName("reserve1")
	private String reserve1;			// reserve

	@SerializedName("active")
	private Integer active;

	public AhaStaff() {
		this.ahaId = AhaDalShare.NULL_MARKER;
		this.orgId = AhaDalShare.NULL_MARKER;
		this.nickname = AhaDalShare.NULL_MARKER;
		this.longname = AhaDalShare.NULL_MARKER;
		this.role = AhaDalShare.NULL_MARKER;
		this.email = AhaDalShare.NULL_MARKER;
		this.superEmail = AhaDalShare.NULL_MARKER;
		this.imagename = AhaDalShare.NULL_MARKER;
		this.tagList = new ArrayList<String>();
		this.reserve1 = AhaDalShare.NULL_MARKER;
		this.active = AhaDalShare.OBJ_ACTIVE;
	}

	public AhaStaff(
			String ahaId,
			String orgId,
			String nickname,
			String longname,
			String role,
			String email,
			String superEmail,
			String imagename,
			List<String> tagList,
			String reserve1,
			Integer active
			) {
		this.ahaId = ahaId;
		this.orgId = orgId;
		this.nickname = nickname;
		this.longname = longname;
		this.role = role;
		this.email = email;
		this.superEmail = superEmail;
		this.imagename = imagename;
		this.tagList = tagList;
		this.reserve1 = reserve1;
		this.active = active;
	}

	/////////////////////////////helpers//////////////////////////////////
	public String toString() {
		return ahaId + ", " + orgId + ", " + nickname + ", " + longname + ", " + role + ", " + 
				email + ", " + superEmail + ", " + imagename  + ", " + tagList + ", " + reserve1 + ", " + active;
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

	public String getSuperEmail() {
		return superEmail;
	}

	public void setSuperEmail(String superEmail) {
		this.superEmail = superEmail;
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