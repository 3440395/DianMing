package com.zyr.ui.fragment;

import android.view.View;

import com.zyr.common.R;


public abstract class BaseListFragment extends RefreshBaseFragment {
//    private TruckInfoAdapter truckInfoAdapter;

    @Override
    protected void setLayoutRes() {
        layoutRes = R.layout.fragment_list_refresh;
    }


    @Override
    protected void setupViews(View v) {
        super.setupViews(v);
//        truckInfoAdapter = new TruckInfoAdapter(getActivity(), rv_list);
//        rv_list.setAdapter(truckInfoAdapter);

    }

    @Override
    protected void setListener(View v) {
        super.setListener(v);
//        truckInfoAdapter.setOnDataEmptyListener(new TruckInfoAdapter.OnDataEmptyListener() {
//            @Override
//            public void onDataEmpty() {
//                tv_without_info.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void onDataNotEmpty() {
//                tv_without_info.setVisibility(View.GONE);
//            }
//        });
    }

    @Override
    public void onRefresh() {
        requestData();
    }

    public abstract void requestData();

}
