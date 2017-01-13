// Project: AHA Device IO Service
// Contributer(s): M.A.Tucker
// Origination: MAR 2014
// Copyright � Adaptive Handy Apps, LLC.  All Rights Reserved.

package com.adaptivehandyapps.ahaclouddal;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigInteger;

import com.google.gson.annotations.SerializedName;

public class AhaOpsStage implements Parcelable {

	public static final String JSON_OPSSTAGE_CONTAINER = "ahaOpsStage";

	// OPS Stages
	public enum OpsStage {
		CHECKOUT (1),			// checkout date/time
		LINEN (2),				// linen delivery
		CLEAN (3),				// cleaning
		HOTTUB (4),				// hottub maint
		MAINT (5),				// regular or exceptional maint
		INSPECT (6),			// inspection
		RFO (7),				//  RFO
		CHECKIN (8);			// checkin date/time
		
        public int value;
        OpsStage(int value) {this.value = value;}
	}
	// OPS OnTrackKPI
	public enum OpsOnTrackKPI {
		@SerializedName("PLAN")
		PLAN (1),		// estimated completion
		@SerializedName("GOOD")
		GOOD (2),		// RFO on complete or on track
		@SerializedName("RISK")
		RISK (3),		// places RFO at risk
		@SerializedName("DELAY")
		DELAY (4);		// delays RFO
		
        public int value;
		OpsOnTrackKPI(int value) {this.value = value;}
	}

	// OPS ActivityKPI
	public enum OpsActivityKPI {
		@SerializedName("INACTIVE")
		INACTIVE (1),		// activity not started
		@SerializedName("START")
		START (2),			// activity started
		@SerializedName("BLOCK")
		BLOCK (3),			// activity blocked
		@SerializedName("DONE")
		DONE (4);			// activity complete
 
		public int value;
		OpsActivityKPI(int value) {this.value = value;}
	}
	
	// site id 
	@SerializedName("siteId")
	private String siteId;
	// stage 
	@SerializedName("stage")
	private String stage;
	// KPI 
	@SerializedName("onTrackKpi")
//	private OpsOnTrackKPI onTrackKpi;
	private String onTrackKpi;
	// KPI 
	@SerializedName("activityKpi")
//	private OpsActivityKPI activityKpi;
	private String activityKpi;
	// plan start time
	@SerializedName("planStartTime")
	private BigInteger planStartTime;
	// actual start time
	@SerializedName("actualStartTime")
	private BigInteger actualStartTime;
	// plan done time
	@SerializedName("planDoneTime")
	private BigInteger planDoneTime;
	// actual done time
	@SerializedName("actualDoneTime")
	private BigInteger actualDoneTime;
	// TODO: convert String to Text
	// note
	@SerializedName("note")
	private String note;
	@SerializedName("reserve1")
	private String reserve1;			// reserve

	@SerializedName("active")
	private Integer active;
	
