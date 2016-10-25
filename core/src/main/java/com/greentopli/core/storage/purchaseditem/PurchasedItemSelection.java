package com.greentopli.core.storage.purchaseditem;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.greentopli.core.storage.base.AbstractSelection;

/**
 * Selection for the {@code purchased_item} table.
 */
public class PurchasedItemSelection extends AbstractSelection<PurchasedItemSelection> {
    @Override
    protected Uri baseUri() {
        return PurchasedItemColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code PurchasedItemCursor} object, which is positioned before the first entry, or null.
     */
    public PurchasedItemCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new PurchasedItemCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public PurchasedItemCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code PurchasedItemCursor} object, which is positioned before the first entry, or null.
     */
    public PurchasedItemCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new PurchasedItemCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public PurchasedItemCursor query(Context context) {
        return query(context, null);
    }


    public PurchasedItemSelection id(long... value) {
        addEquals("purchased_item." + PurchasedItemColumns._ID, toObjectArray(value));
        return this;
    }

    public PurchasedItemSelection idNot(long... value) {
        addNotEquals("purchased_item." + PurchasedItemColumns._ID, toObjectArray(value));
        return this;
    }

    public PurchasedItemSelection orderById(boolean desc) {
        orderBy("purchased_item." + PurchasedItemColumns._ID, desc);
        return this;
    }

    public PurchasedItemSelection orderById() {
        return orderById(false);
    }

    public PurchasedItemSelection purchaseId(String... value) {
        addEquals(PurchasedItemColumns.PURCHASE_ID, value);
        return this;
    }

    public PurchasedItemSelection purchaseIdNot(String... value) {
        addNotEquals(PurchasedItemColumns.PURCHASE_ID, value);
        return this;
    }

    public PurchasedItemSelection purchaseIdLike(String... value) {
        addLike(PurchasedItemColumns.PURCHASE_ID, value);
        return this;
    }

    public PurchasedItemSelection purchaseIdContains(String... value) {
        addContains(PurchasedItemColumns.PURCHASE_ID, value);
        return this;
    }

    public PurchasedItemSelection purchaseIdStartsWith(String... value) {
        addStartsWith(PurchasedItemColumns.PURCHASE_ID, value);
        return this;
    }

    public PurchasedItemSelection purchaseIdEndsWith(String... value) {
        addEndsWith(PurchasedItemColumns.PURCHASE_ID, value);
        return this;
    }

    public PurchasedItemSelection orderByPurchaseId(boolean desc) {
        orderBy(PurchasedItemColumns.PURCHASE_ID, desc);
        return this;
    }

    public PurchasedItemSelection orderByPurchaseId() {
        orderBy(PurchasedItemColumns.PURCHASE_ID, false);
        return this;
    }

    public PurchasedItemSelection userId(String... value) {
        addEquals(PurchasedItemColumns.USER_ID, value);
        return this;
    }

    public PurchasedItemSelection userIdNot(String... value) {
        addNotEquals(PurchasedItemColumns.USER_ID, value);
        return this;
    }

    public PurchasedItemSelection userIdLike(String... value) {
        addLike(PurchasedItemColumns.USER_ID, value);
        return this;
    }

    public PurchasedItemSelection userIdContains(String... value) {
        addContains(PurchasedItemColumns.USER_ID, value);
        return this;
    }

    public PurchasedItemSelection userIdStartsWith(String... value) {
        addStartsWith(PurchasedItemColumns.USER_ID, value);
        return this;
    }

    public PurchasedItemSelection userIdEndsWith(String... value) {
        addEndsWith(PurchasedItemColumns.USER_ID, value);
        return this;
    }

    public PurchasedItemSelection orderByUserId(boolean desc) {
        orderBy(PurchasedItemColumns.USER_ID, desc);
        return this;
    }

    public PurchasedItemSelection orderByUserId() {
        orderBy(PurchasedItemColumns.USER_ID, false);
        return this;
    }

    public PurchasedItemSelection productId(String... value) {
        addEquals(PurchasedItemColumns.PRODUCT_ID, value);
        return this;
    }

    public PurchasedItemSelection productIdNot(String... value) {
        addNotEquals(PurchasedItemColumns.PRODUCT_ID, value);
        return this;
    }

    public PurchasedItemSelection productIdLike(String... value) {
        addLike(PurchasedItemColumns.PRODUCT_ID, value);
        return this;
    }

    public PurchasedItemSelection productIdContains(String... value) {
        addContains(PurchasedItemColumns.PRODUCT_ID, value);
        return this;
    }

    public PurchasedItemSelection productIdStartsWith(String... value) {
        addStartsWith(PurchasedItemColumns.PRODUCT_ID, value);
        return this;
    }

    public PurchasedItemSelection productIdEndsWith(String... value) {
        addEndsWith(PurchasedItemColumns.PRODUCT_ID, value);
        return this;
    }

    public PurchasedItemSelection orderByProductId(boolean desc) {
        orderBy(PurchasedItemColumns.PRODUCT_ID, desc);
        return this;
    }

    public PurchasedItemSelection orderByProductId() {
        orderBy(PurchasedItemColumns.PRODUCT_ID, false);
        return this;
    }

    public PurchasedItemSelection dateRequested(long... value) {
        addEquals(PurchasedItemColumns.DATE_REQUESTED, toObjectArray(value));
        return this;
    }

    public PurchasedItemSelection dateRequestedNot(long... value) {
        addNotEquals(PurchasedItemColumns.DATE_REQUESTED, toObjectArray(value));
        return this;
    }

    public PurchasedItemSelection dateRequestedGt(long value) {
        addGreaterThan(PurchasedItemColumns.DATE_REQUESTED, value);
        return this;
    }

    public PurchasedItemSelection dateRequestedGtEq(long value) {
        addGreaterThanOrEquals(PurchasedItemColumns.DATE_REQUESTED, value);
        return this;
    }

    public PurchasedItemSelection dateRequestedLt(long value) {
        addLessThan(PurchasedItemColumns.DATE_REQUESTED, value);
        return this;
    }

    public PurchasedItemSelection dateRequestedLtEq(long value) {
        addLessThanOrEquals(PurchasedItemColumns.DATE_REQUESTED, value);
        return this;
    }

    public PurchasedItemSelection orderByDateRequested(boolean desc) {
        orderBy(PurchasedItemColumns.DATE_REQUESTED, desc);
        return this;
    }

    public PurchasedItemSelection orderByDateRequested() {
        orderBy(PurchasedItemColumns.DATE_REQUESTED, false);
        return this;
    }

    public PurchasedItemSelection dateAccepted(long... value) {
        addEquals(PurchasedItemColumns.DATE_ACCEPTED, toObjectArray(value));
        return this;
    }

    public PurchasedItemSelection dateAcceptedNot(long... value) {
        addNotEquals(PurchasedItemColumns.DATE_ACCEPTED, toObjectArray(value));
        return this;
    }

    public PurchasedItemSelection dateAcceptedGt(long value) {
        addGreaterThan(PurchasedItemColumns.DATE_ACCEPTED, value);
        return this;
    }

    public PurchasedItemSelection dateAcceptedGtEq(long value) {
        addGreaterThanOrEquals(PurchasedItemColumns.DATE_ACCEPTED, value);
        return this;
    }

    public PurchasedItemSelection dateAcceptedLt(long value) {
        addLessThan(PurchasedItemColumns.DATE_ACCEPTED, value);
        return this;
    }

    public PurchasedItemSelection dateAcceptedLtEq(long value) {
        addLessThanOrEquals(PurchasedItemColumns.DATE_ACCEPTED, value);
        return this;
    }

    public PurchasedItemSelection orderByDateAccepted(boolean desc) {
        orderBy(PurchasedItemColumns.DATE_ACCEPTED, desc);
        return this;
    }

    public PurchasedItemSelection orderByDateAccepted() {
        orderBy(PurchasedItemColumns.DATE_ACCEPTED, false);
        return this;
    }

    public PurchasedItemSelection accepted(boolean value) {
        addEquals(PurchasedItemColumns.ACCEPTED, toObjectArray(value));
        return this;
    }

    public PurchasedItemSelection orderByAccepted(boolean desc) {
        orderBy(PurchasedItemColumns.ACCEPTED, desc);
        return this;
    }

    public PurchasedItemSelection orderByAccepted() {
        orderBy(PurchasedItemColumns.ACCEPTED, false);
        return this;
    }

    public PurchasedItemSelection completed(boolean value) {
        addEquals(PurchasedItemColumns.COMPLETED, toObjectArray(value));
        return this;
    }

    public PurchasedItemSelection orderByCompleted(boolean desc) {
        orderBy(PurchasedItemColumns.COMPLETED, desc);
        return this;
    }

    public PurchasedItemSelection orderByCompleted() {
        orderBy(PurchasedItemColumns.COMPLETED, false);
        return this;
    }

    public PurchasedItemSelection volume(int... value) {
        addEquals(PurchasedItemColumns.VOLUME, toObjectArray(value));
        return this;
    }

    public PurchasedItemSelection volumeNot(int... value) {
        addNotEquals(PurchasedItemColumns.VOLUME, toObjectArray(value));
        return this;
    }

    public PurchasedItemSelection volumeGt(int value) {
        addGreaterThan(PurchasedItemColumns.VOLUME, value);
        return this;
    }

    public PurchasedItemSelection volumeGtEq(int value) {
        addGreaterThanOrEquals(PurchasedItemColumns.VOLUME, value);
        return this;
    }

    public PurchasedItemSelection volumeLt(int value) {
        addLessThan(PurchasedItemColumns.VOLUME, value);
        return this;
    }

    public PurchasedItemSelection volumeLtEq(int value) {
        addLessThanOrEquals(PurchasedItemColumns.VOLUME, value);
        return this;
    }

    public PurchasedItemSelection orderByVolume(boolean desc) {
        orderBy(PurchasedItemColumns.VOLUME, desc);
        return this;
    }

    public PurchasedItemSelection orderByVolume() {
        orderBy(PurchasedItemColumns.VOLUME, false);
        return this;
    }

    public PurchasedItemSelection totalPrice(int... value) {
        addEquals(PurchasedItemColumns.TOTAL_PRICE, toObjectArray(value));
        return this;
    }

    public PurchasedItemSelection totalPriceNot(int... value) {
        addNotEquals(PurchasedItemColumns.TOTAL_PRICE, toObjectArray(value));
        return this;
    }

    public PurchasedItemSelection totalPriceGt(int value) {
        addGreaterThan(PurchasedItemColumns.TOTAL_PRICE, value);
        return this;
    }

    public PurchasedItemSelection totalPriceGtEq(int value) {
        addGreaterThanOrEquals(PurchasedItemColumns.TOTAL_PRICE, value);
        return this;
    }

    public PurchasedItemSelection totalPriceLt(int value) {
        addLessThan(PurchasedItemColumns.TOTAL_PRICE, value);
        return this;
    }

    public PurchasedItemSelection totalPriceLtEq(int value) {
        addLessThanOrEquals(PurchasedItemColumns.TOTAL_PRICE, value);
        return this;
    }

    public PurchasedItemSelection orderByTotalPrice(boolean desc) {
        orderBy(PurchasedItemColumns.TOTAL_PRICE, desc);
        return this;
    }

    public PurchasedItemSelection orderByTotalPrice() {
        orderBy(PurchasedItemColumns.TOTAL_PRICE, false);
        return this;
    }
}
