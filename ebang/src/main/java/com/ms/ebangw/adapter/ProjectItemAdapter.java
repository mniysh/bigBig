package com.ms.ebangw.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ms.ebangw.R;
import com.ms.ebangw.bean.ReleaseProject;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.service.DataAccessUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class ProjectItemAdapter extends BaseAdapter {
    private List<ReleaseProject> list;

    public ProjectItemAdapter(List<ReleaseProject> projectList) {
        this.list = projectList;
    }

    public void setList(List<ReleaseProject> list) {
        this.list = list;
    }

    public List<ReleaseProject> getList() {
        return list;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final ReleaseProject project = list.get(position);
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.home_project_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        String imageUrl = project.getImages();
        String title = project.getTitle();
        String description = project.getDescription();
        String grab_num = project.getGrab_num();
        String project_money = project.getProject_money();
        String distance = project.getDistance();
        if (!TextUtils.isEmpty(imageUrl)) {
            Picasso.with(parent.getContext()).load(DataAccessUtil.getImageUrl(imageUrl))
                .placeholder(R.drawable.head).
                into(holder.head);
        }else {
            holder.head.setImageResource(R.drawable.head);
        }

        holder.tvTitle.setText(title);
        holder.tvDescription.setText(description);
        holder.tvGrabNum.setText("已有" + grab_num + "人抢单");
        holder.tvMoney.setText(project_money);
        holder.tvDistance.setText(distance);
        holder.btnGrab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onGrabClickListener) {
                    onGrabClickListener.onGrabClick(v, project);
                }
            }
        });
        convertView.setTag(Constants.KEY_RELEASED_PROJECT, project);
        return convertView;
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'home_project_itemitem.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolder {
        @Bind(R.id.head)
        ImageView head;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_distance)
        TextView tvDistance;
        @Bind(R.id.tv_description)
        TextView tvDescription;
        @Bind(R.id.tv_money)
        TextView tvMoney;
        @Bind(R.id.tv_grab_num)
        TextView tvGrabNum;
        @Bind(R.id.btn_grab)
        Button btnGrab;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface OnGrabClickListener{
        void onGrabClick(View view, ReleaseProject releaseProject);
    }

    private OnGrabClickListener onGrabClickListener;

    public void setOnGrabClickListener(OnGrabClickListener onGrabClickListener) {
        this.onGrabClickListener = onGrabClickListener;
    }
}