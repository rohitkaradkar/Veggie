package com.greentopli.core.storage.purchaseditem;

import android.support.annotation.NonNull;

import com.greentopli.core.storage.base.BaseModel;

/**
 * Order Information
 */
public interface PurchasedItemModel extends BaseModel {

	/**
	 * Unique id for purchasing entity
	 * Cannot be {@code null}.
	 */
	@NonNull
	String getPurchaseId();

	/**
	 * Unique id for user
	 * Cannot be {@code null}.
	 */
	@NonNull
	String getUserId();

	/**
	 * product that user is purchasing
	 * Cannot be {@code null}.
	 */
	@NonNull
	String getProductId();

	/**
	 * Get the {@code date_requested} value.
	 */
	long getDateRequested();

	/**
	 * Get the {@code date_accepted} value.
	 */
	long getDateAccepted();

	/**
	 * Get the {@code accepted} value.
	 */
	boolean getAccepted();

	/**
	 * Get the {@code completed} value.
	 */
	boolean getCompleted();

	/**
	 * volume of purchasing item eg. 6pcs or 250gms
	 */
	int getVolume();

	/**
	 * Total price
	 */
	int getTotalPrice();
}
