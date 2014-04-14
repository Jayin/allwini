package com.allwini.app;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;

import com.allwini.app.fragment.Login;
import com.allwini.app.fragment.Manage;
import com.allwini.app.interfaces.UpdateFragmet;
import com.allwini.app.utils.Constant;

public class Main extends FragmentActivity implements UpdateFragmet {

	FragmentManager fm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		fm = getSupportFragmentManager();
		if (getIntent().getAction().equals(Constant.Action_to_login)) {
			fm.beginTransaction().add(R.id.container, new Login(), "login")
					.commit();
		} else {
			LoginSuccess();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void LoginSuccess() {
		onLoginSuccess();
	}

	@Override
	public void onLoginSuccess() {
		fm.beginTransaction().replace(R.id.container, new Manage(), "manage")
				.commit();
	}

}
