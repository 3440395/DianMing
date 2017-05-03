//package com.zyr.common.base;
//
//import android.app.Activity;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.DialogInterface.OnKeyListener;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.text.TextUtils;
//import android.view.KeyEvent;
//
//import java.io.File;
//import java.lang.ref.WeakReference;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//public abstract class BaseActivity extends Activity{
//    public ExecutorService pool = Executors.newCachedThreadPool();
//
//    private static final int ACCESS_NET_FAIL = 0x10000001;
//    private static final int ACCESS_GATADATA_FAIL = 0x10000002;
//    private static final int ACCESS_WWW_SUCCESS = 0x10000003;
//    private static final int ACCESS_WWW_FAIL = 0x10000004;
//    private static final int ACCESS_MSFTNCSI_FAIL = 0x10000005;
//
//    protected abstract void doHandleMessage(Message msg);
//
//    protected abstract String getPageName();
//
//    protected abstract void execGC();
//
//
//    protected MyHandler handler = null;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        LogUtil.d("BaseActivity-->onCreate" + System.currentTimeMillis());
//
//
//        handler = new MyHandler(this) {
//            public void handleMessage(Message msg) {
//                if (this.mActivity == null || this.mActivity.get() == null || this.mActivity.get().isFinishing()) {
//                    LogUtil.d("RecommendActivity --> handleMessage return ");
//                    return;
//                }
//                Bundle b = msg.getData();
//                if (b == null || !b.getBoolean("notCloseProgressBar")) {
//                    cancelDialog();
//                }
//                if (msg.what == ACCESS_NET_FAIL) {
//                    String s = (String) msg.obj;
////					checkNetwork("http://www.baidu.com", s);
//                    if (TextUtils.isEmpty(checkUrl)) {
//                        LogUtil.d("BaseActivity ------->check Msftncsi ");
//                        checkNetwork(s);
//                    } else {
//                        LogUtil.d("BaseActivity ------->check internet----->" + checkUrl);
//                        checkNetwork(checkUrl, s);
//                    }
//                } else if (msg.what == ACCESS_WWW_FAIL) {
//                    String s = (String) msg.obj;
//                    checkNetwork(s);
//                    LogUtil.d("BaseActivity ------->check Msftncsi ");
//                } else if (msg.what == ACCESS_GATADATA_FAIL) {
//                    String s = (String) msg.obj;
//                    new ErrorHintDialog.Builder(BaseActivity.this).setCancelable(false).setDialogTitle(s)
//                            .setPositiveButton(R.string.cs_uicore_common_ok, new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    BaseActivity.this.finish();
//                                }
//                            }).create().show();
//                } else if (msg.what == ACCESS_MSFTNCSI_FAIL) {
//                    LogUtil.d("BaseActivity ------->ACCESS_MSFTNCSI_FAIL ");
//                    new ErrorHintDialog.Builder(BaseActivity.this).setCancelable(false)
//                            .setDialogTitle("网络不通，请检查网络。\n错误码：0101200001")
//                            .setPositiveButton(R.string.cs_uicore_common_ok, new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    BaseActivity.this.finish();
//                                }
//                            }).create().show();
//                } else if (msg.what == ACCESS_WWW_SUCCESS) {
//                    String s = (String) msg.obj;
//                    String hint = (String) getText(R.string.cs_uicore_common_access_net_fail);
//                    if (!TextUtils.isEmpty(s)) {
//                        hint = s;
//                    }
//                    new ErrorHintDialog.Builder(BaseActivity.this).setCancelable(false).setDialogTitle(hint)
//                            .setPositiveButton(R.string.cs_uicore_common_ok, new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    BaseActivity.this.finish();
//                                }
//                            }).create().show();
//                } else {
//                    doHandleMessage(msg);
//                }
//            }
//
//            ;
//        };
//
//    }
//
//
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (handler != null) {
//            handler.removeCallbacksAndMessages(null);
//        }
//    }
//
//    protected void checkNetwork(final String debugMsg) {
//        new Thread() {
//            public void run() {
//                if (NetUtil.connectServer(checkMsftncsiUrl, 1, 2) == null) {
//                    if (NetUtil.connectServer(checkBaiduUrl, 1, 2) == null) {
//                        sendMessage(ACCESS_MSFTNCSI_FAIL);
//                        return;
//                    }
//                }
//                sendMessage(ACCESS_WWW_SUCCESS, debugMsg);
//            }
//
//            ;
//        }.start();
//    }
//
//    protected void checkNetwork(final String url, final String debugMsg) {
//        new Thread() {
//            public void run() {
//                if (NetUtil.connectServer(url, 1, 2) == null) {
//                    sendMessage(ACCESS_WWW_FAIL, debugMsg);
//                } else {
//                    sendMessage(ACCESS_WWW_SUCCESS, debugMsg);
//                }
//            }
//
//            ;
//        }.start();
//    }
//
//    @Override
//    public File getDataDir() {
//        return super.getDataDir();
//    }
//
//    protected void showDialog() {
//        if (progressDialog == null) {
//            progressDialog = new TVProgressDialog(this);
//            progressDialog.setOnKeyListener(new OnKeyListener() {
//                @Override
//                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
//                    if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
//                        dialog.dismiss();
//                        finish();
//                        return true;
//                    }
//                    return false;
//                }
//            });
//            progressDialog.show();
//        }
//
//		/*
//         * if( progressDialog !=null && !progressDialog.isShowing()){
//		 * progressDialog.show(); }
//		 */
//    }
//
//    protected void cancelDialog() {
//        if (progressDialog != null) {
//            progressDialog.dismiss();
//            progressDialog = null;
//        }
//    }
//
//    protected void sendMessage(int what) {
//        Message message = Message.obtain();
//        message.what = what;
//        handler.sendMessage(message);
//    }
//
//    protected void sendMessage(int what, int arg1) {
//        Message message = Message.obtain();
//        message.what = what;
//        message.arg1 = arg1;
//        handler.sendMessage(message);
//    }
//
//    protected void sendMessage(int what, int arg1, int arg2) {
//        Message message = Message.obtain();
//        message.what = what;
//        message.arg1 = arg1;
//        message.arg2 = arg2;
//        handler.sendMessage(message);
//    }
//
//    protected void sendMessage(int what, Object obj) {
//        Message message = Message.obtain();
//        message.what = what;
//        message.obj = obj;
//        handler.sendMessage(message);
//    }
//
//    protected void sendMessage(int what, int arg1, Object obj) {
//        Message message = Message.obtain();
//        message.what = what;
//        message.arg1 = arg1;
//        message.obj = obj;
//        handler.sendMessage(message);
//    }
//
//    protected void sendMessage(int what, int arg1, int arg2, Object obj) {
//        Message message = Message.obtain();
//        message.what = what;
//        message.arg1 = arg1;
//        message.arg2 = arg2;
//        message.obj = obj;
//        handler.sendMessage(message);
//    }
//
//    protected void sendNetFailMessage() {
//        Message message = Message.obtain();
//        message.what = ACCESS_NET_FAIL;
//        handler.sendMessage(message);
//    }
//
//    protected void sendNetFailMessage(String debugMsg) {
//        Message message = Message.obtain();
//        message.what = ACCESS_NET_FAIL;
//        message.obj = debugMsg;
//        handler.sendMessage(message);
//    }
//
//    protected void sendGetDataFailMessage(String failMsg) {
//        Message message = Message.obtain();
//        message.what = ACCESS_GATADATA_FAIL;
//        message.obj = failMsg;
//        handler.sendMessage(message);
//    }
//
//    protected void sendMessageNotClosePb(int what) {
//        Message message = Message.obtain();
//        message.what = what;
//        Bundle b = new Bundle();
//        b.putBoolean("notCloseProgressBar", true);
//        message.setData(b);
//        handler.sendMessage(message);
//    }
//
//    protected void sendMessageNotClosePb(int what, int arg1, Object obj) {
//        Message message = Message.obtain();
//        message.what = what;
//        message.arg1 = arg1;
//        message.obj = obj;
//        Bundle b = new Bundle();
//        b.putBoolean("notCloseProgressBar", true);
//        message.setData(b);
//        handler.sendMessage(message);
//    }
//
//    protected void getServiceData() {
//
//    }
//
//    protected static class MyHandler extends Handler {
//        public final WeakReference<BaseActivity> mActivity;
//
//        public MyHandler(BaseActivity activity) {
//            mActivity = new WeakReference<BaseActivity>(activity);
//        }
//    }
//
//    private class ClosedReceiver extends BroadcastReceiver {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            finish();
//        }
//    }
//
//    private class HomeKeyEventBroadCastReceiver extends BroadcastReceiver {
//        static final String SYSTEM_REASON = "reason";
//        static final String SYSTEM_HOME_KEY = "homekey";// home key
//        static final String SYSTEM_RECENT_APPS = "recentapps";// long home key
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//
//            String action = intent.getAction();
//            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
//                String reason = intent.getStringExtra(SYSTEM_REASON);
//                if (reason != null) {
//                    if (reason.equals(SYSTEM_HOME_KEY)) {
//                        finish();
//                    }
//                }
//            }
//        }
//    }
//
//    private class InitCompletedReceiver extends BroadcastReceiver {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if ("0".equals(intent.getStringExtra("code"))) {
//                getServiceData();
//            } else if ("1".equals(intent.getStringExtra("code"))) {
//                String message = intent.getStringExtra("msg");
//                sendGetDataFailMessage(message);
//            } else if ("-1".equals(intent.getStringExtra("code"))) {
//                String message = intent.getStringExtra("msg");
//                sendNetFailMessage(message);
//                List<Random> list = new ArrayList();
//                list.add(0, new Random());
//            }
//        }
//    }
//
//    @Override
//    public void recevie(org.jivesoftware.smack.packet.Message msg) {
//        LogUtil.e("recevie xmpp ------>" + msg.getFunction());
//        if (msg.getFunction().equals("CacheNotification")) {
//            if (clear != null) {
//                clear.clear();
//            }
//        } else if (msg.getFunction().equals("vmc_user_offline")) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    VUserManager.getInstance().loginOut(BaseActivity.this);
//                }
//            }).start();
//            onUserOffline();
//        }
//    }
//
//    public void onUserOffline(){
//    }
//
//    public void setClear(Clear clear) {
//        this.clear = clear;
//    }
//
//    private Clear clear;
//
//    public interface Clear {
//        void clear();
//    }
//}
