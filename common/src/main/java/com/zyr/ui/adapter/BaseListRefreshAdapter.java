package com.zyr.ui.adapter;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by xuekai on 2017/5/5.
 */

public abstract class BaseListRefreshAdapter<T> extends BaseRecycleAdapter<T> {
    private ViewGroup rootView;
    private TextView msg;

    /**
     * @param context  //上下文
     * @param layoutId //布局id
     * @param data     //数据源
     */
    public BaseListRefreshAdapter(Context context, int layoutId, List<T> data) {
        super(context, layoutId, data);
    }

    /**
     * 设置数据空时显示的内容
     *
     * @return
     */
    public abstract String setEmptyMstContent();

    @Override
    protected abstract void convert(BaseViewHolder holder, T bean);


    public abstract void requestData(SwipeRefreshLayout swipeRefreshLayout);

    @Override
    public void setData(List<T> data) {
        super.setData(data);
        if (data.size() == 0) {
            showEmptyMsg(true);
        } else {
            showEmptyMsg(false);
        }
    }

    private void setEmptyMsg() {
        if (rootView != null) {
            msg = new TextView(context);
            msg.setText(setEmptyMstContent());
            msg.setGravity(Gravity.CENTER);
            msg.setTextSize(TypedValue.COMPLEX_UNIT_SP,23);
            msg.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            rootView.addView(msg);
        }
    }

    private void showEmptyMsg(boolean show) {
        msg.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }

    public void setRootView(ViewGroup rootView) {
        this.rootView = rootView;
        setEmptyMsg();
    }
}
