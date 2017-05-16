package cn.songguohulian.cschool.ui;

import android.app.Dialog;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.baidu.location.LocationClient;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.songguohulian.cschool.R;
import cn.songguohulian.cschool.adapter.AroundActivityAdapter;
import cn.songguohulian.cschool.base.BaseActivity;
import cn.songguohulian.cschool.bean.AroundResultBean;
import cn.songguohulian.cschool.bean.WeatherBean;
import cn.songguohulian.cschool.utils.MyContants;
import cn.songguohulian.cschool.utils.LogUtil;
import cn.songguohulian.cschool.utils.NetUtils;
import cn.songguohulian.cschool.view.DiglogAnimation;
import okhttp3.Call;

/**
 * @author Ziv
 * @data 2017/4/29
 * @time 22:19:33
 * <p>
 * 周边游:
 *
 * 主要采用XRecyclerView 具备下拉刷新 上滑加载的更多的功能
 */

public class AroundActivity extends BaseActivity {


    @Bind(R.id.around_rv_home)
    public XRecyclerView xRecyclerView;

    private AroundActivityAdapter adapter;
    private AroundResultBean.ResultBean result;
    private Dialog dialog;


    @Override
    protected void initView() {
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

        setContentView(R.layout.activity_around);

        ButterKnife.bind(this);


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
                if (NetUtils.isNetworkAvailable(getApplicationContext())) {
                    getDataFromNet();

                } else {
                    Toast.makeText(getApplicationContext(), "网络异常,请检查您的网络是否顺畅!", Toast.LENGTH_SHORT).show();
                    return;
                }

            }

            @Override
            public void onLoadMore() {
                xRecyclerView.loadMoreComplete();
            }
        });
    }

    private void getDataFromNet() {

        String url = MyContants.AROUND_URL;
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    /**
                     * 当请求失败的时候回调
                     * @param call
                     * @param e
                     * @param id
                     */
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtil.e("首页请求失败==" + e.getMessage());
                        // 跳向网络请求失败的地方
                        Toast.makeText(getApplicationContext(), "网络异常,请检查您的网络是否顺畅!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        return;
                    }

                    /**
                     * 当联网成功的时候回调
                     * @param response 请求成功的数据
                     * @param id
                     */
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

        AroundResultBean aroundResData = JSON.parseObject(response, AroundResultBean.class);
        result = aroundResData.getResult();

        // 判断是否有数据
        if (result != null) {

            // 设置适配器

            adapter = new AroundActivityAdapter(getApplicationContext(), result);

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
