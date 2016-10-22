package com.greentopli.core;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by rnztx on 21/10/16.
 */

public class AppClass extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		Stetho.initializeWithDefaults(this);
	}
}
