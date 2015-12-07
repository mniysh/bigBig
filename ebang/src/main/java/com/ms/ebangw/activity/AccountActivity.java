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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.ms.ebangw.R;
import com.ms.ebangw.adapter.AccountDetailAdapter;
import com.ms.ebangw.bean.Account;
import com.ms.ebangw.bean.Trade;
import com.ms.ebangw.exception.ResponseException;
import com.ms.ebangw.service.DataAccessUtil;
import com.ms.ebangw.service.DataParseUtil;
import com.ms.ebangw.utils.T;

import org.apache.http.Header;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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
    private String[] years = {"2010", "2011", "2012", "2013", "2014", "2015"};
    private String[] months = {"01", "02", "03", "04", "05", "06",
        "07", "08", "09", "10", "11","12"};

    private int currentPage = 1;
    private AccountDetailAdapter adapter;
    /**
     * 选择的账单时间 2014-11
     */
    private String  dateStr;

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

        ptr.setMode(PullToRefreshBase.Mode.BOTH);
        ptr.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                load();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                loadMore();
            }
        });
    }

    private void setDate(){
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH);
        long timeInMillis = calendar.getTimeInMillis();

        SimpleDateFormat format = new SimpleDateFormat("MM");
        String month = format.format(calendar.getTime());
        tvYear.setText(year + "年");
        tvMonth.setText(month + "");
    }

    @Override
    public void initData() {
        setDate();
        adapter = new AccountDetailAdapter(this, new ArrayList<Trade>());
        ptr.setAdapter(adapter);
        load();
    }



    private String getDateStr() {
        String year = tvYear.getText().toString().trim().replace("年", "");
        String month = tvMonth.getText().toString().trim();
        return year + "-" + month;
    }

    private void load() {

        currentPage = 1;
        DataAccessUtil.account(currentPage + "", getDateStr(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                currentPage++;
                try {
                    Account account = DataParseUtil.account(response);
                    setPayAndIncome(account);
                    List<Trade> list = DataParseUtil.tradeDetail(response);
                    if (adapter != null && list != null && list.size() > 0) {
                        adapter.setList(list);
                        adapter.notifyDataSetChanged();
                    }
                } catch (ResponseException e) {
                    e.printStackTrace();
                    T.show(e.getMessage());
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (null != ptr) {
                    ptr.onRefreshComplete();
                }
            }
        });

    }

    private void loadMore() {

        DataAccessUtil.account(currentPage + "", getDateStr(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                currentPage++;
                try {
                    Account account = DataParseUtil.account(response);
                    setPayAndIncome(account);
                    List<Trade> list = account.getTrade();
                    if (adapter != null && list != null && list.size() > 0) {
                        adapter.getList().addAll(list);
                        adapter.notifyDataSetChanged();
                    }
                } catch (ResponseException e) {
                    e.printStackTrace();
                    T.show(e.getMessage());
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                ptr.onRefreshComplete();
            }
        });
    }

    private void setPayAndIncome(Account account) {
        tvExpend.setText(account.getPay_money());
        tvIncome.setText(account.getIncome_money());
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
                load();
                dialog.dismiss();
            }
        });
    }
}
