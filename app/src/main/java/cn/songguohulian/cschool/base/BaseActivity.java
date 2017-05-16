package cn.songguohulian.cschool.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import c.b.BP;
import cn.bmob.v3.Bmob;
import cn.songguohulian.cschool.utils.MyContants;


/**
 *
 * @author Ziv
 * @data 2017/4/26
 * @time 17:19
 *
 * Activity的基类
 */

public abstract class BaseActivity extends FragmentActivity {

    public Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
        // 初始化BmobSdk
        Bmob.initialize(mContext, MyContants.BMOB_APP_ID);

        // 初始化Bmob支付
        BP.init(MyContants.BMOB_APP_ID);

        initView();

        initData();

        initEvent();
    }

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initEvent();
}
