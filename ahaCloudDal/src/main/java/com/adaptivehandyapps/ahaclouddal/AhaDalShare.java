// Project: AHA Device IO Service
// Contributor(s): M.A.Tucker
// Origination: JUL 2014
// Copyright � 2014 Adaptive Handy Apps, LLC.  All Rights Reserved.
package com.adaptivehandyapps.ahaclouddal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AhaDalShare {
	////////////////////////////////////////////////////////
	// AHA object indicator
	public static final Integer OBJ_ACTIVE = 1;	// object is active
	public static final Integer OBJ_INACTIVE = 0;	// object is inactive
	public static final Integer OBJ_UNDEFINED = -1;

	////////////////////////////////////////////////////////
	// request URL elements
	public static final String URL_ACE_SERVICE = "ahadiossam";
	public static final String URL_GRANT_REQUEST = "grant";
	public static final String URL_ADMIN_REQUEST = "admin";
	public static final String URL_ACE_REQUEST = "ace";
	public static final String URL_ALERT_REQUEST = "alert";
	public static final String URL_PULSE_REQUEST = "pulse";
	public static final String URL_TOKEN_OID = "oid";
	public static final String URL_TOKEN_SID = "sid";
	public static final String URL_TOKEN_DID = "did";

	public static final String URL_KEY_ACCESS = "access";
	public static final String URL_KEY_RELEASE = "release";
	
	public static final String URL_KEY_ORG = "org";
	public static final String URL_KEY_LOCATOR = "locator";
	public static final String URL_KEY_SUMMARY = "summary";
	public static final String URL_KEY_ADD = "add";
	public static final String URL_KEY_UPDATE = "update";
	public static final String URL_KEY_LIST = "list";
	public static final String URL_KEY_DELETE = "delete";
	public static final String URL_KEY_RESET = "reset";
	public static final String URL_KEY_FLUSH = "flush";
	public static final String URL_KEY_STAFF = "staff";
	public static final String URL_KEY_SITE = "site";
	public static final String URL_KEY_OPSSTAGE = "opsstage";
	public static final String URL_KEY_STAGE = "stage";
	public static final String URL_KEY_ACTIVITY = "activity";
	public static final String URL_KEY_PSTIME = "pstime";
	public static final String URL_KEY_ASTIME = "astime";
	public static final String URL_KEY_PDTIME = "pdtime";
	public static final String URL_KEY_ADTIME = "adtime";
	public static final String URL_KEY_NOTE = "note";

	public static final String URL_KEY_TAG = "tag";
	public static final String URL_KEY_RESERVE1 = "reserve1";

//	public static final String URL_KEY_SITESTATE = "sitestate";
	public static final String URL_KEY_SITELIFE = "sitelife";
	public static final String URL_KEY_DEVICE = "device";
	public static final String URL_KEY_HISTMIN = "historymin";
	public static final String URL_KEY_HISTHOUR = "historyhour";
	public static final String URL_KEY_HISTDAY = "historyday";

	public static final String URL_KEY_NICKNAME = "nickname";
	public static final String URL_KEY_LONGNAME = "longname";
	public static final String URL_KEY_ADDRESS = "address";
	public static final String URL_KEY_LOCATION = "location";
	public static final String URL_KEY_ROLE = "role";
	public static final String URL_KEY_EMAIL = "email";
	public static final String URL_KEY_SUPEREMAIL = "super";
	public static final String URL_KEY_COUNT = "count";

	public static final String URL_KEY_FROM = "from";
	public static final String URL_KEY_DATE = "date";
	public static final String URL_KEY_SUBJECT = "subject";
	public static final String URL_KEY_BODY = "body";
	
	public static final String URL_USER_AGENT = "Mozilla/5.0";
	////////////////////////////////////////////////////////
	// markers
	public static final String NULL_MARKER = "nada";
	public static final String STATUS_CODE_MARKER = "SC ";
	public static final String END_OF_TOKENS = "eof";
	public static final String VALIDATION_MARKER = "validation";
	public static final String RESERVATION_MARKER = "reservation:";
	public static final String RESERVATION_FROMTO_MARKER = "@";
	public static final String BATTERY_MARKER = "BatteryLevel";
	public static final String ARMED_MARKER = "Armed";
	public static final String TRIPPED_MARKER = "Tripped";
//	public static final String PINCODE_MARKER = "DL_USERCODE";
	public static final String PINCODE_MARKER = "DLUSERCODE";
	public static final String SERIAL_MARKER = "Serial";
	public static final String USERNAME_MARKER = "UserName";
	public static final String START_MARKER = "in";
	public static final String DONE_MARKER = "out";
	public static final String SEPARATOR_MARKER = "----";
	////////////////////////////////////////////////////////
	// AHA roles
	public static final String AHA_ROLE_OWNER = "owner";
	public static final String AHA_ROLE_RENTER = "renter";
	public static final String AHA_ROLE_DIRECTOR = "director";
	public static final String AHA_ROLE_ADMIN = "admin";
	public static final String AHA_ROLE_OWNERREP = "or";
	public static final String AHA_ROLE_HKSUPER = "hksuper";
	public static final String AHA_ROLE_HK = "hk";
	public static final String AHA_ROLE_LDSUPER = "ldsuper";
	public static final String AHA_ROLE_LD = "ld";
	public static final String AHA_ROLE_HTSUPER = "htsuper";
	public static final String AHA_ROLE_HT = "ht";
	public static final String AHA_ROLE_MTSUPER = "mtsuper";
	public static final String AHA_ROLE_MT = "mt";
	public static final List<String> AHA_ROLE = new ArrayList<String>(
			Arrays.asList(
					AHA_ROLE_OWNER, AHA_ROLE_RENTER, 
					AHA_ROLE_DIRECTOR, AHA_ROLE_ADMIN, AHA_ROLE_OWNERREP, 
					AHA_ROLE_HKSUPER, AHA_ROLE_HK, AHA_ROLE_LDSUPER, AHA_ROLE_LD,
					AHA_ROLE_HTSUPER, AHA_ROLE_HT, AHA_ROLE_MTSUPER, AHA_ROLE_MT
					));
	
	////////////////////////////////////////////////////////
	// AHA device names
	public static final String DEVICE_NAME_THERMOSTAT = "Thermostat";
	public static final String DEVICE_NAME_MULTISENSORAEON = "MultiSensorAeon";
	public static final String DEVICE_NAME_MULTISENSORPHILIO = "MultiSensorPhilio";
	public static final String DEVICE_NAME_SWITCHHEAVY = "SmartSwitchHeavy";
	public static final String DEVICE_NAME_SWITCHLIGHT = "SmartPlugLight";
	public static final String DEVICE_NAME_METER = "SmartMeter";
	public static final String DEVICE_NAME_METER1 = "SmartMeter1";
	public static final String DEVICE_NAME_METER2 = "SmartMeter2";
	public static final String DEVICE_NAME_METER3 = "SmartMeter3";
	public static final String DEVICE_NAME_DOOROPENAEON = "DoorOpenAeon";
	public static final String DEVICE_NAME_DOOROPENPHILIO = "DoorOpenPhilio";
	public static final String DEVICE_NAME_HOTTUBOPENAEON = "HotTubOpenAeon";
	public static final String DEVICE_NAME_HOTTUBOPENPHILIO = "HotTubOpenPhilio";
	public static final String DEVICE_NAME_FRONTLOCKYALE = "FrontLockYale";
	public static final String DEVICE_NAME_OWNERLOCKYALE = "OwnerLockYale";
	////////////////////////////////////////////////////////
	// alert markers
	public static final String ALERT_PINCODE = "pincode";

}
