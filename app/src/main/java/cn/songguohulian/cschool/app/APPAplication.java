package cn.songguohulian.cschool.app;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsListener;
import com.zhy.autolayout.config.AutoLayoutConifg;

public class APPAplication extends Application {

	private static Context mContext;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		this.mContext = this;
		AutoLayoutConifg.getInstance().useDeviceSize();
		//搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
		//TbsDownloader.needDownload(getApplicationContext(), false);
		
		QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
			
			@Override
			public void onViewInitFinished(boolean arg0) {
				// TODO Auto-generated method stub
				//Log.e("0828", " onViewInitFinished is " + arg0);
			}
			
			@Override
			public void onCoreInitFinished() {
				// TODO Auto-generated method stub
				
			}
		};
		 QbSdk.setTbsListener(new TbsListener() {
	            @Override
	            public void onDownloadFinish(int i) {
	                Log.d("0828","onDownloadFinish");
	            }

	            @Override
	            public void onInstallFinish(int i) {
	                Log.d("0828","onInstallFinish");
	            }

	            @Override
	            public void onDownloadProgress(int i) {
	                Log.d("0828","onDownloadProgress:"+i);
	            }
	        });
//		QbSdk.allowThirdPartyAppDownload(true);
//		QbSdk.initX5Environment(getApplicationContext(), QbSdk.WebviewInitType.FIRSTUSE_AND_PRELOAD, cb);
	}

	// 获取全局上下文
	public static Context getContext() {
		return mContext;
	}

}
