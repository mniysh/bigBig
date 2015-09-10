package com.ms.ebangw.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

public class BaseFragment extends Fragment {
	public View findviewbyid(int id){
		
		return getView().findViewById(id);
		
	}
	public void toast(Context context,String str){
		Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
	}
}
