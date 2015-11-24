package com.ms.ebangw.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ms.ebangw.R;
import com.ms.ebangw.activity.ProjectStatusActivity;
import com.ms.ebangw.bean.ReleaseProject;
import com.ms.ebangw.commons.Constants;
import com.ms.ebangw.fragment.ProjectStatusFragment;
import com.ms.ebangw.service.DataAccessUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class PublishedProjectStatusAdapter extends BaseAdapter {
    private List<ReleaseProject> list;
    private String status;
    private String category;
    private String inviteType;

    public PublishedProjectStatusAdapter(List<ReleaseProject> projectList,String category,
                                         @ProjectStatusFragment.ProjectStatus String
                                         projectStatus, String inviteType) {
        this.list = projectList;
        this.category = category;
        this.status = projectStatus;
        this.inviteType = inviteType;

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
        ReleaseProject project = list.get(position);
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
        if (!TextUtils.isEmpty(imageUrl)) {
            Picasso.with(parent.getContext()).load(DataAccessUtil.getImageUrl(imageUrl)).error(R.drawable.head)
                .placeholder(R.drawable.head).
                into(holder.head);
        }else {
            holder.head.setImageResource(R.drawable.head);
        }

        switch (category) {
        case Constants.DEVELOPERS:

                break;

         case Constants.HEADMAN:

                break;

         case Constants.WORKER:

                break;

         case Constants.INVESTOR:

                break;

         case Constants.COMPANY:

                break;

        }

        switch (status) {
            case ProjectStatusFragment.WAITING:
                holder.tvShow.setVisibility(View.GONE);
                holder.tvGrabDescription.setVisibility(View.VISIBLE);

                break;
            case ProjectStatusFragment.EXECUTE:
                holder.tvShow.setVisibility(View.GONE);
                holder.tvGrabDescription.setVisibility(View.GONE);
                break;
            case ProjectStatusFragment.COMPLETE:
                holder.tvShow.setVisibility(View.VISIBLE);
                holder.tvGrabDescription.setVisibility(View.GONE);

                break;

        }

        holder.tvTitle.setText(title);
        holder.tvDescription.setText(description);
//        holder.tvGrabDescription.setText(grab_num + "人抢单");
        holder.tvMoney.setText("总工资:" + project_money + " 元");

        convertView.setTag(Constants.KEY_RELEASED_PROJECT, project);
        return convertView;
    }

    private void setDevelopersItem(ViewHolder holder, String status, final ReleaseProject project) {
        switch (status) {
            case ProjectStatusFragment.WAITING:
                holder.tvShow.setVisibility(View.GONE);
                holder.tvGrabDescription.setVisibility(View.VISIBLE);

                break;
            case ProjectStatusFragment.EXECUTE:
                holder.tvShow.setVisibility(View.GONE);
                holder.tvGrabDescription.setVisibility(View.GONE);
                break;
            case ProjectStatusFragment.COMPLETE:
                holder.tvShow.setVisibility(View.VISIBLE);
                holder.tvGrabDescription.setVisibility(View.GONE);
                holder.tvShow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null != onEvaluateClickListener) {
                            onEvaluateClickListener.onGrabClick(v, project);
                        }
                    }
                });
                break;
        }
    }

    private void setWorkerItem(ViewHolder holder, String status, final ReleaseProject project) {
        switch (status) {
            case ProjectStatusFragment.WAITING:
            case ProjectStatusFragment.EXECUTE:
                holder.tvShow.setVisibility(View.GONE);
                holder.tvGrabDescription.setVisibility(View.VISIBLE);
                if (TextUtils.equals(inviteType, ProjectStatusActivity.INVITE_TYPE_INVITE)) {
                //待接受邀请
                    holder.tvShow.setText("查看邀请我的");
                }else {
                    holder.tvShow.setText("点击联系");
                }

                break;
            case ProjectStatusFragment.COMPLETE:
                holder.tvShow.setVisibility(View.VISIBLE);
                holder.tvGrabDescription.setVisibility(View.GONE);
                holder.tvShow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null != onEvaluateClickListener) {
                            onEvaluateClickListener.onGrabClick(v, project);
                        }
                    }
                });
                break;
        }
    }

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
        @Bind(R.id.tv_grab_description)
        TextView tvGrabDescription;
        @Bind(R.id.tv_show)
        TextView tvShow;

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