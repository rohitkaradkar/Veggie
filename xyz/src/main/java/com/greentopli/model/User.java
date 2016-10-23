package com.greentopli.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

/**
 * Created by rnztx on 23/10/16.
 */

@Entity public class User {
	@Index private String email;
	@Index private String name;
	@Index private long mobileNo;
	@Index private String address;
	@Index private int pincode;
	@Index private String instanceId;
	@Index private String authToken;

	public User(String email) {
		this.email = email;
	}

	public User() {}

	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}

	public long getMobileNo() {
		return mobileNo;
	}

	public String getAddress() {
		return address;
	}

	public int getPincode() {
		return pincode;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setMobileNo(long mobileNo) {
		this.mobileNo = mobileNo;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setPincode(int pincode) {
		this.pincode = pincode;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
}
