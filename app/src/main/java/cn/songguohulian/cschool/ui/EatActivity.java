package cn.songguohulian.cschool.ui;

import android.app.Dialog;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.songguohulian.cschool.R;
import cn.songguohulian.cschool.adapter.EatActivityAdapter;
import cn.songguohulian.cschool.base.BaseActivity;
import cn.songguohulian.cschool.bean.EatResultBean;
import cn.songguohulian.cschool.utils.MyContants;
import cn.songguohulian.cschool.utils.LogUtil;
import cn.songguohulian.cschool.utils.NetUtils;
import cn.songguohulian.cschool.view.DiglogAnimation;
import okhttp3.Call;

/**
 *
 * @author Ziv
 * @data 2017/5/3
 * @time 8:19
 *
 * 我是吃货使用Fragment进行实现
 */

public class EatActivity extends BaseActivity {

    @Bind(R.id.eat_rv_home)
    public XRecyclerView xRecyclerView;

    private EatActivityAdapter adapter;
    private EatResultBean.ResultBean result;
    private Dialog dialog;

    @Override
    protected void initView() {
        steep();

        // 初始化布局
        setContentView(R.layout.eat_activity);

        // 绑定Activity
        ButterKnife.bind(this);
    }

    private void steep() {
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
    }

    @Override
    protected void initData() {

        if(NetUtils.isNetworkAvailable(this)) {
            dialog = DiglogAnimation.createLoadingDialog(this, "数据加载中....");
            dialog.show();
            getDataFromNet();
        } else {
            Toast.makeText(this, "网络异常,请检查您的网络是否顺畅!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void initEvent() {

        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                if(NetUtils.isNetworkAvailable(getApplicationContext())) {
                    getDataFromNet();
                } else {
                    Toast.makeText(getApplicationContext(), "网络异常,请检查您的网络是否顺畅!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onLoadMore() {
                xRecyclerView.loadMoreComplete();
            }
        });
    }


    private void getDataFromNet() {

        String url = MyContants.EAT_URL;
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getApplicationContext(), "网络异常,请检查您的网络是否顺畅!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtil.e("首页请求成功==" + response);

                        dialog.dismiss();
                        xRecyclerView.refreshComplete();
                        //解析数据
                        processData(response);
                    }
                });
    }

    private void processData(String response) {

        EatResultBean eatResultBean = JSON.parseObject(response, EatResultBean.class);
        result = eatResultBean.getResult();

        // 判断是否有数据
        if (result != null) {

            // 设置适配器

            adapter = new EatActivityAdapter(getApplicationContext(), result);

            xRecyclerView.setAdapter(adapter);


            GridLayoutManager manager =  new GridLayoutManager(getApplicationContext(),1);

            //设置布局管理者
            xRecyclerView.setLayoutManager(manager);

        } else {
            // 没有数据 可以跳向服务器忙的页面
            Toast.makeText(getApplicationContext(), "服务器忙,请稍后再试.....", Toast.LENGTH_SHORT).show();
            return;
        }
    }
}
