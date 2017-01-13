package com.adaptivehandyapps.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

import com.adaptivehandyapps.ahaclouddal.AhaOpsStage;
import com.adaptivehandyapps.ahaclouddal.AhaSite;

public class Site implements Parcelable {

	public enum Condition {
		GOOD, WARNING, ALARM, NOFEED
	}

	private String detailedCondition;
	private Condition baseCondition;
	private int profilePicture;
	private int accountPicture;
	private String siteId;
    private String address;
    private String orgId;
    private String accountName;
    private ArrayList<AhaOpsStage> opsStages;

    public String getDetailedCondition() {
        return detailedCondition;
    }

    public void setDetailedCondition(String detailedCondition) {
        this.detailedCondition = detailedCondition;
    }

    public Condition getBaseCondition() {
        return baseCondition;
    }

    public void setBaseCondition(Condition baseCondition) {
        this.baseCondition = baseCondition;
    }

    public int getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(int profilePicture) {
        this.profilePicture = profilePicture;
    }

    public int getAccountPicture() {
        return accountPicture;
    }

    public void setAccountPicture(int accountPicture) {
        this.accountPicture = accountPicture;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public ArrayList<AhaOpsStage> getOpsStages() {
        return opsStages;
    }

    public void setOpsStages(ArrayList<AhaOpsStage> opsStages) {
        this.opsStages = opsStages;
    }

	public Site( )
	{

	}

	public Site( String address )
	{
		this.setAddress(address);
	}

	public Site(  String address, String detailedCondition, Condition baseCondition )
	{
		this.setAddress(address);
		this.setDetailedCondition(detailedCondition);
		this.setBaseCondition(baseCondition);
	}

	@Override
	public String toString() {
		return address;
	}

    protected Site(Parcel in) {
        detailedCondition = in.readString();
        baseCondition = (Condition) in.readValue(Condition.class.getClassLoader());
        profilePicture = in.readInt();
        accountPicture = in.readInt();
        siteId = in.readString();
        address = in.readString();
        orgId = in.readString();
        accountName = in.readString();
        if (in.readByte() == 0x01) {
            opsStages = new ArrayList<AhaOpsStage>();
            in.readList(opsStages, AhaOpsStage.class.getClassLoader());
        } else {
            opsStages = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(detailedCondition);
        dest.writeValue(baseCondition);
        dest.writeInt(profilePicture);
        dest.writeInt(accountPicture);
        dest.writeString(siteId);
        dest.writeString(address);
        dest.writeString(orgId);
        dest.writeString(accountName);
        if (opsStages == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(opsStages);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Site> CREATOR = new Parcelable.Creator<Site>() {
        @Override
        public Site createFromParcel(Parcel in) {
            return new Site(in);
        }

        @Override
        public Site[] newArray(int size) {
            return new Site[size];
        }
    };
}