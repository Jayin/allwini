package com.allwini.app;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.allwini.app.api.AllwiniAPI;
import com.allwini.app.utils.BaseNotification;
import com.allwini.app.utils.Constant;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class CoreService extends Service {
	private int intervalMillis = 5 * 1000;
	public static final String Action_getMsg = "get_message";

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(getApplicationContext(), CoreService.class);
		intent.setAction(Action_getMsg);
		PendingIntent operation = PendingIntent.getService(
				getApplicationContext(), 0, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
				intervalMillis, operation);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		String action = intent.getAction();
		Log.i("debug", "onStartCommand-->start!");
		if (action != null) {
			Thread t = null;

			if (Action_getMsg.equals(action)) {
				t = new GetMsgTask();
			}

			if (t != null) {
				t.start();
			}
		}
		return Service.START_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(getApplicationContext(), CoreService.class);
		intent.setAction(Action_getMsg);
		PendingIntent operation = PendingIntent.getService(
				getApplicationContext(), 0, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		am.cancel(operation);
	}

	class GetMsgTask extends Thread {

		@Override
		public void run() {
			Log.i("debug", "GetMsgTask-->start!");
			AllwiniAPI api = new AllwiniAPI();
			api.getMsg(new AsyncHttpResponseHandler() {
				@Override
				public void onSuccess(int arg0, Header[] arg1, byte[] data) {
					try {
						JSONObject obj = new JSONObject(new String(data));
						int count = obj.getInt("retval");
						Intent intent = new Intent(Constant.Action_New_Msg);
						intent.putExtra("count", count);
						sendBroadcast(intent);
						if (count > 0) {
							BaseNotification notification = new BaseNotification(
									getBaseContext());
							notification.setContentText("你有" + count + "条消息");
							notification.setNotificationID(1);
							notification.show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
						// 解析失败 登录失败
						sendBroadcast(new Intent(Constant.Action_to_login));
					}

				}

				@Override
				public void onFailure(int arg0, Header[] arg1, byte[] arg2,
						Throwable arg3) {
					sendBroadcast(new Intent(Constant.Action_to_login)); //网络异常
				}
			});
		}
	}
}
