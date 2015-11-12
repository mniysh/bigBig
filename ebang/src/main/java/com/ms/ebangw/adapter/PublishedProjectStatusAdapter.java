package com.ms.ebangw.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ms.ebangw.R;
import com.ms.ebangw.bean.ReleaseProject;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.fragment.PublishedProjectStatusFragment;
import com.ms.ebangw.service.DataAccessUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class PublishedProjectStatusAdapter extends BaseAdapter {
    private List<ReleaseProject> list;
    private String status;

    public PublishedProjectStatusAdapter(List<ReleaseProject> projectList,
                                         @PublishedProjectStatusFragment.ProjectStatus String
                                         projectStatus) {
        this.list = projectList;
        this.status = projectStatus;

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
            convertView = View.inflate(parent.getContext(), R.layout.published_project_status_item, null);
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

        switch (status) {
            case PublishedProjectStatusFragment.WAITTING:
                holder.tvEvaluate.setVisibility(View.GONE);
                holder.tvGrabNum.setVisibility(View.VISIBLE);

                break;
            case PublishedProjectStatusFragment.EXECUTE:
                holder.tvEvaluate.setVisibility(View.GONE);
                holder.tvGrabNum.setVisibility(View.GONE);
                break;
            case PublishedProjectStatusFragment.COMPLETE:
                holder.tvEvaluate.setVisibility(View.VISIBLE);
                holder.tvGrabNum.setVisibility(View.GONE);
                holder.tvEvaluate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null != onEvaluateClickListener) {
                            onEvaluateClickListener.onGrabClick(v, project);
                        }
                    }
                });
                break;

        }

        holder.tvTitle.setText(title);
        holder.tvDescription.setText(description);
        holder.tvGrabNum.setText(grab_num + "人抢单");
        holder.tvMoney.setText("总工资:" + project_money + " 元");
//        holder.tvDistance.setText(distance);

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
        @Bind(R.id.tv_evaluate)
        TextView tvEvaluate;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface OnEvaluateClickListener {
        void onGrabClick(View view, ReleaseProject releaseProject);
    }

    private OnEvaluateClickListener onEvaluateClickListener;

    public void setOnEvaluateClickListener(OnEvaluateClickListener onEvaluateClickListener) {
        this.onEvaluateClickListener = onEvaluateClickListener;
    }
}