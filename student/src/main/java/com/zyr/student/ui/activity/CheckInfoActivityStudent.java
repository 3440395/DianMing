package com.zyr.student.ui.activity;

import android.support.v4.widget.SwipeRefreshLayout;

import com.zyr.entity.BaseEntity;
import com.zyr.entity.CheckInfo;
import com.zyr.student.net.ProgressSubscriber;
import com.zyr.student.net.retrofit.Networks;
import com.zyr.ui.activity.CheckInfoActivity;
import com.zyr.ui.adapter.BaseListRefreshAdapter;
import com.zyr.ui.fragment.RefreshBaseFragment;
import com.zyr.util.RxSchedulerHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by xuekai on 2017/5/8.
 */

public class CheckInfoActivityStudent extends CheckInfoActivity {
    @Override
    public void requestAllStudentByCourseId(SwipeRefreshLayout swipeRefreshLayout, BaseListRefreshAdapter<CheckInfo> baseListRefreshAdapter) {
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "requestAllStudentByCourseId");
        params.put("courseid", course.getId() + "");
        params.put("courseTime", courseTime + "");
        params.put("courseDate", courseDate);
        Networks.getInstance().getApiService()
                .getListCheckInfo(params)
                .compose(RxSchedulerHelper.io_main())
                .subscribe(new ProgressSubscriber<BaseEntity<List<CheckInfo>>>(CheckInfoActivityStudent.this, true) {
                    @Override
                    public void onNext(BaseEntity<List<CheckInfo>> checkInfosEntity) {
                        if (checkInfosEntity.getResultCode() == 1) {
                            List<CheckInfo> checkInfos = checkInfosEntity.getData();
                            baseListRefreshAdapter.setData(checkInfos);
                        } else {
                            toast("获取签到信息失败");
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
    }

    @Override
    public void checkIn(RefreshBaseFragment<CheckInfo> fragment) {
        toast("签到");
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "checkIn");
        params.put("courseid", course.getId() + "");
        params.put("courseTime", courseTime + "");
        params.put("courseDate", courseDate);
        params.put("studentid", student.getStudentid());
        Networks.getInstance().getApiService()
                .coreInterface(params)
                .compose(RxSchedulerHelper.io_main())
                .subscribe(new ProgressSubscriber<BaseEntity>(CheckInfoActivityStudent.this, true) {
                    @Override
                    public void onNext(BaseEntity check) {
                        if (check.getResultCode() == 1) {
                            toast("签到成功");
                            fragment.onRefresh();
                        }else{
                            toast("签到失败");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    @Override
    public void requestSelf(SwipeRefreshLayout swipeRefreshLayout, BaseListRefreshAdapter<CheckInfo> baseListRefreshAdapter) {
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "requestAllStudentByCourseId");
        params.put("courseid", course.getId() + "");
        params.put("courseTime", courseTime + "");
        params.put("courseDate", courseDate);
        Networks.getInstance().getApiService()
                .getListCheckInfo(params)
                .compose(RxSchedulerHelper.io_main())
                .subscribe(new ProgressSubscriber<BaseEntity<List<CheckInfo>>>(CheckInfoActivityStudent.this, true) {
                    @Override
                    public void onNext(BaseEntity<List<CheckInfo>> checkInfosEntity) {
                        if (checkInfosEntity.getResultCode() == 1) {
                            List<CheckInfo> checkInfos = checkInfosEntity.getData();
                            List<CheckInfo> mcheckInfo = new ArrayList<CheckInfo>();
                            for (CheckInfo checkInfo : checkInfos) {
                                if (checkInfo.getStudent().getStudentid().equals(student.getStudentid())) {
                                    mcheckInfo.add(checkInfo);
                                }
                            }
                            baseListRefreshAdapter.setData(mcheckInfo);
                        } else {
                            toast("获取签到信息失败");
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
    }
}
