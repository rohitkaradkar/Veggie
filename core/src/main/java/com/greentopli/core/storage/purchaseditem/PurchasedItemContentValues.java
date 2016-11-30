package com.greentopli.core.storage.purchaseditem;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.greentopli.core.storage.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code purchased_item} table.
 */
public class PurchasedItemContentValues extends AbstractContentValues {
	@Override
	public Uri uri() {
		return PurchasedItemColumns.CONTENT_URI;
	}

	/**
	 * Update row(s) using the values stored by this object and the given selection.
	 *
	 * @param contentResolver The content resolver to use.
	 * @param where           The selection to use (can be {@code null}).
	 */
	public int update(ContentResolver contentResolver, @Nullable PurchasedItemSelection where) {
		return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
	}

	/**
	 * Update row(s) using the values stored by this object and the given selection.
	 *
	 * @param contentResolver The content resolver to use.
	 * @param where           The selection to use (can be {@code null}).
	 */
	public int update(Context context, @Nullable PurchasedItemSelection where) {
		return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
	}

	/**
	 * Unique id for purchasing entity
	 */
	public PurchasedItemContentValues putPurchaseId(@NonNull String value) {
		if (value == null) throw new IllegalArgumentException("purchaseId must not be null");
		mContentValues.put(PurchasedItemColumns.PURCHASE_ID, value);
		return this;
	}


	/**
	 * Unique id for user
	 */
	public PurchasedItemContentValues putUserId(@NonNull String value) {
		if (value == null) throw new IllegalArgumentException("userId must not be null");
		mContentValues.put(PurchasedItemColumns.USER_ID, value);
		return this;
	}


	/**
	 * product that user is purchasing
	 */
	public PurchasedItemContentValues putProductId(@NonNull String value) {
		if (value == null) throw new IllegalArgumentException("productId must not be null");
		mContentValues.put(PurchasedItemColumns.PRODUCT_ID, value);
		return this;
	}


	public PurchasedItemContentValues putDateRequested(long value) {
		mContentValues.put(PurchasedItemColumns.DATE_REQUESTED, value);
		return this;
	}


	public PurchasedItemContentValues putDateAccepted(long value) {
		mContentValues.put(PurchasedItemColumns.DATE_ACCEPTED, value);
		return this;
	}


	public PurchasedItemContentValues putAccepted(boolean value) {
		mContentValues.put(PurchasedItemColumns.ACCEPTED, value);
		return this;
	}


	public PurchasedItemContentValues putCompleted(boolean value) {
		mContentValues.put(PurchasedItemColumns.COMPLETED, value);
		return this;
	}


	/**
	 * volume of purchasing item eg. 6pcs or 250gms
	 */
	public PurchasedItemContentValues putVolume(int value) {
		mContentValues.put(PurchasedItemColumns.VOLUME, value);
		return this;
	}


	/**
	 * Total price
	 */
	public PurchasedItemContentValues putTotalPrice(int value) {
		mContentValues.put(PurchasedItemColumns.TOTAL_PRICE, value);
		return this;
	}

}
