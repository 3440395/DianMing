package com.zyr.teacher.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zyr.entity.Teacher;
import com.zyr.teacher.R;
import com.zyr.util.ViewUtils;

import java.util.List;

/**
 * Created by X.Sation on 2017/5/4.
 */

public class TeacherListAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<Teacher> datas;
    private final int dp_5;

    public TeacherListAdapter(Context context) {
        this.context = context;
        dp_5 = ViewUtils.dip2px(context, 5);
    }

    public void setDatas(List<Teacher> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_teacher, null);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(dp_5,dp_5,dp_5,0);
        view.setLayoutParams(layoutParams);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String sex = datas.get(position).getSex();
        if (sex!=null) {
            sex=sex.equals("男")?"♂":"♀";
        }else{
            sex="-";
        }
        String phone = datas.get(position).getPhone();
        if (phone!=null) {
            phone="-";
        }
        ((TextView) holder.itemView.findViewById(R.id.tv_name)).setText(datas.get(position).getName());
        ((TextView) holder.itemView.findViewById(R.id.tv_sex)).setText(sex);
        ((TextView) holder.itemView.findViewById(R.id.tv_phone)).setText(phone);
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
        }
    }
}
