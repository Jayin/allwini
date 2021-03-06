package com.allwini.app.fragment;

import org.apache.http.Header;

import com.allwini.app.Main;
import com.allwini.app.R;
import com.allwini.app.api.AllwiniAPI;
import com.allwini.app.utils.DataPool;
import com.loopj.android.http.AsyncHttpResponseHandler;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

public class Login extends BaseFragment {
	EditText username, psw;
	ProgressBar pb ;
    DataPool dp ;
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	dp = new DataPool(getActivity());
    }
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_login, container, false);
		username = (EditText) v.findViewById(R.id.et_username);
		psw = (EditText) v.findViewById(R.id.et_psw);
		v.findViewById(R.id.btn_login).setOnClickListener(this);
		pb =(ProgressBar) v.findViewById(R.id.progressBar);
		String _name = (String)dp.get("username");
		String _psw = (String)dp.get("password");
		if(_name!=null){
			username.setText(_name);
		}
		if(_psw!=null){
			psw.setText(_psw);
		}
		return v;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_login:
			if ("".equals(username.getText().toString())) {
				toast("不能为空");
				return;
			}
			if ("".equals(psw.getText().toString())) {
				toast("不能为空");
				return;
			}
			dp.put("username", username.getText().toString());
			dp.put("password", psw.getText().toString());
			AllwiniAPI api = new AllwiniAPI();
			api.login(username.getText().toString(), psw.getText().toString(), new AsyncHttpResponseHandler() {
				@Override
				public void onStart() {
					pb.setVisibility(View.VISIBLE);
				}

				@Override
				public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
					 ((Main)getActivity()).LoginSuccess();
//					 Log.i("debug", new String(arg2));
				}

				@Override
				public void onFailure(int arg0, Header[] arg1, byte[] arg2,
						Throwable arg3) {
					 toast("登录失败");
				}

				@Override
				public void onFinish() {
					pb.setVisibility(View.INVISIBLE);
				}
			});
			break;
		default:
			break;
		}
	}
}
