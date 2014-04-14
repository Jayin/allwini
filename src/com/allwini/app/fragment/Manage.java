package com.allwini.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.allwini.app.R;

public class Manage extends BaseFragment {
	TextView status;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_manager, container, false);
		status =(TextView) v.findViewById(R.id.tv_status);
		return v;
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}
}
