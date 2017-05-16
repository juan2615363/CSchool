package cn.songguohulian.cschool.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import android.widget.TextView;

import com.tencent.smtt.export.external.interfaces.GeolocationPermissionsCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.songguohulian.cschool.R;
import cn.songguohulian.cschool.view.TerryX5Web;


/**
 * @author Ziv
 * @data 2017/4/19
 * @time 23:30
 * <p>
 * 浏览界面
 */

public class MyWebActivity extends Activity {

    @Bind(R.id.tv_web_title)
    public TextView title;

    @Bind(R.id.webView)
    public TerryX5Web webView;

    String url = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * 沉浸式状态栏
         *
         * */
        //透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }


        setContentView(R.layout.activity_my_web);
        ButterKnife.bind(this);

        WebSettings webSettings = webView.getSettings();
        //启用数据库
        webSettings.setDatabaseEnabled(true);
        String dir = this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();

        //启用地理定位
        webSettings.setGeolocationEnabled(true);
        //设置定位的数据库路径
        webSettings.setGeolocationDatabasePath(dir);

        //最重要的方法，一定要设置，这就是出不来的主要原因

        webSettings.setDomStorageEnabled(true);

               /*
        * 开启X5定位功能
        * */
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onGeolocationPermissionsShowPrompt(String s, GeolocationPermissionsCallback geolocationPermissionsCallback) {
                geolocationPermissionsCallback.invoke(s, true, true);
                super.onGeolocationPermissionsShowPrompt(s, geolocationPermissionsCallback);
            }

        });

        webView.setWebViewClient(new WebViewClient());

        Bundle bundle = getIntent().getExtras();
        String text = bundle.getString("TITLE");//根据key来获取
        String url = bundle.getString("URL");

        title.setText(text);

        if (url != null) {
            this.url = url;
        }
        webView.loadUrl(url);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView != null && webView.canGoBack()) {
                webView.goBack();
                if (Integer.parseInt(Build.VERSION.SDK) >= 16)
                    return true;
            } else
                return super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }


}