	// constructor
	public AhaOpsStage () {
		this.siteId = AhaDalShare.NULL_MARKER;
		this.stage = AhaDalShare.NULL_MARKER;
		this.onTrackKpi = OpsOnTrackKPI.PLAN.toString();
		this.activityKpi = OpsActivityKPI.INACTIVE.toString();
		this.planStartTime = BigInteger.valueOf(0);
		this.actualStartTime = BigInteger.valueOf(0);
		this.planDoneTime = BigInteger.valueOf(0);
		this.actualDoneTime = BigInteger.valueOf(0);
		this.note = AhaDalShare.NULL_MARKER;
		this.reserve1 = AhaDalShare.NULL_MARKER;
		this.active = AhaDalShare.OBJ_ACTIVE;
	}
	public AhaOpsStage (
			String siteId,
			OpsStage stage,
			OpsOnTrackKPI onTrackKpi,
			OpsActivityKPI activityKpi,
			BigInteger planStartTime,
			BigInteger actualStartTime,
			BigInteger planDoneTime,
			BigInteger actualDoneTime,
			String note, 
			String reserve1,
			Integer active
			) {
		this.siteId = siteId;
		this.stage = stage.toString();
		this.onTrackKpi = onTrackKpi.toString();
		this.activityKpi = activityKpi.toString();
		this.planStartTime = planStartTime;
		this.actualStartTime = actualStartTime;
		this.planDoneTime = planDoneTime;
		this.actualDoneTime = actualDoneTime;
		this.note = note;
		this.reserve1 = reserve1;
		this.active = active;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public OpsStage getStage() {
		return OpsStage.valueOf(stage);
	}

	public void setStage(OpsStage stage) {
		this.stage = stage.toString();
	}

	public OpsOnTrackKPI getOnTrackKpi() {
		return OpsOnTrackKPI.valueOf(onTrackKpi);
	}

	public void setOnTrackKpi(OpsOnTrackKPI onTrackKpi) {
		this.onTrackKpi = onTrackKpi.toString();
	}

	public OpsActivityKPI getActivityKpi() {
		return OpsActivityKPI.valueOf(activityKpi);
	}

	public void setActivityKpi(OpsActivityKPI activityKpi) {
		this.activityKpi = activityKpi.toString();
	}

	public BigInteger getPlanStartTime() {
		return planStartTime;
	}

	public void setPlanStartTime(BigInteger planStartTime) {
		this.planStartTime = planStartTime;
	}

	public BigInteger getActualStartTime() {
		return actualStartTime;
	}

	public void setActualStartTime(BigInteger actualStartTime) {
		this.actualStartTime = actualStartTime;
	}

	public BigInteger getPlanDoneTime() {
		return planDoneTime;
	}

	public void setPlanDoneTime(BigInteger planDoneTime) {
		this.planDoneTime = planDoneTime;
	}

	public BigInteger getActualDoneTime() {
		return actualDoneTime;
	}

	public void setActualDoneTime(BigInteger actualDoneTime) {
		this.actualDoneTime = actualDoneTime;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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

	/////////////////////////////////////////////////////////////////////////
	// helpers
//	public static final String getOpsStage(int index) {
//		if (index < OpsStage.length) {
//			return OpsStage[index];
//		}
//		return null;
//	}
//	public static final int getOpsStage(String stage) {
//		for (int i = 0; i < OpsStage.length; i++) {
//			if (OpsStage[i].equals(stage)) {
//				return i;
//			}
//		}
//		return -1;		
//	}

    protected AhaOpsStage(Parcel in) {
        siteId = in.readString();
        stage = in.readString();
        onTrackKpi = in.readString();
        activityKpi = in.readString();
        planStartTime = (BigInteger) in.readValue(BigInteger.class.getClassLoader());
        actualStartTime = (BigInteger) in.readValue(BigInteger.class.getClassLoader());
        planDoneTime = (BigInteger) in.readValue(BigInteger.class.getClassLoader());
        actualDoneTime = (BigInteger) in.readValue(BigInteger.class.getClassLoader());
        note = in.readString();
        reserve1 = in.readString();
        active = in.readByte() == 0x00 ? null : in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(siteId);
        dest.writeString(stage);
        dest.writeString(onTrackKpi);
        dest.writeString(activityKpi);
        dest.writeValue(planStartTime);
        dest.writeValue(actualStartTime);
        dest.writeValue(planDoneTime);
        dest.writeValue(actualDoneTime);
        dest.writeString(note);
        dest.writeString(reserve1);
        if (active == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(active);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<AhaOpsStage> CREATOR = new Parcelable.Creator<AhaOpsStage>() {
        @Override
        public AhaOpsStage createFromParcel(Parcel in) {
            return new AhaOpsStage(in);
        }

        @Override
        public AhaOpsStage[] newArray(int size) {
            return new AhaOpsStage[size];
        }
    };
}
