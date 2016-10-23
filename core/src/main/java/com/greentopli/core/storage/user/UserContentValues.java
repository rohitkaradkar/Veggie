package com.greentopli.core.storage.user;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.greentopli.core.storage.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code user} table.
 */
public class UserContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return UserColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable UserSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable UserSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public UserContentValues putEmail(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("email must not be null");
        mContentValues.put(UserColumns.EMAIL, value);
        return this;
    }


    public UserContentValues putName(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("name must not be null");
        mContentValues.put(UserColumns.NAME, value);
        return this;
    }


    public UserContentValues putMobileNo(long value) {
        mContentValues.put(UserColumns.MOBILE_NO, value);
        return this;
    }


    public UserContentValues putAddress(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("address must not be null");
        mContentValues.put(UserColumns.ADDRESS, value);
        return this;
    }


    public UserContentValues putPincode(int value) {
        mContentValues.put(UserColumns.PINCODE, value);
        return this;
    }


    /**
     * identification for FCM service
     */
    public UserContentValues putInstanceId(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("instanceId must not be null");
        mContentValues.put(UserColumns.INSTANCE_ID, value);
        return this;
    }


    /**
     * for verifying user auth from Firebase. Used by server
     */
    public UserContentValues putAuthToken(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("authToken must not be null");
        mContentValues.put(UserColumns.AUTH_TOKEN, value);
        return this;
    }

}
