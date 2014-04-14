package com.allwini.app;

import com.allwini.app.api.BaseClient;

import android.app.Application;

public class App extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		BaseClient.init(getApplicationContext());
	}
}
