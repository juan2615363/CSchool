package cn.songguohulian.cschool.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import cn.songguohulian.cschool.R;
import cn.songguohulian.cschool.adapter.ExpandableListViewAdapter;
import cn.songguohulian.cschool.adapter.GoodsListAdapter;
import cn.songguohulian.cschool.bean.GoodsBean;
import cn.songguohulian.cschool.bean.TypeListBean;
import cn.songguohulian.cschool.utils.MyContants;
import cn.songguohulian.cschool.utils.SpaceItemDecoration;
import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by Administrator on 2017/5/13.
 */

public class GoodsListActivity extends Activity {

    private RecyclerView recyclerview;
    private ExpandableListView listView;


    private int click_count = 0;
    private ArrayList<String> group;
    private ArrayList<List<String>> child;
    private ExpandableListViewAdapter adapter;

    private LinearLayout ll_select_root;
    private LinearLayout ll_price_root;
    private LinearLayout ll_theme_root;
    private LinearLayout ll_type_root;

    private ImageButton ib_drawer_layout_back;
    private Button btn_drawer_layout_confirm;
    private Button btn_drawer_layout_cancel;

    private Button btn_drawer_type_confirm;
    private Button btn_drawer_type_cancel;

    private Button btn_drawer_theme_confirm;
    private Button btn_drawer_theme_cancel;

    private RelativeLayout rl_select_price;
    private RelativeLayout rl_select_recommend_theme;
    private RelativeLayout rl_select_type;
    private RelativeLayout rl_price_30_50;
    private RelativeLayout rl_theme_note;
    private int position;
    private String[] urls = new String[]{
            MyContants.CLOSE_STORE,
            MyContants.GAME_STORE,
            MyContants.COMIC_STORE,
            MyContants.COSPLAY_STORE,
            MyContants.GUFENG_STORE,
            MyContants.STICK_STORE,
            MyContants.WENJU_STORE,
    };
    private List<TypeListBean.ResultBean.PageDataBean> page_data;
    private GoodsListAdapter adapter1;
    private DrawerLayout dl_left;

    private void findViews() {
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);

        ll_select_root = (LinearLayout) findViewById(R.id.ll_select_root);
        ll_price_root = (LinearLayout) findViewById(R.id.ll_price_root);
        ll_theme_root = (LinearLayout) findViewById(R.id.ll_theme_root);
        ll_type_root = (LinearLayout) findViewById(R.id.ll_type_root);

        ib_drawer_layout_back = (ImageButton) findViewById(R.id.ib_drawer_layout_back);
        btn_drawer_layout_confirm = (Button) findViewById(R.id.btn_drawer_layout_confirm);
        btn_drawer_layout_cancel = (Button) findViewById(R.id.btn_drawer_layout_cancel);
        btn_drawer_type_confirm = (Button) findViewById(R.id.btn_drawer_type_confirm);
        btn_drawer_type_cancel = (Button) findViewById(R.id.btn_drawer_type_cancel);
        btn_drawer_theme_confirm = (Button) findViewById(R.id.btn_drawer_theme_confirm);
        btn_drawer_theme_cancel = (Button) findViewById(R.id.btn_drawer_theme_cancel);

        rl_select_price = (RelativeLayout) findViewById(R.id.rl_select_price);
        rl_select_recommend_theme = (RelativeLayout) findViewById(R.id.rl_select_recommend_theme);
        rl_select_type = (RelativeLayout) findViewById(R.id.rl_select_type);
        rl_price_30_50 = (RelativeLayout) findViewById(R.id.rl_price_30_50);
        rl_theme_note = (RelativeLayout) findViewById(R.id.rl_theme_note);
        dl_left = (DrawerLayout) findViewById(R.id.dl_left);


        listView = (ExpandableListView) findViewById(R.id.expandableListView);

    }




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


        setContentView(R.layout.activity_goods_list);
        findViews();

        Intent intent = getIntent();
        position = intent.getIntExtra("position", -1);

        getDataFromNet();
    }

    public void getDataFromNet() {
        OkHttpUtils
                .get()
                .url(urls[position])
                .id(100)
                .build()
                .execute(new MyStringCallback());
    }


    public class MyStringCallback extends StringCallback {


        @Override
        public void onBefore(Request request, int id) {
        }

        @Override
        public void onAfter(int id) {
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            Log.e("TAG", "联网失败" + e.getMessage());
        }

        @Override
        public void onResponse(String response, int id) {

            switch (id) {
                case 100:
                    if (response != null) {
                        processData(response);
                        GridLayoutManager manager = new GridLayoutManager(GoodsListActivity.this, 2);
                        recyclerview.setLayoutManager(manager);
                        adapter1 = new GoodsListAdapter(GoodsListActivity.this, page_data);
                        recyclerview.addItemDecoration(new SpaceItemDecoration(10));
                        recyclerview.setAdapter(adapter1);

                        adapter1.setOnItemClickListener(new GoodsListAdapter.OnItemClickListener() {
                            @Override
                            public void setOnItemClickListener(TypeListBean.ResultBean.PageDataBean data) {
                                String name = data.getName();
                                String cover_price = data.getCover_price();
                                String figure = data.getFigure();
                                String product_id = data.getProduct_id();

                                GoodsBean goodsBean = new GoodsBean(name, cover_price, figure, product_id);
                                Intent intent = new Intent(GoodsListActivity.this, GoodsInfoActivity.class);
                                intent.putExtra("goods_bean", goodsBean);
                                startActivity(intent);
                                finish();
                            }
                        });
                    }
                    break;
                case 101:
                    Toast.makeText(GoodsListActivity.this, "https", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    }

    private void processData(String response) {
        Gson gson = new Gson();
        TypeListBean typeListBean = gson.fromJson(response, TypeListBean.class);
        page_data = typeListBean.getResult().getPage_data();
    }

}
