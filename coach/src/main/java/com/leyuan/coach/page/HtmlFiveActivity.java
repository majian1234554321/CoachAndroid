package com.leyuan.coach.page;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.gson.Gson;
import com.leyuan.coach.R;
import com.leyuan.coach.bean.PayOrderBean;
import com.leyuan.coach.bean.PushExtroInfo;
import com.leyuan.coach.config.Constant;
import com.leyuan.coach.config.ConstantString;
import com.leyuan.coach.pay.AliPay;
import com.leyuan.coach.pay.PayInterface;
import com.leyuan.coach.pay.WeiXinPay;
import com.leyuan.coach.utils.LogUtil;
import com.leyuan.commonlibrary.manager.DeviceManager;
import com.leyuan.commonlibrary.manager.TelephoneManager;

import static com.leyuan.coach.R.id.webView;

/**
 * Created by user on 2017/8/11.
 */

public class HtmlFiveActivity extends BaseActivity implements View.OnClickListener {

    private static final java.lang.String TAG = "HtmlFiveActivity";
    private WebView mWebView;
    private Gson gson = new Gson();

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            LogUtil.i(TAG, "onReceive action = " + intent.getAction());
            switch (intent.getAction()) {
                case Constant.BROADCAST_NOTIFY_CURRENT_TAKE_OVER_COURSE:
                    mWebView.loadUrl("javascript:daiCourse()");
                    break;
                case Constant.BROADCAST_NOTIFY_SUSPEND_COURSE:
                    mWebView.loadUrl("javascript:closedCourse()");
                    break;
                case Constant.BROADCAST_NOTIFY_NEWLY_INCREASE_COURSE:
                    mWebView.loadUrl("javascript:addCourse()");
                    break;
                case Constant.BROADCAST_NOTIFY_NEWS_NESSAGE:
                    mWebView.loadUrl("javascript:sendMessageTip()");
                    break;
                case Constant.BROADCAST_NOTIFY_NEXT_MONTH_COURSE:
                    mWebView.loadUrl("javascript:nextMonthCourseTip()");
                    break;
                case Constant.BROADCAST_NOTIFY_SAVE_JPUSHID:
                    mWebView.loadUrl("javascript:jpushId('" + App.getInstance().getJPushId() + "')");
                    break;
            }
        }
    };
    private boolean isFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_html_five);

        initReceiver();
        initWebView();
    }

    private void initReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.BROADCAST_NOTIFY_CURRENT_TAKE_OVER_COURSE);
        filter.addAction(Constant.BROADCAST_NOTIFY_SUSPEND_COURSE);
        filter.addAction(Constant.BROADCAST_NOTIFY_NEWLY_INCREASE_COURSE);
        filter.addAction(Constant.BROADCAST_NOTIFY_NEWS_NESSAGE);
        filter.addAction(Constant.BROADCAST_NOTIFY_NEXT_MONTH_COURSE);
        filter.addAction(Constant.BROADCAST_NOTIFY_SAVE_JPUSHID);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        mWebView = (WebView) findViewById(webView);

//        mWebView.clearCache(true);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        webSettings.setAllowFileAccess(true);// 设置允许访问文件数据 v

        webSettings.setSupportZoom(true);

        webSettings.setBuiltInZoomControls(true);
        //        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        webSettings.setDomStorageEnabled(true);

        webSettings.setDatabaseEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);

            }
        });

        mWebView.setWebViewClient(new WebViewClient() {
            @SuppressLint("AddJavascriptInterface")
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                LogUtil.i(TAG, "mWebView.loadUrl onPageFinished");

                if (isFirst) {
                    isFirst = false;

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        mWebView.evaluateJavascript("javascript:jpushId('" + App.getInstance().getJPushId() + "')", new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String value) {
                                LogUtil.i(TAG, "jpushId onReceiveValue = " + value);
                            }
                        });
                    }else {
                        mWebView.loadUrl("javascript:jpushId('" + App.getInstance().getJPushId() + "')");
                    }

                    Bundle bundle = getIntent().getExtras();
                    if (bundle == null) return;
                    String backup = bundle.getString(ConstantString.PUSH_BACKUP, "0");
                    int type = bundle.getInt(ConstantString.PUSH_TYPE, 0);
                    LogUtil.i(TAG, "backup = " + backup + ", type = " + type);
                    switch (type) {
                        case PushExtroInfo.PushType.NEWS_MESSAGE:
                            mWebView.loadUrl("javascript:sendMessage('" + backup + "')");
                            break;
                        case PushExtroInfo.PushType.MEXT_MONTH_UNCONFIRMED:
                            mWebView.loadUrl("javascript:nextMonthCourse('" + backup + "')");
                            break;
                        case PushExtroInfo.PushType.CURRENT_TAKE_OVER_COURSE:
                            break;
                        case PushExtroInfo.PushType.NOTIFY_SUSPEND_COURSE:
                            break;
                        case PushExtroInfo.PushType.NEWLY_INCREASE_COURSE:
                            break;
                    }
                }
            }

