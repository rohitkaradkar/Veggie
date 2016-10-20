package com.greentopli.core;

import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;

/**
 * Created by rnztx on 20/10/16.
 */

public class CoreApp extends Application {
	private static CoreApp instance;
	public CoreApp(){
		instance = this;
	}
	public static Context getContext(){
		if (instance==null)
			instance = new CoreApp();

		return instance;
	}
	public static ContentResolver getResolver(){
		return instance.getApplicationContext().getContentResolver();
	}
}
