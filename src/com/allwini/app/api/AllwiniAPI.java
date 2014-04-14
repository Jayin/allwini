package com.allwini.app.api;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class AllwiniAPI {

	public AllwiniAPI() {

	}

	public void login(String username, String password,
			AsyncHttpResponseHandler responseHandler) {
		RequestParams params = new RequestParams();
		params.add("user_name", username);
		params.add("password", password);
		params.add("ret_url", "index.php?app=message&act=checknew");
		BaseClient.get("http://www.allwini.com/index.php?app=member&act=login",
				params, responseHandler);
	}
	
	public void getMsg(AsyncHttpResponseHandler responseHandler){
		BaseClient.get("http://www.allwini.com/index.php?app=message&act=checknew", null, responseHandler);
	}
}
