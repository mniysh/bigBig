package com.ms.ebangw.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.ms.ebangw.R;

/**
 * 支付页面
 * @author admin
 *
 */
public class SettlementActivity extends BaseActivity {

	@Override
	public void initView() {
		initTitle(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SettlementActivity.this.finish();
			}
		},"返回","结算",null,null);
	}

	@Override
	public void initData() {

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settlement);
		initView();
		initData();

	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
