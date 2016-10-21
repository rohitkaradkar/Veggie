package com.greentopli.core.storage.user;

import com.greentopli.core.storage.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Order Information
 */
public interface UserModel extends BaseModel {

    /**
     * Get the {@code user_id} value.
     * Cannot be {@code null}.
     */
    @NonNull
    String getUserId();

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
    int getMobileNo();

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
}
