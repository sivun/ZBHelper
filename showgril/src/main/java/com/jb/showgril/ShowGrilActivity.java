package com.jb.showgril;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.MobclickAgentJSInterface;
import com.umeng.update.UmengUpdateAgent;


public class ShowGrilActivity extends Activity implements View.OnClickListener {
    private static final String URL = "http://show.xkdaogou.com/";
    protected WebView webView;
    private static long clickTime = 0l;
    private ImageButton back, more;
    private View copy, menu;
    private ClipboardManager myClipboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zbhelper);
        webView = (WebView) findViewById(R.id.webview);
        new MobclickAgentJSInterface(this, webView);
        back = (ImageButton) findViewById(R.id.back);
        more = (ImageButton) findViewById(R.id.more);
        copy = findViewById(R.id.copy);
        menu = findViewById(R.id.menu);
        back.setOnClickListener(this);
        more.setOnClickListener(this);
        copy.setOnClickListener(this);
        myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        initWebView();
        MobclickAgent.onPageStart("activity_onCreate");
        MobclickAgent.onEvent(this, "activity_onCreate");
    }

    private void initWebView() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);  //支持js

        webSettings.setUseWideViewPort(false);  //将图片调整到适合webview的大小

        webSettings.setSupportZoom(true);  //支持缩放

        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局

        webSettings.supportMultipleWindows();  //多窗口

        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);  //关闭webview中缓存

        webSettings.setAllowFileAccess(true);  //设置可以访问文件

        webSettings.setNeedInitialFocus(true); //当webview调用requestFocus时为webview设置节点

        webSettings.setBuiltInZoomControls(true); //设置支持缩放

        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口

        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        webSettings.setLoadsImagesAutomatically(true);  //支持自动加载图片
        webView.loadUrl(URL);
        webView.setWebViewClient(new WebViewClient() {

        });
        webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (menu.getVisibility() != View.GONE) {
                    menu.setVisibility(View.GONE);
                }
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        UmengUpdateAgent.update(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MobclickAgent.onPageEnd("activity_onDestroy");
    }

    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();
                return true;
            } else {
                // 双击退出
                long time = System.currentTimeMillis();
                if (time - clickTime > 2000 || time - clickTime < -2000) {
                    Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                    clickTime = time;
                    return true;
                } else {
                    finish();
                    System.exit(0);
                    return true;
                }
            }
        } else if (keyCode == KeyEvent.KEYCODE_MENU) {
            menu.setVisibility(menu.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v == back) {
            finish();
            System.exit(0);
        } else if (v == more) {
            menu.setVisibility(View.VISIBLE);
        } else if (v == copy) {
            // 得到剪贴板管理器
            ClipboardManager cmb = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            cmb.setText(URL);
            Toast.makeText(this, "复制成功，可以发给朋友们了。", Toast.LENGTH_LONG).show();
            menu.setVisibility(View.GONE);
        }

    }
}
