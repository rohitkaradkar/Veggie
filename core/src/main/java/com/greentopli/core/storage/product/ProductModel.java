package com.greentopli.core.storage.product;

import com.greentopli.core.storage.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * The Product Item wich user is will purchase.
 */
public interface ProductModel extends BaseModel {

    /**
     * Unique Product Identificaiotn
     * Cannot be {@code null}.
     */
    @NonNull
    String getProductId();

    /**
     * English name of product
     * Cannot be {@code null}.
     */
    @NonNull
    String getNameEnglish();

    /**
     * Hindi English name
     * Cannot be {@code null}.
     */
    @NonNull
    String getNameHinglish();

    /**
     * Image Download Url
     * Cannot be {@code null}.
     */
    @NonNull
    String getImageUrl();

    /**
     * minimum volume that user has to purchase
     */
    int getMinVolume();

    /**
     *  maximum volume that user is allowed to buy
     */
    int getMaxVolume();

    /**
     * eg. set 400 gms min user will be to purchase item only in quantity of 400, 800, 1200, 1600, 2000 gms
     */
    int getVolumeSet();

    /**
     * Get the {@code price} value.
     */
    int getPrice();

    /**
     * Get the {@code time} value.
     */
    long getTime();

    /**
     * Get the {@code type} value.
     * Cannot be {@code null}.
     */
    @NonNull
    String getType();

    /**
     * Get the {@code volume} value.
     * Cannot be {@code null}.
     */
    @NonNull
    String getVolume();
}
