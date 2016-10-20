package com.greentopli.core.storage.product;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.greentopli.core.storage.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code product} table.
 */
public class ProductContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return ProductColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable ProductSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable ProductSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Unique Product Identificaiotn
     */
    public ProductContentValues putProductId(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("productId must not be null");
        mContentValues.put(ProductColumns.PRODUCT_ID, value);
        return this;
    }


    /**
     * English name of product
     */
    public ProductContentValues putNameEnglish(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("nameEnglish must not be null");
        mContentValues.put(ProductColumns.NAME_ENGLISH, value);
        return this;
    }


    /**
     * Hindi English name
     */
    public ProductContentValues putNameHinglish(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("nameHinglish must not be null");
        mContentValues.put(ProductColumns.NAME_HINGLISH, value);
        return this;
    }


    /**
     * Image Download Url
     */
    public ProductContentValues putImageUrl(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("imageUrl must not be null");
        mContentValues.put(ProductColumns.IMAGE_URL, value);
        return this;
    }


    /**
     * minimum volume that user has to purchase
     */
    public ProductContentValues putMinVolume(int value) {
        mContentValues.put(ProductColumns.MIN_VOLUME, value);
        return this;
    }


    /**
     *  maximum volume that user is allowed to buy
     */
    public ProductContentValues putMaxVolume(int value) {
        mContentValues.put(ProductColumns.MAX_VOLUME, value);
        return this;
    }


    /**
     * eg. set 400 gms min user will be to purchase item only in quantity of 400, 800, 1200, 1600, 2000 gms
     */
    public ProductContentValues putVolumeSet(int value) {
        mContentValues.put(ProductColumns.VOLUME_SET, value);
        return this;
    }


    public ProductContentValues putPrice(int value) {
        mContentValues.put(ProductColumns.PRICE, value);
        return this;
    }


    public ProductContentValues putTime(long value) {
        mContentValues.put(ProductColumns.TIME, value);
        return this;
    }


    public ProductContentValues putType(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("type must not be null");
        mContentValues.put(ProductColumns.TYPE, value);
        return this;
    }


    public ProductContentValues putVolume(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("volume must not be null");
        mContentValues.put(ProductColumns.VOLUME, value);
        return this;
    }

}
