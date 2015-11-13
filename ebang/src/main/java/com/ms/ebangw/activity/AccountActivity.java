package com.ms.ebangw.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ms.ebangw.R;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import kan.wheel.widget.StringWheelAdapter;
import kan.wheel.widget.WheelView;

/**
 * 帐户
 *
 * @author wangkai
 */
public class AccountActivity extends BaseActivity {


    @Bind(R.id.tv_year)
    TextView tvYear;
    @Bind(R.id.tv_month)
    TextView tvMonth;
    @Bind(R.id.rl_date_select)
    RelativeLayout rlDateSelect;
    @Bind(R.id.tv_expend)
    TextView tvExpend;
    @Bind(R.id.tv_income)
    TextView tvIncome;
    @Bind(R.id.ptr)
    PullToRefreshListView ptr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        ButterKnife.bind(this);
        initView();
        initData();
    }


    @Override
    public void initView() {
        initTitle(null, "返回", "账单", "交易明细", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountActivity.this, AccountDetailActivity.class);
                startActivity(intent);
            }
        });

        rlDateSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateSelectPopup(AccountActivity.this);
            }
        });
    }

    @Override
    public void initData() {

    }

    public void showDateSelectPopup(Activity context) {
        Display display = context.getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        final Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setContentView(R.layout.year_month_select);

        final WheelView yearWv = (WheelView) window.findViewById(R.id.wv_year);
        final WheelView monthWv = (WheelView) window.findViewById(R.id.wv_month);
//		wheelView.setWheelBackground(R.drawable.wheel_bg_holo);
//		wheelView.setWheelForeground(R.drawable.wheel_val_holo);
//        yearWv.setShadowColor(0x00000000, 0x00000000, 0x00000000);// 设置前景色颜色
//        monthWv.setShadowColor(0x00000000, 0x00000000, 0x00000000);// 设置前景色颜色
        yearWv.setVisibleItems(3);
//        yearWv.setAdapter(new WheelViewSelectAdapter(AccountActivity.this, R.layout
//            .wv_select_item, R.id.tv_name, years));

        StringWheelAdapter yearWheelAdapter = new StringWheelAdapter(Arrays.asList(years));
        StringWheelAdapter monthWheelAdapter = new StringWheelAdapter(Arrays.asList(months));
        yearWv.setAdapter(yearWheelAdapter);
        yearWv.setCurrentItem(1);

        monthWv.setVisibleItems(3);
//        monthWv.setViewAdapter(new WheelViewSelectAdapter(AccountActivity.this, R.layout.wv_select_item, R.id
//            .tv_name, months));
        monthWv.setAdapter(monthWheelAdapter);
        monthWv.setCurrentItem(1);

        TextView cancelTv = (TextView) window.findViewById(R.id.tv_cancel);
        TextView okTv = (TextView) window.findViewById(R.id.tv_ok);
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        okTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int yearItem = yearWv.getCurrentItem();
                int monthItem = monthWv.getCurrentItem();

                String year = years[yearItem];
                String month = months[monthItem];
                tvYear.setText(year+"年");
                tvMonth.setText(month);
                dialog.dismiss();
            }
        });
    }

    private String[] years = {"2010", "2011", "2012", "2013", "2014", "2015"};
    private String[] months = {"01", "02", "03", "04", "05", "06",
        "07", "08", "09", "10", "11","12"};




}
