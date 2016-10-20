package com.greentopli.core.storage.product;

import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.greentopli.core.storage.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code product} table.
 */
public class ProductCursor extends AbstractCursor implements ProductModel {
    public ProductCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(ProductColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Unique Product Identificaiotn
     * Cannot be {@code null}.
     */
    @NonNull
    public String getProductId() {
        String res = getStringOrNull(ProductColumns.PRODUCT_ID);
        if (res == null)
            throw new NullPointerException("The value of 'product_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * English name of product
     * Cannot be {@code null}.
     */
    @NonNull
    public String getNameEnglish() {
        String res = getStringOrNull(ProductColumns.NAME_ENGLISH);
        if (res == null)
            throw new NullPointerException("The value of 'name_english' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Hindi English name
     * Cannot be {@code null}.
     */
    @NonNull
    public String getNameHinglish() {
        String res = getStringOrNull(ProductColumns.NAME_HINGLISH);
        if (res == null)
            throw new NullPointerException("The value of 'name_hinglish' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Image Download Url
     * Cannot be {@code null}.
     */
    @NonNull
    public String getImageUrl() {
        String res = getStringOrNull(ProductColumns.IMAGE_URL);
        if (res == null)
            throw new NullPointerException("The value of 'image_url' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * minimum volume that user has to purchase
     */
    public int getMinVolume() {
        Integer res = getIntegerOrNull(ProductColumns.MIN_VOLUME);
        if (res == null)
            throw new NullPointerException("The value of 'min_volume' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     *  maximum volume that user is allowed to buy
     */
    public int getMaxVolume() {
        Integer res = getIntegerOrNull(ProductColumns.MAX_VOLUME);
        if (res == null)
            throw new NullPointerException("The value of 'max_volume' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * eg. set 400 gms min user will be to purchase item only in quantity of 400, 800, 1200, 1600, 2000 gms
     */
    public int getVolumeSet() {
        Integer res = getIntegerOrNull(ProductColumns.VOLUME_SET);
        if (res == null)
            throw new NullPointerException("The value of 'volume_set' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code price} value.
     */
    public int getPrice() {
        Integer res = getIntegerOrNull(ProductColumns.PRICE);
        if (res == null)
            throw new NullPointerException("The value of 'price' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code time} value.
     */
    public long getTime() {
        Long res = getLongOrNull(ProductColumns.TIME);
        if (res == null)
            throw new NullPointerException("The value of 'time' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code type} value.
     * Cannot be {@code null}.
     */
    @NonNull
    public String getType() {
        String res = getStringOrNull(ProductColumns.TYPE);
        if (res == null)
            throw new NullPointerException("The value of 'type' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code volume} value.
     * Cannot be {@code null}.
     */
    @NonNull
    public String getVolume() {
        String res = getStringOrNull(ProductColumns.VOLUME);
        if (res == null)
            throw new NullPointerException("The value of 'volume' in the database was null, which is not allowed according to the model definition");
        return res;
    }
}
