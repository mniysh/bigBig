package com.ms.ebangw.activity;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.ms.ebangw.R;
import com.ms.ebangw.adapter.ContactAdapter;
import com.ms.ebangw.bean.ContactInfo;
import com.ms.ebangw.view.QuickindexBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 用户中心， 邀请好友
 * @author wangkai
 */
public class InviteFriendsActivity extends BaseActivity {
    private Handler handler;
    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.slideBar)
    QuickindexBar slideBar;
    @Bind(R.id.tv_zimu)
    TextView tvZimu;

    private Map<Integer, ContactInfo> contactIdMap = null;
    private List<ContactInfo> list;
    private ContactAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_friends);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    public void initView() {
        initTitle(null, "返回", "邀请好友", null, null);
        slideBar.setOnSlideTouchListener(new QuickindexBar.OnSlideTouchListener() {

            @Override
            public void onBack(String str) {
                showZimu(str);
                if (list != null && list.size() > 0) {
                    int size = list.size();
                    for (int i = 0; i < size; i++) {
                        if (list.get(i).getPinyin().substring(0, 1).equals(str)) {
                            listView.setSelection(i);
                            break;
                        }
                    }
                }
            }
        });
    }

    // 显示在屏幕中间的字母
    private void showZimu(String string) {

        tvZimu.setVisibility(View.VISIBLE);
        tvZimu.setText(string);
        handler.removeCallbacksAndMessages(null);
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                tvZimu.setVisibility(View.GONE);
            }
        }, 1500);
    }

    public  void  invite(String smsBody, String phone){
        Uri smsToUri = Uri.parse( "smsto:" );
        Intent sendIntent =  new  Intent(Intent.ACTION_VIEW, smsToUri);
        //sendIntent.putExtra("address", "123456"); // 电话号码，这行去掉的话，默认就没有电话
        if (!TextUtils.isEmpty(phone)) {
            sendIntent.putExtra("address", phone);
        }
        //短信内容
        sendIntent.putExtra("sms_body", smsBody);
        sendIntent.setType("vnd.android-dir/mms-sms");
        startActivityForResult(sendIntent, 1002);
    }

    @Override
    public void initData() {
        handler = new Handler();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI; // 联系人的Uri
        String[] projection = {
            ContactsContract.CommonDataKinds.Phone._ID,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.DATA1,
            "sort_key",
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.CommonDataKinds.Phone.PHOTO_ID,
            ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY
        }; // 查询的列

        MyAsyncQueryHandler myAsyncQueryHandler = new MyAsyncQueryHandler(getContentResolver());

        myAsyncQueryHandler.startQuery(0, null, uri, projection, null, null,
            "sort_key COLLATE LOCALIZED asc"); // 按照sort_key升序查询

    }

        /**
         * 数据库异步查询类AsyncQueryHandler
         *
         * @author administrator
         *
         */
    private class MyAsyncQueryHandler extends AsyncQueryHandler {

        public MyAsyncQueryHandler(ContentResolver cr) {
            super(cr);
        }

        /**
         * 查询结束的回调函数
         */
        @Override
        protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {

                contactIdMap = new HashMap<Integer, ContactInfo>();

                list = new ArrayList<>();
                cursor.moveToFirst();
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToPosition(i);
                    String name = cursor.getString(1);
                    String number = cursor.getString(2);
                    String sortKey = cursor.getString(3);
                    int contactId = cursor.getInt(4);
                    Long photoId = cursor.getLong(5);
                    String lookUpKey = cursor.getString(6);

                    if (contactIdMap.containsKey(contactId)) {

                    }else{

                        ContactInfo cb = new ContactInfo();
                        cb.setDisplayName(name);
//					if (number.startsWith("+86")) {// 去除多余的中国地区号码标志，对这个程序没有影响。
//						cb.setPhoneNum(number.substring(3));
//					} else {
                        cb.setPhoneNum(number);
//					}
                        cb.setSortKey(sortKey);
                        cb.setContactId(contactId);
                        cb.setPhotoId(photoId);
                        cb.setLookUpKey(lookUpKey);
                        list.add(cb);

                        contactIdMap.put(contactId, cb);
                    }
                }
                if (list.size() > 0) {
                    setAdapter(list);
                }
            }
        }
    }

    private void setAdapter(List<ContactInfo> list) {
        if (null != list && list.size() > 0) {
            adapter = new ContactAdapter(list);
            adapter.setOnInviteListener(new ContactAdapter.OnInviteListener() {
                @Override
                public void onInvite(String phone) {
                    invite("", phone);
                }
            });
            listView.setAdapter(adapter);
        }
    }

}
