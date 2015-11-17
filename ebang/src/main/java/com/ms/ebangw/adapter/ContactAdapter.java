package com.ms.ebangw.adapter;

import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ms.ebangw.R;
import com.ms.ebangw.bean.ContactInfo;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.List;

public class ContactAdapter extends BaseAdapter {

    private List<ContactInfo> list;

    public ContactAdapter(List<ContactInfo> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void remove(int position) {
        list.remove(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(parent.getContext(), R.layout.recommend_worker_item, null);
            holder.tv_py = (TextView) convertView.findViewById(R.id.tv_py);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_phone = (TextView) convertView.findViewById(R.id.tv_craft_desc);
            holder.iv_avatar = (ImageView) convertView.findViewById(R.id.iv_avatar);
            holder.tv_invite = (TextView) convertView.findViewById(R.id.tv_remove_relation);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ContactInfo info = list.get(position);
        String name = info.getDisplayName();
        String phoneNum = info.getPhoneNum();
        Uri lookupUri = Contacts.getLookupUri(info.getContactId(), info.getLookUpKey());

        String firstChar = null;
        if (position == 0) {
            firstChar = info.getPinyin().substring(0, 1);
        } else {
            String py = info.getPinyin().substring(0, 1);
            String spy = list.get(position - 1).getPinyin().substring(0, 1);
            if (!py.equals(spy)) {
                firstChar = info.getPinyin().substring(0, 1);
            }
        }
        if (firstChar == null) {
            holder.tv_py.setVisibility(View.GONE);
        } else {
            holder.tv_py.setVisibility(View.VISIBLE);
            holder.tv_py.setText(firstChar);
        }
        holder.tv_name.setText(name);
        holder.tv_phone.setText(phoneNum);
        Picasso.with(parent.getContext()).load(lookupUri).placeholder(R.drawable.worker_avatar).into(holder.iv_avatar);

        return convertView;

//        ContactInfo cb = list.get(position);
//		String name = cb.getDisplayName();
//		String number = cb.getPhoneNum();
//		holder.name.setText(name);
//		holder.number.setText(number);
//		holder.qcb.assignContactUri(Contacts.getLookupUri(cb.getContactId(), cb.getLookUpKey()));
//		if(0 == cb.getPhotoId()){
//			holder.qcb.setImageResource(R.drawable.touxiang);
//		}else{
//			Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, cb.getContactId());
//			InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(ctx.getContentResolver(), uri);
//			Bitmap contactPhoto = BitmapFactory.decodeStream(input);
//			holder.qcb.setImageBitmap(contactPhoto);
//		}
//		String currentStr = getAlpha(cb.getSortKey());
//		String previewStr = (position - 1) >= 0 ? getAlpha(list.get(position - 1).getSortKey()) : " ";
//
//		if (!previewStr.equals(currentStr)) {
//			holder.alpha.setVisibility(View.VISIBLE);
//			holder.alpha.setText(currentStr);
//		} else {
//			holder.alpha.setVisibility(View.GONE);
//		}
//		return convertView;
    }

    private Bitmap getHeadBmp(Context context, long contactId) {

        Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId);
        InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(context.getContentResolver(), uri);
        return BitmapFactory.decodeStream(input);

    }

    static class ViewHolder {
        TextView tv_py, tv_name, tv_phone, tv_invite;
        ImageView iv_avatar;
    }

}
