package com.ms.ebangw.activity;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class BaseActivity extends AppCompatActivity {

	public void toast(String str){
		Toast.makeText(this, str, Toast.LENGTH_LONG).show();
	}
}
