// Project: AHA Device IO Service
// Contributer(s): M.A.Tucker
// Origination: DEC 2014
// Copyright © 2014 Adaptive Handy Apps, LLC.  All Rights Reserved.

package com.adaptivehandyapps.ahaclouddal;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class AhaSiteLife implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String JSON_SITELIFE_CONTAINER = "sitelife";

	// properties 
	@SerializedName("orgId")
	private String orgId;		// org id
	@SerializedName("siteId")
	private String siteId;		// site id

	// site attributes
	@SerializedName("keylockInstalled")
	private Boolean keylockInstalled;		// site serial number defined
	@SerializedName("activeBooking")
	private Integer activeBooking;			// current active booking
	
	// bookings
	@SerializedName("bookingEmail")
	private List<String> bookingEmail;		// renter email
	@SerializedName("bookingCheckin")		
	private List<String> bookingCheckin;	// checkin time (secs)
	@SerializedName("bookingCheckout")		
	private List<String> bookingCheckout;	// checkout time (secs)
	
	// alerts: PinCode entered
	@SerializedName("alertTime")			
	private List<String> alertTime;			// rcv'd time (secs)
	@SerializedName("alertNickname")
	private List<String> alertNickname;		// nickname: PINCODE name
	@SerializedName("alertActivity")
	private List<String> alertActivity;		// activity: enter, exit
	
	// requests: app updates
	@SerializedName("requestTime")			
	private List<String> requestTime;		// rcv'd time (secs)
	@SerializedName("requestNickname")
	private List<String> requestNickname;	// nickname: staff id
	@SerializedName("requestActivity")
	private List<String> requestActivity;	// activity: START, BLOCK, DONE

	// booking
	public class Booking {
		public String email;				// renter email
		public BigInteger checkin;			// checkin time (secs)
		public BigInteger checkout;			// checkout time (secs)
	}

	// event: alert or request
	public class Event {
		public BigInteger time;	// when
		public String nickname;	// who
		public String activity;	// what
	}
	
	// constructor
	public AhaSiteLife() {
		this.orgId = AhaDalShare.NULL_MARKER;
		this.siteId = AhaDalShare.NULL_MARKER;
		
		this.keylockInstalled = false;
		this.activeBooking = AhaDalShare.OBJ_UNDEFINED;
				
		this.bookingEmail = new ArrayList<String>();
		this.bookingCheckin = new ArrayList<String>();
		this.bookingCheckout = new ArrayList<String>();
		
		this.alertTime = new ArrayList<String>();
		this.alertNickname = new ArrayList<String>();
		this.alertActivity = new ArrayList<String>();
		
		this.requestTime = new ArrayList<String>();
		this.requestNickname = new ArrayList<String>();
		this.requestActivity = new ArrayList<String>();
	}
	
	////////////////////////////////////////////////////////////
	// utility
	// is a keylock installed?
	public boolean isKeylockInstalled() {
		return false;
	}
	////////////////////////////////////////////////////////////
	// booking helpers
	// is the site booked at time
	public boolean isSiteBooked(BigInteger time) {
		return false;
	}
	// get booking by time
	public int getBooking(BigInteger time) {
		int bookingIndex = -1;
		boolean found = false;
		int i = 0;
		while (!found & i < bookingEmail.size()) {
			Booking booking = getBooking(i);
			if (time.longValue() >= booking.checkin.longValue() && 
					time.longValue() <= booking.checkout.longValue()) {
				found = true;
				bookingIndex = i;
			}
			++i;
		}
		return bookingIndex;
	}
	// add booking
	public int addBooking(String email, BigInteger checkin, BigInteger checkout) {
		// TODO: if email exists
		// if bookingEmail.contains(email), update booking set(i)
		// else add
		bookingEmail.add(email);
		bookingCheckin.add(checkin.toString());
		bookingCheckout.add(checkout.toString());
		return bookingEmail.size();
	}
	public int getBookingCount() {
		return bookingEmail.size();
	}
	// get booking
	public Booking getBooking(int i) {
		Booking booking = null;
		if (i >= 0 && i < bookingEmail.size()) {
			booking = new Booking();
			booking.email = bookingEmail.get(i);
			booking.checkin = new BigInteger(bookingCheckin.get(i));
			booking.checkout = new BigInteger(bookingCheckout.get(i));
		}
		return booking;
	}
	// remove booking
	public Booking removeBooking(int i) {
		Booking booking = null;
		if (i >= 0 && i < bookingEmail.size()) {
			// copy booking
			booking = getBooking(i);
			// remove booking
			bookingEmail.remove(i);
			bookingCheckin.remove(i);
			bookingCheckout.remove(i);
		}
		// return copy of removed booking
		return booking;
	}
	////////////////////////////////////////////////////////////
	// alert helpers
	// add alert
	public int addAlert(BigInteger time, String nickname, String activity) {
		alertTime.add(time.toString());
		alertNickname.add(nickname);
		alertActivity.add(activity);
		return alertTime.size();
	}
	public int getAlertCount() {
		return alertTime.size();
	}
	// get alert
	public Event getAlert(int i) {
		Event event = null;
		if (i >= 0 && i < alertTime.size()) {
			event = new Event();
			event.time = new BigInteger(alertTime.get(i));
			event.nickname = alertNickname.get(i);
			event.activity = alertActivity.get(i);
		}
		return event;
	}
	// remove alert
	public Event removeAlert(int i) {
		Event event = null;
		if (i >= 0 && i < alertTime.size()) {
			// copy alert
			event = getAlert(i);
			// remove alert
			alertTime.remove(i);
			alertNickname.remove(i);
			alertActivity.remove(i);
		}
		// return copy of removed event
		return event;
	}
	////////////////////////////////////////////////////////////
	// request helpers
	// add request
	public int addRequest(BigInteger time, String nickname, String activity) {
		requestTime.add(time.toString());
		requestNickname.add(nickname);
		requestActivity.add(activity);
		return requestTime.size();
	}
	public int getRequestCount() {
		return requestTime.size();
	}
	// get request
	public Event getRequest(int i) {
		Event event = null;
		if (i >= 0 && i < requestTime.size()) {
			event = new Event();
			event.time = new BigInteger(requestTime.get(i));
			event.nickname = requestNickname.get(i);
			event.activity = requestActivity.get(i);
		}
		return event;
	}
	// remove request
	public Event removeRequest(int i) {
		Event event = null;
		if (i >= 0 && i < requestTime.size()) {
			// copy event
			event = getRequest(i);
			// remove request
			requestTime.remove(i);
			requestNickname.remove(i);
			requestActivity.remove(i);
		}
		// return copy of removed event
		return event;
	}
	
	////////////////////////////////////////////////////////////
	// getters/setters
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public Boolean getKeylockInstalled() {
		return keylockInstalled;
	}

	public void setKeylockInstalled(Boolean keylockInstalled) {
		this.keylockInstalled = keylockInstalled;
	}

	public Integer getActiveBooking() {
		return activeBooking;
	}

	public void setActiveBooking(Integer activeBooking) {
		this.activeBooking = activeBooking;
	}

	public List<String> getBookingEmail() {
		return bookingEmail;
	}

	public void setBookingEmail(List<String> bookingEmail) {
		this.bookingEmail = bookingEmail;
	}

	public List<String> getBookingCheckin() {
		return bookingCheckin;
	}

	public void setBookingCheckin(List<String> bookingCheckin) {
		this.bookingCheckin = bookingCheckin;
	}

	public List<String> getBookingCheckout() {
		return bookingCheckout;
	}

	public void setBookingCheckout(List<String> bookingCheckout) {
		this.bookingCheckout = bookingCheckout;
	}

	public List<String> getAlertTime() {
		return alertTime;
	}

	public void setAlertTime(List<String> alertTime) {
		this.alertTime = alertTime;
	}

	public List<String> getAlertNickname() {
		return alertNickname;
	}

	public void setAlertNickname(List<String> alertNickname) {
		this.alertNickname = alertNickname;
	}

	public List<String> getAlertActivity() {
		return alertActivity;
	}

	public void setAlertActivity(List<String> alertActivity) {
		this.alertActivity = alertActivity;
	}

	public List<String> getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(List<String> requestTime) {
		this.requestTime = requestTime;
	}

	public List<String> getRequestNickname() {
		return requestNickname;
	}

	public void setRequestNickname(List<String> requestNickname) {
		this.requestNickname = requestNickname;
	}

	public List<String> getRequestActivity() {
		return requestActivity;
	}

	public void setRequestActivity(List<String> requestActivity) {
		this.requestActivity = requestActivity;
	}

}