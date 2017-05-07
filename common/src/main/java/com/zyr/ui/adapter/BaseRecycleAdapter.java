package com.zyr.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.zyr.util.ViewUtils;

import java.util.List;

/**
 * Created by xuekai on 2017/5/5.
 */

public abstract class BaseRecycleAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    private int layoutId;
    private List<T> data;
    protected Context context;
    private OnItemClickListner onItemClickListner;//单击事件
    private int dp_5;
    protected View view;
    /**
     * @param context  //上下文
     * @param layoutId //布局id
     * @param data     //数据源
     */
    public BaseRecycleAdapter(Context context, int layoutId, List<T> data) {
        dp_5 = ViewUtils.dip2px(context, 5);
        this.layoutId = layoutId;
        this.data = data;
        this.context = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = View.inflate(context, layoutId, null);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(dp_5, dp_5, dp_5, 0);
        view.setLayoutParams(layoutParams);
        final BaseViewHolder holder = new BaseViewHolder(view, context);
        //单击事件回调
        view.setOnClickListener(v1 -> {
            if (onItemClickListner!=null) {
                onItemClickListner.onItemClickListner(v1,data.get(holder.getLayoutPosition()));
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        convert(holder, data.get(position));
    }

    protected abstract void convert(BaseViewHolder holder, T bean);

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setOnItemClickListner(OnItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }


    public interface OnItemClickListner<T> {
        void onItemClickListner(View v, T t);
    }


    public void setData(List<T> data) {
        this.data = data;
        notifyDataSetChanged();
    }




}