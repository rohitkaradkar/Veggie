package com.greentopli.core.storage.user;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.greentopli.core.storage.base.AbstractSelection;

/**
 * Selection for the {@code user} table.
 */
public class UserSelection extends AbstractSelection<UserSelection> {
    @Override
    protected Uri baseUri() {
        return UserColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code UserCursor} object, which is positioned before the first entry, or null.
     */
    public UserCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new UserCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public UserCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code UserCursor} object, which is positioned before the first entry, or null.
     */
    public UserCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new UserCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public UserCursor query(Context context) {
        return query(context, null);
    }


    public UserSelection id(long... value) {
        addEquals("user." + UserColumns._ID, toObjectArray(value));
        return this;
    }

    public UserSelection idNot(long... value) {
        addNotEquals("user." + UserColumns._ID, toObjectArray(value));
        return this;
    }

    public UserSelection orderById(boolean desc) {
        orderBy("user." + UserColumns._ID, desc);
        return this;
    }

    public UserSelection orderById() {
        return orderById(false);
    }

    public UserSelection email(String... value) {
        addEquals(UserColumns.EMAIL, value);
        return this;
    }

    public UserSelection emailNot(String... value) {
        addNotEquals(UserColumns.EMAIL, value);
        return this;
    }

    public UserSelection emailLike(String... value) {
        addLike(UserColumns.EMAIL, value);
        return this;
    }

    public UserSelection emailContains(String... value) {
        addContains(UserColumns.EMAIL, value);
        return this;
    }

    public UserSelection emailStartsWith(String... value) {
        addStartsWith(UserColumns.EMAIL, value);
        return this;
    }

    public UserSelection emailEndsWith(String... value) {
        addEndsWith(UserColumns.EMAIL, value);
        return this;
    }

    public UserSelection orderByEmail(boolean desc) {
        orderBy(UserColumns.EMAIL, desc);
        return this;
    }

    public UserSelection orderByEmail() {
        orderBy(UserColumns.EMAIL, false);
        return this;
    }

    public UserSelection name(String... value) {
        addEquals(UserColumns.NAME, value);
        return this;
    }

    public UserSelection nameNot(String... value) {
        addNotEquals(UserColumns.NAME, value);
        return this;
    }

    public UserSelection nameLike(String... value) {
        addLike(UserColumns.NAME, value);
        return this;
    }

    public UserSelection nameContains(String... value) {
        addContains(UserColumns.NAME, value);
        return this;
    }

    public UserSelection nameStartsWith(String... value) {
        addStartsWith(UserColumns.NAME, value);
        return this;
    }

    public UserSelection nameEndsWith(String... value) {
        addEndsWith(UserColumns.NAME, value);
        return this;
    }

    public UserSelection orderByName(boolean desc) {
        orderBy(UserColumns.NAME, desc);
        return this;
    }

    public UserSelection orderByName() {
        orderBy(UserColumns.NAME, false);
        return this;
    }

    public UserSelection mobileNo(long... value) {
        addEquals(UserColumns.MOBILE_NO, toObjectArray(value));
        return this;
    }

    public UserSelection mobileNoNot(long... value) {
        addNotEquals(UserColumns.MOBILE_NO, toObjectArray(value));
        return this;
    }

    public UserSelection mobileNoGt(long value) {
        addGreaterThan(UserColumns.MOBILE_NO, value);
        return this;
    }

    public UserSelection mobileNoGtEq(long value) {
        addGreaterThanOrEquals(UserColumns.MOBILE_NO, value);
        return this;
    }

    public UserSelection mobileNoLt(long value) {
        addLessThan(UserColumns.MOBILE_NO, value);
        return this;
    }

    public UserSelection mobileNoLtEq(long value) {
        addLessThanOrEquals(UserColumns.MOBILE_NO, value);
        return this;
    }

    public UserSelection orderByMobileNo(boolean desc) {
        orderBy(UserColumns.MOBILE_NO, desc);
        return this;
    }

    public UserSelection orderByMobileNo() {
        orderBy(UserColumns.MOBILE_NO, false);
        return this;
    }

    public UserSelection address(String... value) {
        addEquals(UserColumns.ADDRESS, value);
        return this;
    }

    public UserSelection addressNot(String... value) {
        addNotEquals(UserColumns.ADDRESS, value);
        return this;
    }

    public UserSelection addressLike(String... value) {
        addLike(UserColumns.ADDRESS, value);
        return this;
    }

    public UserSelection addressContains(String... value) {
        addContains(UserColumns.ADDRESS, value);
        return this;
    }

    public UserSelection addressStartsWith(String... value) {
        addStartsWith(UserColumns.ADDRESS, value);
        return this;
    }

    public UserSelection addressEndsWith(String... value) {
        addEndsWith(UserColumns.ADDRESS, value);
        return this;
    }

    public UserSelection orderByAddress(boolean desc) {
        orderBy(UserColumns.ADDRESS, desc);
        return this;
    }

    public UserSelection orderByAddress() {
        orderBy(UserColumns.ADDRESS, false);
        return this;
    }

    public UserSelection pincode(int... value) {
        addEquals(UserColumns.PINCODE, toObjectArray(value));
        return this;
    }

    public UserSelection pincodeNot(int... value) {
        addNotEquals(UserColumns.PINCODE, toObjectArray(value));
        return this;
    }

    public UserSelection pincodeGt(int value) {
        addGreaterThan(UserColumns.PINCODE, value);
        return this;
    }

    public UserSelection pincodeGtEq(int value) {
        addGreaterThanOrEquals(UserColumns.PINCODE, value);
        return this;
    }

    public UserSelection pincodeLt(int value) {
        addLessThan(UserColumns.PINCODE, value);
        return this;
    }

    public UserSelection pincodeLtEq(int value) {
        addLessThanOrEquals(UserColumns.PINCODE, value);
        return this;
    }

    public UserSelection orderByPincode(boolean desc) {
        orderBy(UserColumns.PINCODE, desc);
        return this;
    }

    public UserSelection orderByPincode() {
        orderBy(UserColumns.PINCODE, false);
        return this;
    }

    public UserSelection instanceId(String... value) {
        addEquals(UserColumns.INSTANCE_ID, value);
        return this;
    }

    public UserSelection instanceIdNot(String... value) {
        addNotEquals(UserColumns.INSTANCE_ID, value);
        return this;
    }

    public UserSelection instanceIdLike(String... value) {
        addLike(UserColumns.INSTANCE_ID, value);
        return this;
    }

    public UserSelection instanceIdContains(String... value) {
        addContains(UserColumns.INSTANCE_ID, value);
        return this;
    }

    public UserSelection instanceIdStartsWith(String... value) {
        addStartsWith(UserColumns.INSTANCE_ID, value);
        return this;
    }

    public UserSelection instanceIdEndsWith(String... value) {
        addEndsWith(UserColumns.INSTANCE_ID, value);
        return this;
    }

    public UserSelection orderByInstanceId(boolean desc) {
        orderBy(UserColumns.INSTANCE_ID, desc);
        return this;
    }

    public UserSelection orderByInstanceId() {
        orderBy(UserColumns.INSTANCE_ID, false);
        return this;
    }

    public UserSelection authToken(String... value) {
        addEquals(UserColumns.AUTH_TOKEN, value);
        return this;
    }

    public UserSelection authTokenNot(String... value) {
        addNotEquals(UserColumns.AUTH_TOKEN, value);
        return this;
    }

    public UserSelection authTokenLike(String... value) {
        addLike(UserColumns.AUTH_TOKEN, value);
        return this;
    }

    public UserSelection authTokenContains(String... value) {
        addContains(UserColumns.AUTH_TOKEN, value);
        return this;
    }

    public UserSelection authTokenStartsWith(String... value) {
        addStartsWith(UserColumns.AUTH_TOKEN, value);
        return this;
    }

    public UserSelection authTokenEndsWith(String... value) {
        addEndsWith(UserColumns.AUTH_TOKEN, value);
        return this;
    }

    public UserSelection orderByAuthToken(boolean desc) {
        orderBy(UserColumns.AUTH_TOKEN, desc);
        return this;
    }

    public UserSelection orderByAuthToken() {
        orderBy(UserColumns.AUTH_TOKEN, false);
        return this;
    }
}
