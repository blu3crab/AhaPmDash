// Project: AHA Device IO Service
// Contributer(s): M.A.Tucker
// Origination: MAR 2014
// Copyright © Adaptive Handy Apps, LLC.  All Rights Reserved.

package com.adaptivehandyapps.ahaclouddal;

import java.io.Serializable;
import java.math.BigInteger;

import com.google.gson.annotations.SerializedName;
import com.adaptivehandyapps.ahaclouddal.AhaOpsStage.OpsActivityKPI;
import com.adaptivehandyapps.ahaclouddal.AhaOpsStage.OpsOnTrackKPI;

public class CopyOfAhaSiteState implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String JSON_SITESTATE_CONTAINER = "siteState";

	// enum alert level: diag, info, warning, severe, critical
//	public enum AlertLevel {NADA, DIAG, INFO, WARNING, SEVERE, CRITICAL};
	public enum AlertLevel {
		@SerializedName("NADA")
		NADA, 
		@SerializedName("DIAG")
		DIAG, 
		@SerializedName("INFO")
		INFO, 
		@SerializedName("WARNING")
		WARNING, 
		@SerializedName("SEVERE")
		SEVERE, 
		@SerializedName("CRITICAL")
		CRITICAL};
	// enum alert type: heat high-low, cool high-low, unoccupied motion, door open, htub cover off, power anomaly
//	public enum AlertType {NADA, OPS_RFO_RISK, OPS_RFO_DELAY, HEAT_HIGH, HEAT_LOW, COOL_HIGH, COOL_LOW, MOTION, OPEN, UNLOCKED, COVER_OFF, POWER_ANOMALY}; 
	public enum AlertType {
		@SerializedName("NADA")
		NADA, 
		@SerializedName("OPS_RFO_RISK")
		OPS_RFO_RISK, 
		@SerializedName("OPS_RFO_DELAY")
		OPS_RFO_DELAY, 
		@SerializedName("HEAT_HIGH")
		HEAT_HIGH, 
		@SerializedName("HEAT_LOW")
		HEAT_LOW, 
		@SerializedName("COOL_HIGH")
		COOL_HIGH, 
		@SerializedName("COOL_LOW")
		COOL_LOW, 
		@SerializedName("MOTION")
		MOTION, 
		@SerializedName("OPEN")
		OPEN, 
		@SerializedName("UNLOCKED")
		UNLOCKED, 
		@SerializedName("COVER_OFF")
		COVER_OFF, 
		@SerializedName("POWER_ANOMALY")
		POWER_ANOMALY}; 
	// enum site status: occupied, to unoccupied, unoccupied, to occupied (prep)
//	public enum SiteStatus {NADA, OCCUPIED, TO_UNOCCUPIED, UNOCCUPIED, TO_OCCUPIED};
	public enum SiteStatus {
		@SerializedName("NADA")
		NADA, 
		@SerializedName("OCCUPIED")
		OCCUPIED, 
		@SerializedName("TO_UNOCCUPIED")
		TO_UNOCCUPIED, 
		@SerializedName("UNOCCUPIED")
		UNOCCUPIED, 
		@SerializedName("TO_OCCUPIED")
		TO_OCCUPIED};
	// enum scenario: welcome, spring, summer, fall, winter
//	public enum ScenarioType {NADA, WELCOME, SPRING, SUMMER, FALL, WINTER};
	public enum ScenarioType {
		@SerializedName("NADA")
		NADA, 
		@SerializedName("WELCOME")
		WELCOME, 
		@SerializedName("SPRING")
		SPRING, 
		@SerializedName("SUMMER")
		SUMMER, 
		@SerializedName("FALL")
		FALL, 
		@SerializedName("WINTER")
		WINTER};
	// enum open-closed: open, closed, locked
	// enum usage: on, off
	// 
	// site id 
	@SerializedName("siteId")
	private String siteId;
	// KPI 
	@SerializedName("onTrackKpi")
//	private OpsOnTrackKPI onTrackKpi;
	private String onTrackKpi;
	// KPI 
	@SerializedName("activityKpi")
//	private OpsActivityKPI activityKpi;
	private String activityKpi;
	// current alert level
	@SerializedName("alertLevel")
//	private AlertLevel alertLevel;
	private String alertLevel;
	// current alert type
	@SerializedName("alertType")
//	private AlertType alertType;
	private String alertType;
	// current site status
	@SerializedName("siteStatus")
//	private SiteStatus siteStatus;
	private String siteStatus;
	// current scenario(s)
	@SerializedName("scenarioType")
//	private ScenarioType scenarioType;
	private String scenarioType;
	// next site use transition (date/time)
	@SerializedName("transitionTime")
	private BigInteger transitionTime;	

	public CopyOfAhaSiteState() {
		this.siteId = AhaDalShare.NULL_MARKER;
		this.onTrackKpi = OpsOnTrackKPI.PLAN.toString();
		this.activityKpi = OpsActivityKPI.INACTIVE.toString();
		this.alertLevel = AlertLevel.NADA.toString();
		this.alertType = AlertType.NADA.toString();
		this.siteStatus = SiteStatus.NADA.toString();
		this.scenarioType = ScenarioType.NADA.toString();
		this.transitionTime = BigInteger.valueOf(0);
	}
	
	public CopyOfAhaSiteState(
			String siteId,
			OpsOnTrackKPI onTrackKpi,
			OpsActivityKPI activityKpi,
			AlertLevel alertLevel,
			AlertType alertType,
			SiteStatus siteStatus,
			ScenarioType scenarioType,
			BigInteger transitionTime
			) {
		this.siteId = siteId;
		this.onTrackKpi = onTrackKpi.toString();
		this.activityKpi = activityKpi.toString();
		this.alertLevel = alertLevel.toString();
		this.alertType = alertType.toString();
		this.siteStatus = siteStatus.toString();
		this.scenarioType = scenarioType.toString();
		this.transitionTime = transitionTime;
	}

	////////////////////////////////////////////////////////////
	// getters/setters
	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
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

	public AlertLevel getAlertLevel() {
		return AlertLevel.valueOf(alertLevel);
	}

	public void setAlertLevel(AlertLevel alertLevel) {
		this.alertLevel = alertLevel.toString();
	}

	public AlertType getAlertType() {
		return AlertType.valueOf(alertType);
	}

	public void setAlertType(AlertType alertType) {
		this.alertType = alertType.toString();
	}

	public SiteStatus getSiteStatus() {
		return SiteStatus.valueOf(siteStatus);
	}

	public void setSiteStatus(SiteStatus siteStatus) {
		this.siteStatus = siteStatus.toString();
	}

	public ScenarioType getScenarioType() {
		return ScenarioType.valueOf(scenarioType);
	}

	public void setScenarioType(ScenarioType scenarioType) {
		this.scenarioType = scenarioType.toString();
	}

	public BigInteger getTransitionTime() {
		return transitionTime;
	}

	public void setTransitionTime(BigInteger transitionTime) {
		this.transitionTime = transitionTime;
	}

}