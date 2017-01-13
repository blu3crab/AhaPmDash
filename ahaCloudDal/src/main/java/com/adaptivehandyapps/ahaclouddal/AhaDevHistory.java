// Project: AHA Device IO Service
// Contributer(s): M.A.Tucker
// Origination: MAR 2014
// Copyright © 2014 Adaptive Handy Apps, LLC.  All Rights Reserved.
package com.adaptivehandyapps.ahaclouddal;

import java.io.Serializable;
import java.math.BigInteger;

import com.google.gson.annotations.SerializedName;

public class AhaDevHistory implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final String JSON_DEVICEHISTORY_CONTAINER = "deviceHistory";

	@SerializedName("siteId")
	private String siteId;			// site id
	@SerializedName("serialNumber")
	private String serialNumber;	// gateway serial number
	@SerializedName("collectTime")
	private BigInteger collectTime;	// collection time
	// thermostat
	@SerializedName("nameThermostat")
	private String nameThermostat;
	@SerializedName("hvacstate")		
	private String hvacstate;		// "idle", "on"
	@SerializedName("tempThermostat")
	private String tempThermostat;	// current temperature (units F or C will be defined in AhaSite class)
	@SerializedName("mode")
	private String mode;			// off, auto, cool, heat
	@SerializedName("coolsp")
	private String coolsp;			// cooling set point (target temperature)
	@SerializedName("heatsp")
	private String heatsp;			// heat set point (target temperature)
	@SerializedName("fanmode")
	private String fanmode;			// auto, on, off
	@SerializedName("batteryThermostat")
	private String batteryThermostat;	// battery charge % remaining
	
	// power meter has 2 clamps which may monitor separate electrical flows
	@SerializedName("nameMeter1")
	private String nameMeter1;
	@SerializedName("wattsMeter1")
	private String wattsMeter1;
	@SerializedName("kwhMeter1")
	private String kwhMeter1;

	@SerializedName("nameMeter2")
	private String nameMeter2;
	@SerializedName("wattsMeter2")
	private String wattsMeter2;
	@SerializedName("kwhMeter2")
	private String kwhMeter2;
	// 4-in-1 sensor captures temperature, light level humidity
	@SerializedName("nameSensor41")
	private String nameSensor41;
	@SerializedName("tempSensor41")
	private String tempSensor41;
	@SerializedName("light")
	private String light;
	@SerializedName("humidity")
	private String humidity;
	@SerializedName("batterySensor41")
	private String batterySensor41;	// battery charge % remaining
	@SerializedName("armedSensor41")
	private String armedSensor41;	// indicates whether motion sensor is armed
	// power switch captures watts, kwh
	@SerializedName("nameSwitch")
	private String nameSwitch;
	@SerializedName("wattsSwitch")
	private String wattsSwitch;
	@SerializedName("kwhSwitch")
	private String kwhSwitch;
	// simple open/close motion sensors only indicate whether they are armed
	@SerializedName("nameMotion1")
	private String nameMotion1;
	@SerializedName("armedMotion1")
	private String armedMotion1;
	@SerializedName("nameMotion2")
	private String nameMotion2;
	@SerializedName("armedMotion2")
	private String armedMotion2;

	public AhaDevHistory() {
		this.siteId = "-";
		this.serialNumber = "-";
		this.collectTime = BigInteger.valueOf(0);
		this.nameThermostat = "-";
		this.hvacstate = "-";
		this.tempThermostat = "-";
		this.mode = "-";
		this.coolsp = "-";
		this.heatsp = "-";
		this.fanmode = "-";
		this.batteryThermostat = "-";
		this.nameMeter1 = "-";
		this.wattsMeter1 = "-";
		this.kwhMeter1 = "-";
		this.nameMeter2 = "-";
		this.wattsMeter2 = "-";
		this.kwhMeter2 = "-";
		this.nameSensor41 = "-";
		this.tempSensor41 = "-";
		this.light = "-";
		this.humidity = "-";
		this.batterySensor41 = "-";
		this.armedSensor41 = "-";
		this.nameSwitch = "-";
		this.wattsSwitch = "-";
		this.kwhSwitch = "-";
		this.nameMotion1 = "-";
		this.armedMotion1 = "-";
		this.nameMotion2 = "-";
		this.armedMotion2 = "-";
	}

	public AhaDevHistory(
			String siteId,
			String serialNumber,
			BigInteger collectTime,
			String nameThermostat,
			String hvacstate,
			String tempThermostat,
			String mode,
			String coolsp,
			String heatsp,
			String fanmode,
			String batteryThermostat,
			String nameMeter1,
			String wattsMeter1,
			String kwhMeter1,
			String nameMeter2,
			String wattsMeter2,
			String kwhMeter2,
			String nameSensor41,
			String tempSensor41,
			String light,
			String humidity,
			String batterySensor41,
			String armedSensor41,
			String nameSwitch,
			String wattsSwitch,
			String kwhSwitch,
			String nameMotion1,
			String armedMotion1,
			String nameMotion2,
			String armedMotion2		
			) {
		this.siteId = siteId;
		this.serialNumber = serialNumber;
		this.collectTime = collectTime;
		this.nameThermostat = nameThermostat;
		this.hvacstate = hvacstate;
		this.tempThermostat = tempThermostat;
		this.mode = mode;
		this.coolsp = coolsp;
		this.heatsp = heatsp;
		this.fanmode = fanmode;
		this.batteryThermostat = batteryThermostat;
		this.nameMeter1 = nameMeter1;
		this.wattsMeter1 = wattsMeter1;
		this.kwhMeter1 = kwhMeter1;
		this.nameMeter2 = nameMeter2;
		this.wattsMeter2 = wattsMeter2;
		this.kwhMeter2 = kwhMeter2;
		this.nameSensor41 = nameSensor41;
		this.tempSensor41 = tempSensor41;
		this.light = light;
		this.humidity = humidity;
		this.batterySensor41 = batterySensor41;
		this.armedSensor41 = armedSensor41;
		this.nameSwitch = nameSwitch;
		this.wattsSwitch = wattsSwitch;
		this.kwhSwitch = kwhSwitch;
		this.nameMotion1 = nameMotion1;
		this.armedMotion1 = armedMotion1;
		this.nameMotion2 = nameMotion2;
		this.armedMotion2 = armedMotion2;
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

	public BigInteger getCollectTime() {
		return collectTime;
	}

	public void setCollectTime(BigInteger collectTime) {
		this.collectTime = collectTime;
	}

	public String getNameThermostat() {
		return nameThermostat;
	}

	public void setNameThermostat(String nameThermostat) {
		this.nameThermostat = nameThermostat;
	}

	public String getHvacstate() {
		return hvacstate;
	}

	public void setHvacstate(String hvacstate) {
		this.hvacstate = hvacstate;
	}

	public String getTempThermostat() {
		return tempThermostat;
	}

	public void setTempThermostat(String tempThermostat) {
		this.tempThermostat = tempThermostat;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getCoolsp() {
		return coolsp;
	}

	public void setCoolsp(String coolsp) {
		this.coolsp = coolsp;
	}

	public String getHeatsp() {
		return heatsp;
	}

	public void setHeatsp(String heatsp) {
		this.heatsp = heatsp;
	}

	public String getFanmode() {
		return fanmode;
	}

	public void setFanmode(String fanmode) {
		this.fanmode = fanmode;
	}

	public String getBatteryThermostat() {
		return batteryThermostat;
	}

	public void setBatteryThermostat(String batteryThermostat) {
		this.batteryThermostat = batteryThermostat;
	}

	public String getNameMeter1() {
		return nameMeter1;
	}

	public void setNameMeter1(String nameMeter1) {
		this.nameMeter1 = nameMeter1;
	}

	public String getWattsMeter1() {
		return wattsMeter1;
	}

	public void setWattsMeter1(String wattsMeter1) {
		this.wattsMeter1 = wattsMeter1;
	}

	public String getKwhMeter1() {
		return kwhMeter1;
	}

	public void setKwhMeter1(String kwhMeter1) {
		this.kwhMeter1 = kwhMeter1;
	}

	public String getNameMeter2() {
		return nameMeter2;
	}

	public void setNameMeter2(String nameMeter2) {
		this.nameMeter2 = nameMeter2;
	}

	public String getWattsMeter2() {
		return wattsMeter2;
	}

	public void setWattsMeter2(String wattsMeter2) {
		this.wattsMeter2 = wattsMeter2;
	}

	public String getKwhMeter2() {
		return kwhMeter2;
	}

	public void setKwhMeter2(String kwhMeter2) {
		this.kwhMeter2 = kwhMeter2;
	}

	public String getNameSensor41() {
		return nameSensor41;
	}

	public void setNameSensor41(String nameSensor41) {
		this.nameSensor41 = nameSensor41;
	}

	public String getTempSensor41() {
		return tempSensor41;
	}

	public void setTempSensor41(String tempSensor41) {
		this.tempSensor41 = tempSensor41;
	}

	public String getLight() {
		return light;
	}

	public void setLight(String light) {
		this.light = light;
	}

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public String getBatterySensor41() {
		return batterySensor41;
	}

	public void setBatterySensor41(String batterySensor41) {
		this.batterySensor41 = batterySensor41;
	}

	public String getArmedSensor41() {
		return armedSensor41;
	}

	public void setArmedSensor41(String armedSensor41) {
		this.armedSensor41 = armedSensor41;
	}

	public String getNameSwitch() {
		return nameSwitch;
	}

	public void setNameSwitch(String nameSwitch) {
		this.nameSwitch = nameSwitch;
	}

	public String getWattsSwitch() {
		return wattsSwitch;
	}

	public void setWattsSwitch(String wattsSwitch) {
		this.wattsSwitch = wattsSwitch;
	}

	public String getKwhSwitch() {
		return kwhSwitch;
	}

	public void setKwhSwitch(String kwhSwitch) {
		this.kwhSwitch = kwhSwitch;
	}

	public String getNameMotion1() {
		return nameMotion1;
	}

	public void setNameMotion1(String nameMotion1) {
		this.nameMotion1 = nameMotion1;
	}

	public String getArmedMotion1() {
		return armedMotion1;
	}

	public void setArmedMotion1(String armedMotion1) {
		this.armedMotion1 = armedMotion1;
	}

	public String getNameMotion2() {
		return nameMotion2;
	}

	public void setNameMotion2(String nameMotion2) {
		this.nameMotion2 = nameMotion2;
	}

	public String getArmedMotion2() {
		return armedMotion2;
	}

	public void setArmedMotion2(String armedMotion2) {
		this.armedMotion2 = armedMotion2;
	}

//	private static final long serialVersionUID = 1L;
//	// site id (serial number)
//	@SerializedName("serialNumber")
//	private String serialNumber;
//	@SerializedName("collectTime")
//	private BigInteger collectTime;	// collection time
//	// thermostat
//	@SerializedName("nameThermostat")
//	private String nameThermostat;
//	@SerializedName("hvacstate")		
//	private String hvacstate;		// "idle", "on"
//	@SerializedName("tempThermostat")
//	private String tempThermostat;	// current temperature (units F or C will be defined in AhaSite class)
//	@SerializedName("mode")
//	private String mode;			// off, auto, cool, heat
//	@SerializedName("coolsp")
//	private String coolsp;			// cooling set point (target temperature)
//	@SerializedName("heatsp")
//	private String heatsp;			// heat set point (target temperature)
//	@SerializedName("fanmode")
//	private String fanmode;			// auto, on, off
//	@SerializedName("batteryThermostat")
//	private String batteryThermostat;	// battery charge % remaining
//	
//	// power meter has 2 clamps which may monitor separate electrical flows
//	@SerializedName("nameMeter1")
//	private String nameMeter1;
//	@SerializedName("wattsMeter1")
//	private String wattsMeter1;
//	@SerializedName("kwhMeter1")
//	private String kwhMeter1;
//
//	@SerializedName("nameMeter2")
//	private String nameMeter2;
//	@SerializedName("wattsMeter2")
//	private String wattsMeter2;
//	@SerializedName("kwhMeter2")
//	private String kwhMeter2;
//	// 4-in-1 sensor captures temperature, light level humidity
//	@SerializedName("nameSensor41")
//	private String nameSensor41;
//	@SerializedName("tempSensor41")
//	private String tempSensor41;
//	@SerializedName("light")
//	private String light;
//	@SerializedName("humidity")
//	private String humidity;
//	@SerializedName("batterySensor41")
//	private String batterySensor41;	// battery charge % remaining
//	@SerializedName("armedSensor41")
//	private String armedSensor41;	// indicates whether motion sensor is armed
//	// power switch captures watts, kwh
//	@SerializedName("nameSwitch")
//	private String nameSwitch;
//	@SerializedName("wattsSwitch")
//	private String wattsSwitch;
//	@SerializedName("kwhSwitch")
//	private String kwhSwitch;
//	// simple open/close motion sensors only indicate whether they are armed
//	@SerializedName("nameMotion1")
//	private String nameMotion1;
//	@SerializedName("armedMotion1")
//	private String armedMotion1;
//	@SerializedName("nameMotion2")
//	private String nameMotion2;
//	@SerializedName("armedMotion2")
//	private String armedMotion2;
//
//	public AhaDevHistory() {
//	}
//
//	public AhaDevHistory(
//			String serialNumber,
//			BigInteger collectTime,
//			String nameThermostat,
//			String hvacstate,
//			String tempThermostat,
//			String mode,
//			String coolsp,
//			String heatsp,
//			String fanmode,
//			String batteryThermostat,
//			String nameMeter1,
//			String wattsMeter1,
//			String kwhMeter1,
//			String nameMeter2,
//			String wattsMeter2,
//			String kwhMeter2,
//			String nameSensor41,
//			String tempSensor41,
//			String light,
//			String humidity,
//			String batterySensor41,
//			String armedSensor41,
//			String nameSwitch,
//			String wattsSwitch,
//			String kwhSwitch,
//			String nameMotion1,
//			String armedMotion1,
//			String nameMotion2,
//			String armedMotion2		
//			) {
//		this.serialNumber = serialNumber;
//		this.collectTime = collectTime;
//		this.nameThermostat = nameThermostat;
//		this.hvacstate = hvacstate;
//		this.tempThermostat = tempThermostat;
//		this.mode = mode;
//		this.coolsp = coolsp;
//		this.heatsp = heatsp;
//		this.fanmode = fanmode;
//		this.batteryThermostat = batteryThermostat;
//		this.nameMeter1 = nameMeter1;
//		this.wattsMeter1 = wattsMeter1;
//		this.kwhMeter1 = kwhMeter1;
//		this.nameMeter2 = nameMeter2;
//		this.wattsMeter2 = wattsMeter2;
//		this.kwhMeter2 = kwhMeter2;
//		this.nameSensor41 = nameSensor41;
//		this.tempSensor41 = tempSensor41;
//		this.light = light;
//		this.humidity = humidity;
//		this.batterySensor41 = batterySensor41;
//		this.armedSensor41 = armedSensor41;
//		this.nameSwitch = nameSwitch;
//		this.wattsSwitch = wattsSwitch;
//		this.kwhSwitch = kwhSwitch;
//		this.nameMotion1 = nameMotion1;
//		this.armedMotion1 = armedMotion1;
//		this.nameMotion2 = nameMotion2;
//		this.armedMotion2 = armedMotion2;
//	}
//
//	public String getSerialNumber() {
//		return serialNumber;
//	}
//
//	public void setSerialNumber(String serialNumber) {
//		this.serialNumber = serialNumber;
//	}
//
//	public BigInteger getCollectTime() {
//		return collectTime;
//	}
//
//	public void setCollectTime(BigInteger collectTime) {
//		this.collectTime = collectTime;
//	}
//
//	public String getNameThermostat() {
//		return nameThermostat;
//	}
//
//	public void setNameThermostat(String nameThermostat) {
//		this.nameThermostat = nameThermostat;
//	}
//
//	public String getHvacstate() {
//		return hvacstate;
//	}
//
//	public void setHvacstate(String hvacstate) {
//		this.hvacstate = hvacstate;
//	}
//
//	public String getTempThermostat() {
//		return tempThermostat;
//	}
//
//	public void setTempThermostat(String tempThermostat) {
//		this.tempThermostat = tempThermostat;
//	}
//
//	public String getMode() {
//		return mode;
//	}
//
//	public void setMode(String mode) {
//		this.mode = mode;
//	}
//
//	public String getCoolsp() {
//		return coolsp;
//	}
//
//	public void setCoolsp(String coolsp) {
//		this.coolsp = coolsp;
//	}
//
//	public String getHeatsp() {
//		return heatsp;
//	}
//
//	public void setHeatsp(String heatsp) {
//		this.heatsp = heatsp;
//	}
//
//	public String getFanmode() {
//		return fanmode;
//	}
//
//	public void setFanmode(String fanmode) {
//		this.fanmode = fanmode;
//	}
//
//	public String getBatteryThermostat() {
//		return batteryThermostat;
//	}
//
//	public void setBatteryThermostat(String batteryThermostat) {
//		this.batteryThermostat = batteryThermostat;
//	}
//
//	public String getNameMeter1() {
//		return nameMeter1;
//	}
//
//	public void setNameMeter1(String nameMeter1) {
//		this.nameMeter1 = nameMeter1;
//	}
//
//	public String getWattsMeter1() {
//		return wattsMeter1;
//	}
//
//	public void setWattsMeter1(String wattsMeter1) {
//		this.wattsMeter1 = wattsMeter1;
//	}
//
//	public String getKwhMeter1() {
//		return kwhMeter1;
//	}
//
//	public void setKwhMeter1(String kwhMeter1) {
//		this.kwhMeter1 = kwhMeter1;
//	}
//
//	public String getNameMeter2() {
//		return nameMeter2;
//	}
//
//	public void setNameMeter2(String nameMeter2) {
//		this.nameMeter2 = nameMeter2;
//	}
//
//	public String getWattsMeter2() {
//		return wattsMeter2;
//	}
//
//	public void setWattsMeter2(String wattsMeter2) {
//		this.wattsMeter2 = wattsMeter2;
//	}
//
//	public String getKwhMeter2() {
//		return kwhMeter2;
//	}
//
//	public void setKwhMeter2(String kwhMeter2) {
//		this.kwhMeter2 = kwhMeter2;
//	}
//
//	public String getNameSensor41() {
//		return nameSensor41;
//	}
//
//	public void setNameSensor41(String nameSensor41) {
//		this.nameSensor41 = nameSensor41;
//	}
//
//	public String getTempSensor41() {
//		return tempSensor41;
//	}
//
//	public void setTempSensor41(String tempSensor41) {
//		this.tempSensor41 = tempSensor41;
//	}
//
//	public String getLight() {
//		return light;
//	}
//
//	public void setLight(String light) {
//		this.light = light;
//	}
//
//	public String getHumidity() {
//		return humidity;
//	}
//
//	public void setHumidity(String humidity) {
//		this.humidity = humidity;
//	}
//
//	public String getBatterySensor41() {
//		return batterySensor41;
//	}
//
//	public void setBatterySensor41(String batterySensor41) {
//		this.batterySensor41 = batterySensor41;
//	}
//
//	public String getArmedSensor41() {
//		return armedSensor41;
//	}
//
//	public void setArmedSensor41(String armedSensor41) {
//		this.armedSensor41 = armedSensor41;
//	}
//
//	public String getNameSwitch() {
//		return nameSwitch;
//	}
//
//	public void setNameSwitch(String nameSwitch) {
//		this.nameSwitch = nameSwitch;
//	}
//
//	public String getWattsSwitch() {
//		return wattsSwitch;
//	}
//
//	public void setWattsSwitch(String wattsSwitch) {
//		this.wattsSwitch = wattsSwitch;
//	}
//
//	public String getKwhSwitch() {
//		return kwhSwitch;
//	}
//
//	public void setKwhSwitch(String kwhSwitch) {
//		this.kwhSwitch = kwhSwitch;
//	}
//
//	public String getNameMotion1() {
//		return nameMotion1;
//	}
//
//	public void setNameMotion1(String nameMotion1) {
//		this.nameMotion1 = nameMotion1;
//	}
//
//	public String getArmedMotion1() {
//		return armedMotion1;
//	}
//
//	public void setArmedMotion1(String armedMotion1) {
//		this.armedMotion1 = armedMotion1;
//	}
//
//	public String getNameMotion2() {
//		return nameMotion2;
//	}
//
//	public void setNameMotion2(String nameMotion2) {
//		this.nameMotion2 = nameMotion2;
//	}
//
//	public String getArmedMotion2() {
//		return armedMotion2;
//	}
//
//	public void setArmedMotion2(String armedMotion2) {
//		this.armedMotion2 = armedMotion2;
//	}
}