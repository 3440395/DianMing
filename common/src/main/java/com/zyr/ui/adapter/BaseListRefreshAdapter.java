package com.zyr.ui.adapter;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;

import java.util.List;

/**
 * Created by xuekai on 2017/5/5.
 */

public abstract class BaseListRefreshAdapter<T> extends BaseRecycleAdapter<T> {

    /**
     * @param context  //上下文
     * @param layoutId //布局id
     * @param data     //数据源
     */
    public BaseListRefreshAdapter(Context context, int layoutId, List<T> data) {
        super(context, layoutId, data);
    }

    @Override
    protected abstract void convert(BaseViewHolder holder, T bean);


    public abstract void requestData(SwipeRefreshLayout swipeRefreshLayout);
}
