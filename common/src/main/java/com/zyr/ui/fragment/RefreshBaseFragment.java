package com.zyr.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.zyr.base.BaseFragment;
import com.zyr.common.R;
import com.zyr.ui.adapter.BaseListRefreshAdapter;
import com.zyr.util.ViewUtils;


/**
 * Created by xk on 2016/6/4 9:25.
 */
public class RefreshBaseFragment<T> extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    public SwipeRefreshLayout swipeRefreshLayout;
    protected RecyclerView rv_list;
    private BaseListRefreshAdapter<T> adapter;
    private boolean canSwipeRefresh=true;

    public void setCanSwipeRefresh(boolean canSwipeRefresh) {
        this.canSwipeRefresh = canSwipeRefresh;
    }

    @Override
    protected void setLayoutRes() {
        layoutRes = R.layout.fragment_list_refresh;
    }

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
        if (adapter == null) {
            throw new NullPointerException("请调用设置adapter");
        }
        adapter.setRootView((ViewGroup) rootView);
        rv_list.setAdapter(adapter);
        swipeRefreshLayout.setEnabled(canSwipeRefresh);
    }

    @Override
    protected void setListener(View v) {
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void fetchData(View v) {
    }

    @Override
    public void onRefresh() {
        adapter.requestData(swipeRefreshLayout);
    }

    public void setAdapter(BaseListRefreshAdapter<T> adapter) {
        this.adapter = adapter;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.requestData(swipeRefreshLayout);
    }
}