//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                LogUtil.i(TAG,"over url = " + url);
////                view.loadUrl(url);
//
//                return false;
//            }
        });

        findViewById(R.id.bt_confirm).setOnClickListener(this);
        findViewById(R.id.bt_confirm).setVisibility(View.GONE);

        LogUtil.i(TAG, "mWebView.loadUrl start");
        mWebView.addJavascriptInterface(new MyJSInterface(HtmlFiveActivity.this), "android");
        mWebView.loadUrl("http://m1.aidong.me/html/course.html#a");

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.bt_confirm:
                LogUtil.i("bt_confirm  onClick");
                mWebView.loadUrl("javascript:jpushId('234sdfdasf')");
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LogUtil.i(TAG, "onNewIntent intent extras= " + intent.getExtras());

    }

    @Override
    // 设置回退
    // 5、覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //按下返回键并且webview界面可以返回
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {

            String currentUrl = mWebView.copyBackForwardList().getCurrentItem().getUrl();
            LogUtil.i(TAG, "current url = " + currentUrl);

            if (currentUrl != null && currentUrl.contains("http://m1.aidong.me/html/course.html")) {
                onBackPressed();
            } else {
                mWebView.goBack();
            }

//            if(!urlsLoded.isEmpty()){
//                LogUtil.i(TAG,"mWebView.canGoBack()");
//                mWebView.goBack(); // goBack()表示返回WebView的上一页面
//            }else {
//                LogUtil.i(TAG,"mWebView.canGoBack false exitApp" );
//                exitApp();
//            }

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private long mPressedTime = 0;

    @Override
    public void onBackPressed() {
        LogUtil.i(TAG, "onBackPressed");
        long mNowTime = System.currentTimeMillis();//获取第一次按键时间
        if ((mNowTime - mPressedTime) > 2000) {//比较两次按键时间差
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mPressedTime = mNowTime;
        } else {//退出程序
            exitApp();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    class MyJSInterface {
        Context mContext;

        public MyJSInterface(Context context) {
            mContext = context;
        }

        @JavascriptInterface
        public void exitApplication() {
            LogUtil.i(TAG, "invoke my exitApp");
            exitApp();
        }

        @JavascriptInterface
        public String getJpushID() {

            return App.getInstance().getJPushId();
        }

        @JavascriptInterface
        public String getDevice() {
            return "android";
        }

        @JavascriptInterface
        public String getVersion() {

            return App.getInstance().getVersionName();
        }

        @JavascriptInterface
        public String getDeviceName() {

            return DeviceManager.getPhoneBrand();
        }

        @JavascriptInterface
        public void payOrder(String payInfo) {
            LogUtil.i(TAG, "payOrder : " + payInfo);
//            PayOrderResult result = gson.fromJson(payInfo, PayOrderResult.class);

            PayOrderBean payOrderBean = gson.fromJson(payInfo, PayOrderBean.class);

            String payType = payOrderBean.getPayType();
            PayInterface payInterface = "1".equals(payType) ? new AliPay(HtmlFiveActivity.this, payListener)
                    : new WeiXinPay(HtmlFiveActivity.this, payListener);
            payInterface.payOrder(payOrderBean);
        }

        @JavascriptInterface
        public String selectAddress() {

            return "return selectAddress from android ";
        }


        @JavascriptInterface
        public String fitnessPay(String parameters) {
            LogUtil.i("html js : fitnessPay invoked-----" + parameters);

            return "return fitnessPay from android ";
        }

        @JavascriptInterface
        public void callTelephone(String phoneNumber) {
            LogUtil.i("html js : callTelephone invoked-----" + phoneNumber);

            TelephoneManager.callImmediate(HtmlFiveActivity.this, phoneNumber);
        }


    }


    private PayInterface.PayListener payListener = new PayInterface.PayListener() {
        @Override
        public void fail(String code, Object object) {
            String tip = "";
            switch (code) {
                case "4000":
                    tip = "订单支付失败";
                    break;
                case "5000":
                    tip = "订单重复提交";
                    break;
                case "6001":
                    tip = "订单取消支付";
                    break;
                case "6002":
                    tip = "网络连接出错";
                    break;
                default:
                    tip = "订单支付失败";
                    break;
            }
            LogUtil.i(TAG, "payResult : fail");
            mWebView.loadUrl("javascript:payResult(" + false + ",4000,'" + tip + "')");
//

        }

        @Override
        public void success(String code, Object object) {
            LogUtil.i(TAG, "payResult : success");
            mWebView.loadUrl("javascript:payResult( " + true + ",200,'" + "支付成功" + "')");
//
        }
    };
}
