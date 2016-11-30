package com.greentopli.core.storage.user;

import android.support.annotation.NonNull;

import com.greentopli.core.storage.base.BaseModel;

/**
 * Order Information
 */
public interface UserModel extends BaseModel {

	/**
	 * Get the {@code email} value.
	 * Cannot be {@code null}.
	 */
	@NonNull
	String getEmail();

	/**
	 * Get the {@code name} value.
	 * Cannot be {@code null}.
	 */
	@NonNull
	String getName();

	/**
	 * Get the {@code mobile_no} value.
	 */
	long getMobileNo();

	/**
	 * Get the {@code address} value.
	 * Cannot be {@code null}.
	 */
	@NonNull
	String getAddress();

	/**
	 * Get the {@code pincode} value.
	 */
	int getPincode();

	/**
	 * identification for FCM service
	 * Cannot be {@code null}.
	 */
	@NonNull
	String getInstanceId();

	/**
	 * for verifying user auth from Firebase. Used by server
	 * Cannot be {@code null}.
	 */
	@NonNull
	String getAuthToken();

	/**
	 * Get the {@code photo_url} value.
	 * Cannot be {@code null}.
	 */
	@NonNull
	String getPhotoUrl();
}
