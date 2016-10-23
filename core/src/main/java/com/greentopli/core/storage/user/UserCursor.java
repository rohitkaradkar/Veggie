package com.greentopli.core.storage.user;

import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.greentopli.core.storage.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code user} table.
 */
public class UserCursor extends AbstractCursor implements UserModel {
    public UserCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(UserColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code email} value.
     * Cannot be {@code null}.
     */
    @NonNull
    public String getEmail() {
        String res = getStringOrNull(UserColumns.EMAIL);
        if (res == null)
            throw new NullPointerException("The value of 'email' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code name} value.
     * Cannot be {@code null}.
     */
    @NonNull
    public String getName() {
        String res = getStringOrNull(UserColumns.NAME);
        if (res == null)
            throw new NullPointerException("The value of 'name' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code mobile_no} value.
     */
    public long getMobileNo() {
        Long res = getLongOrNull(UserColumns.MOBILE_NO);
        if (res == null)
            throw new NullPointerException("The value of 'mobile_no' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code address} value.
     * Cannot be {@code null}.
     */
    @NonNull
    public String getAddress() {
        String res = getStringOrNull(UserColumns.ADDRESS);
        if (res == null)
            throw new NullPointerException("The value of 'address' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code pincode} value.
     */
    public int getPincode() {
        Integer res = getIntegerOrNull(UserColumns.PINCODE);
        if (res == null)
            throw new NullPointerException("The value of 'pincode' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * identification for FCM service
     * Cannot be {@code null}.
     */
    @NonNull
    public String getInstanceId() {
        String res = getStringOrNull(UserColumns.INSTANCE_ID);
        if (res == null)
            throw new NullPointerException("The value of 'instance_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * for verifying user auth from Firebase. Used by server
     * Cannot be {@code null}.
     */
    @NonNull
    public String getAuthToken() {
        String res = getStringOrNull(UserColumns.AUTH_TOKEN);
        if (res == null)
            throw new NullPointerException("The value of 'auth_token' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code photo_url} value.
     * Cannot be {@code null}.
     */
    @NonNull
    public String getPhotoUrl() {
        String res = getStringOrNull(UserColumns.PHOTO_URL);
        if (res == null)
            throw new NullPointerException("The value of 'photo_url' in the database was null, which is not allowed according to the model definition");
        return res;
    }
}
