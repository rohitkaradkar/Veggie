package com.greentopli.core.handler;

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

public class UserDbHandler {
	private Context context;

	public UserDbHandler(Context context) {
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
		return user;
	}

	public User getUserInfo(@NonNull String email){
		if (isUserInfoAvailable(email)){

			UserSelection where = new UserSelection();
			where.email(email);
			UserCursor cursor = where.query(context.getContentResolver(), UserColumns.ALL_COLUMNS);

			User user = new User();
			while (cursor.moveToNext()){
				user = getUserInfo(cursor);
			}
			cursor.close();
			return user;
		}
		return null;
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
