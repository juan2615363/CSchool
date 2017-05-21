package cn.songguohulian.cschool.fragment;


import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.alibaba.fastjson.JSON;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;



import cn.songguohulian.cschool.R;
import cn.songguohulian.cschool.adapter.FindAdapter;
import cn.songguohulian.cschool.base.BaseFragment;
import cn.songguohulian.cschool.bean.FindResultBean;
import cn.songguohulian.cschool.bean.PlayerVideoBean;
import cn.songguohulian.cschool.utils.LogUtil;
import cn.songguohulian.cschool.utils.MyContants;
import cn.songguohulian.cschool.utils.NetUtils;
import cn.songguohulian.cschool.view.DiglogAnimation;
import okhttp3.Call;

/**
 *
 * @author Ziv
 * @data 2017/4/26
 * @time 17:40
 *
 * 发现
 */
public class FindFragment extends BaseFragment{

    private XRecyclerView recyclerView;
    private Dialog dialog;
    private FindAdapter adapter;
    private PlayerVideoBean.ShowapiResBodyBean.PagebeanBean pagebean;

    private int i;

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_find, null);
        recyclerView = (XRecyclerView) view.findViewById(R.id.rv_find);

        return view;
    }

    @Override
    public void initData() {
        super.initData();


        //判断网络状态
        if (NetUtils.isNetworkAvailable(context)) {
            dialog = DiglogAnimation.createLoadingDialog(context, "数据加载中....");
            dialog.show();
            getDataFromNet();
        } else {
            Toast.makeText(context, "网络异常,请检查您的网络是否顺畅!", Toast.LENGTH_SHORT).show();
        }


        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                if (NetUtils.isNetworkAvailable(context)) {
                    getDataFromNet();
                } else {
                    Toast.makeText(context, "网络异常,请检查您的网络是否顺畅!", Toast.LENGTH_SHORT).show();
                    recyclerView.refreshComplete();
                    return;
                }
            }

            @Override
            public void onLoadMore() {
                recyclerView.loadMoreComplete();
            }
        });


    }

    private void getDataFromNet() {

        String url = MyContants.FIND_URL;
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
//                        Toast.makeText(context, "网络异常,请检查您的网络是否顺畅!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    /**
                     * 当联网成功的时候回调
                     * @param response 请求成功的数据
                     * @param id
                     */
                    @Override
                    public void onResponse(String response, int id) {
                        dialog.dismiss();
                        recyclerView.refreshComplete();

                        LogUtil.e("请求成功==" + response);

                        //解析数据
                        processData(response);

                    }
                });

        LogUtil.e("连接为:" + url);
    }

    public void processData(String json) {
        FindResultBean findResultBean = JSON.parseObject(json, FindResultBean.class);

        FindResultBean.ResultBean result = findResultBean.getResult();




        if (result != null) {
            //有数据 设置适配器
            adapter = new FindAdapter(context, result);
            recyclerView.setAdapter(adapter);
            GridLayoutManager manager = new GridLayoutManager(context, 1);

            //设置布局管理者
            recyclerView.setLayoutManager(manager);


        } else {
            // 没有数据
            // 没有数据 可以跳向服务器忙的页面
            Toast.makeText(context, "服务器忙,请稍后再试.....", Toast.LENGTH_SHORT).show();
            return;
        }

    }

}
