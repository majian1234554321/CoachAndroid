package com.leyuan.coach.page.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.leyuan.coach.R;
import com.leyuan.coach.config.Constant;
import com.leyuan.coach.config.ConstantUrl;
import com.leyuan.coach.page.BaseActivity;
import com.leyuan.coach.utils.LogUtil;
import com.leyuan.coach.widget.CommonTitleLayout;

/**
 * Created by user on 2017/1/3.
 */
public class MessageDetailActivity extends BaseActivity {

    private static final java.lang.String TAG = "MessageDetailActivity";
    private CommonTitleLayout layoutTitle;
    private WebView mWebView;
    private int messageId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (!App.getInstance().isLogin()) {
//            UiManager.activityJump(this, LoginActivity.class);
//        }

        setContentView(R.layout.activity_message_detail);
        messageId = getIntent().getExtras().getInt(Constant.MESSAGE_ID);

        layoutTitle = (CommonTitleLayout) findViewById(R.id.layout_title);
        mWebView = (WebView) findViewById(R.id.webView);

        initData();
        initWebView();
        LogUtil.w(TAG, "onCreate");
    }

    private void initData() {
        layoutTitle.setLeftIconListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void initWebView() {
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
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.w(TAG, "onResume");
        mWebView.loadUrl(ConstantUrl.MESSAGE + messageId);
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.w(TAG, "onStart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.w(TAG, "onDestroy");
    }
}
