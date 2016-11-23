package com.greentopli.core.storage.helper;

import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.greentopli.core.storage.user.UserColumns;
import com.greentopli.core.storage.user.UserContentValues;
import com.greentopli.core.storage.user.UserCursor;
import com.greentopli.core.storage.user.UserSelection;
import com.greentopli.model.User;

/**
 * Created by rnztx on 23/10/16.
 */

public class UserDbHelper {
	private Context context;

	public UserDbHelper(Context context) {
		this.context = context;
	}

	public long storeUserInfo(@NonNull User user){
		UserContentValues values = new UserContentValues();
		values.putEmail(user.getEmail());
		values.putName(user.getName());
		values.putMobileNo(user.getMobileNo());
		values.putAddress(user.getAddress());
		values.putPincode(user.getPincode());
		values.putInstanceId(user.getInstanceId());
		values.putAuthToken(user.getAuthToken());
		values.putPhotoUrl(user.getPhotoUrl());
		Uri uri = values.insert(context.getContentResolver());
		return ContentUris.parseId(uri);
	}

	public User getUserInfo(@NonNull UserCursor cursor){
		User user = new User();
		user.setEmail(cursor.getEmail());
		user.setName(cursor.getName());
		user.setMobileNo(cursor.getMobileNo());
		user.setAddress(cursor.getAddress());
		user.setPincode(cursor.getPincode());
		user.setInstanceId(cursor.getInstanceId());
		user.setAuthToken(cursor.getAuthToken());
		user.setPhotoUrl(cursor.getPhotoUrl());
		return user;
	}

	public User getSignedUserInfo(){
		UserSelection where = new UserSelection();
		UserCursor cursor = where.query(context.getContentResolver(), UserColumns.ALL_COLUMNS);
		User user = null;
		if(cursor.moveToNext())
			user = getUserInfo(cursor);
		cursor.close();
		return user;
	}

	public long removeUserInfo(){
		UserSelection where = new UserSelection();
//		where.email(email);
		return where.delete(context);
	}
	public boolean isUserInfoAvailable(@NonNull String email){
		try {
			UserSelection where = new UserSelection();
			where.email(email);
			UserCursor cursor = where.query(context.getContentResolver(), UserColumns.ALL_COLUMNS);
			boolean isAvailable = cursor.moveToNext();
			cursor.close();
			return isAvailable;
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

}
