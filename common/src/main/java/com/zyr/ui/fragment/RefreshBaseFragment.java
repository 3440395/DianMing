package com.zyr.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zyr.base.BaseFragment;
import com.zyr.common.R;
import com.zyr.util.ViewUtils;


/**
 * Created by xk on 2016/6/4 9:25.
 */
public abstract class RefreshBaseFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    protected SwipeRefreshLayout swipeRefreshLayout;
    protected RecyclerView rv_list;


    @Override
    protected void findViews(View v) {
        swipeRefreshLayout = ViewUtils.findViewById(v, R.id.swipeRefreshLayout);
        rv_list = ViewUtils.findViewById(v, R.id.rv_list);
    }

    @Override
    protected void setupViews(View v) {
        swipeRefreshLayout.setColorSchemeColors(0xff3F51B5);
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(0xffffffff);
        rv_list.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    protected void setListener(View v) {
        swipeRefreshLayout.setOnRefreshListener(this);
    }





    @Override
    protected void fetchData(View v) {
    }
}
