package com.greentopli.core.storage.purchaseditem;

import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.greentopli.core.storage.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code purchased_item} table.
 */
public class PurchasedItemCursor extends AbstractCursor implements PurchasedItemModel {
    public PurchasedItemCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(PurchasedItemColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Unique id for purchasing entity
     * Cannot be {@code null}.
     */
    @NonNull
    public String getPurchaseId() {
        String res = getStringOrNull(PurchasedItemColumns.PURCHASE_ID);
        if (res == null)
            throw new NullPointerException("The value of 'purchase_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Unique id for user
     * Cannot be {@code null}.
     */
    @NonNull
    public String getUserId() {
        String res = getStringOrNull(PurchasedItemColumns.USER_ID);
        if (res == null)
            throw new NullPointerException("The value of 'user_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * product that user is purchasing
     * Cannot be {@code null}.
     */
    @NonNull
    public String getProductId() {
        String res = getStringOrNull(PurchasedItemColumns.PRODUCT_ID);
        if (res == null)
            throw new NullPointerException("The value of 'product_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code date_requested} value.
     */
    public long getDateRequested() {
        Long res = getLongOrNull(PurchasedItemColumns.DATE_REQUESTED);
        if (res == null)
            throw new NullPointerException("The value of 'date_requested' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code date_accepted} value.
     */
    public long getDateAccepted() {
        Long res = getLongOrNull(PurchasedItemColumns.DATE_ACCEPTED);
        if (res == null)
            throw new NullPointerException("The value of 'date_accepted' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code accepted} value.
     */
    public boolean getAccepted() {
        Boolean res = getBooleanOrNull(PurchasedItemColumns.ACCEPTED);
        if (res == null)
            throw new NullPointerException("The value of 'accepted' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code completed} value.
     */
    public boolean getCompleted() {
        Boolean res = getBooleanOrNull(PurchasedItemColumns.COMPLETED);
        if (res == null)
            throw new NullPointerException("The value of 'completed' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * volume of purchasing item eg. 6pcs or 250gms
     */
    public int getVolume() {
        Integer res = getIntegerOrNull(PurchasedItemColumns.VOLUME);
        if (res == null)
            throw new NullPointerException("The value of 'volume' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Total price
     */
    public int getTotalPrice() {
        Integer res = getIntegerOrNull(PurchasedItemColumns.TOTAL_PRICE);
        if (res == null)
            throw new NullPointerException("The value of 'total_price' in the database was null, which is not allowed according to the model definition");
        return res;
    }
}
