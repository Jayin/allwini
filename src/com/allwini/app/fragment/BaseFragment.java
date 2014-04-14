package com.allwini.app.fragment;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class BaseFragment extends Fragment implements OnClickListener{

	public void toast(String text){
		Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onClick(View v) {
		 
		
	}
}
