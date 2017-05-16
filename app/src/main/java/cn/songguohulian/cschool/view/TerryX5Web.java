package cn.songguohulian.cschool.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm;
import com.tencent.smtt.sdk.WebStorage;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;


public class TerryX5Web extends WebView {

	private Context context;
	public TerryX5Web(Context arg0, AttributeSet arg1) {
		super(arg0, arg1);
		//this.setWebViewClientExtension(new X5WebViewEventHandler(this));// 配置X5webview的事件处理
		this.setWebViewClient(client);
		//this.setWebChromeClient(chromeClient);
		WebStorage webStorage = WebStorage.getInstance();
		initWebViewSettings();
		this.getView().setClickable(true);
		this.getView().setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return false;
			}
		});
	}
	
	private void initWebViewSettings() {
		WebSettings webSetting = this.getSettings();
		webSetting.setJavaScriptEnabled(true);
		webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
		webSetting.setAllowFileAccess(true);
		webSetting.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
		webSetting.setSupportZoom(true);
		webSetting.setBuiltInZoomControls(true);
		webSetting.setUseWideViewPort(true);
		webSetting.setSupportMultipleWindows(true);
		webSetting.setLoadWithOverviewMode(true);

		webSetting.setDatabaseEnabled(true);
		webSetting.setDomStorageEnabled(true);
		webSetting.setGeolocationEnabled(true);
		
		// webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
		webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
		webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
		
		
		//cache setting
		webSetting.setAppCacheEnabled(true);
		webSetting.setAppCacheMaxSize(Long.MAX_VALUE);//设置缓存大小
		//webSetting.setAppCachePath(arg0)//设置缓存目录
		webSetting.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

	}

	private WebViewClient client = new WebViewClient() {

		/**
		 * 防止加载网页时调起系统浏览器
		 */
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}

		public void onReceivedHttpAuthRequest(WebView webview,
				com.tencent.smtt.export.external.interfaces.HttpAuthHandler httpAuthHandlerhost, String host,
				String realm) {
			boolean flag = httpAuthHandlerhost.useHttpAuthUsernamePassword();
		}

	};




}
