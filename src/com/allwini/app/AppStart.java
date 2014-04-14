package com.allwini.app;

import com.allwini.app.utils.Constant;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

public class AppStart extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acty_appstart);
		BroadcastReceiver receiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				String action = intent.getAction();
				intent = new Intent(AppStart.this, Main.class);
				if (action !=null && action.equals(Constant.Action_New_Msg)) {
					intent.setAction(Constant.Action_New_Msg);
				} else {
					intent.setAction(Constant.Action_to_login);
				}
				startActivity(intent);
				finish();
			}
		};
		
	    IntentFilter filter = new IntentFilter();
	    filter.addAction(Constant.Action_New_Msg);
	    filter.addAction(Constant.Action_to_login);
	    registerReceiver(receiver, filter);
	    
	    Intent service = new Intent(this, CoreService.class);
	    service.setAction(CoreService.Action_getMsg);
	    startService(service);
	}

}
