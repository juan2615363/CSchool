package cn.songguohulian.cschool.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;



import java.util.List;

import c.b.BP;
import c.b.PListener;
import cn.songguohulian.cschool.R;
import cn.songguohulian.cschool.adapter.ShopCartAdapter;
import cn.songguohulian.cschool.bean.GoodsBean;
import cn.songguohulian.cschool.utils.CartProvider;
import cn.songguohulian.cschool.view.DiglogAnimation;


public class ShoppingCartActivity extends Activity implements View.OnClickListener {
    private ImageButton ibShopcartBack;
    private TextView tvShopcartEdit;
    private RecyclerView recyclerview;
    private CheckBox checkboxAll;
    private TextView tvShopcartTotal;
    private LinearLayout ll_check_all;
    private LinearLayout ll_delete;
    private CheckBox cb_all;
    private Button btn_delete;
    private Button btnCheckOut;
    private ShopCartAdapter adapter;
    private LinearLayout ll_empty_shopcart;
    private TextView tv_empty_cart_tobuy;

    private PopupWindow popupWindow;
    private RelativeLayout 支付布局;
    private TextView 支付宝, 取消;

    /**
     * 编辑状态
     */
    private static final int ACTION_EDIT = 0;
    /**
     * 完成状态
     */
    private static final int ACTION_COMPLETE = 1;
    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2016-10-11 21:08:02 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        ibShopcartBack = (ImageButton) findViewById(R.id.ib_shopcart_back);
        tvShopcartEdit = (TextView) findViewById(R.id.tv_shopcart_edit);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        checkboxAll = (CheckBox) findViewById(R.id.checkbox_all);
        tvShopcartTotal = (TextView) findViewById(R.id.tv_shopcart_total);
        btnCheckOut = (Button) findViewById(R.id.btn_check_out);
        ll_check_all = (LinearLayout) findViewById(R.id.ll_check_all);
        ll_delete = (LinearLayout) findViewById(R.id.ll_delete);
        cb_all = (CheckBox) findViewById(R.id.cb_all);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        ll_empty_shopcart = (LinearLayout) findViewById(R.id.ll_empty_shopcart);
        tv_empty_cart_tobuy = (TextView) findViewById(R.id.tv_empty_cart_tobuy);
        支付宝 = (TextView) findViewById(R.id.popup_zhifubao);
        取消 = (TextView) findViewById(R.id.popup_zhifucancel);
        支付布局 = (RelativeLayout) findViewById(R.id.popup_zhifu_relative);

        ibShopcartBack.setOnClickListener(this);
        btnCheckOut.setOnClickListener(this);
        tvShopcartEdit.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        tv_empty_cart_tobuy.setClickable(true);
        tv_empty_cart_tobuy.setOnClickListener(this);
    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2016-10-11 21:08:02 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
        if (v == ibShopcartBack) {
            finish();
        } else if (v == btnCheckOut) {
            // 弹出选择支付方式
            popupWindow.showAtLocation(getLayoutInflater().inflate(R.layout.activity_shopping_cart, null), Gravity.BOTTOM, 0, 0);
            // 执行结算
        } else if (v == tvShopcartEdit) {
            //设置编辑的点击事件
            int tag = (int) tvShopcartEdit.getTag();
            if (tag == ACTION_EDIT) {
                //变成完成状态
                showDelete();
            } else {
                //变成编辑状态
                hideDelete();
            }
        } else if (v == btn_delete) {
            adapter.deleteData();
            adapter.showTotalPrice();
            //显示空空如也
            checkData();
            adapter.checkAll();
        } else if (v == tv_empty_cart_tobuy) {
            Intent intent = new Intent(this, EatActivity.class);
            startActivity(intent);
            finish();
        } else if (v == 支付宝) {
            if (!checkPackageInstalled("com.eg.android.AlipayGphone",
                    "https://www.alipay.com")) { // 支付宝支付要求用户已经安装支付宝客户端
                Toast.makeText(ShoppingCartActivity.this, "请安装支付宝客户端", Toast.LENGTH_SHORT)
                        .show();
                return;
            }
            支付宝();
        } else if (v == 取消) {
            popupWindow.dismiss();
        } else if (v == 支付布局) {
            popupWindow.dismiss();

        }
    }

    private void hideDelete() {
        tvShopcartEdit.setText("编辑");
        tvShopcartEdit.setTag(ACTION_EDIT);

        adapter.checkAll_none(true);
        ll_delete.setVisibility(View.GONE);
        ll_check_all.setVisibility(View.VISIBLE);

        adapter.showTotalPrice();
    }

    private void showDelete() {
        tvShopcartEdit.setText("完成");
        tvShopcartEdit.setTag(ACTION_COMPLETE);

        adapter.checkAll_none(false);
        cb_all.setChecked(false);
        checkboxAll.setChecked(false);

        ll_delete.setVisibility(View.VISIBLE);
        ll_check_all.setVisibility(View.GONE);

        adapter.showTotalPrice();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        findViews();

        showData();
        tvShopcartEdit.setTag(ACTION_EDIT);
        tvShopcartEdit.setText("编辑");

        initPopupWindow();

    }

    //-----------------------------------------
    private void checkData() {
        if (adapter != null && adapter.getItemCount() > 0) {
            tvShopcartEdit.setVisibility(View.VISIBLE);
            ll_empty_shopcart.setVisibility(View.GONE);
            ll_check_all.setVisibility(View.GONE);
        } else {
            ll_empty_shopcart.setVisibility(View.VISIBLE);
            tvShopcartEdit.setVisibility(View.GONE);
            ll_check_all.setVisibility(View.GONE);
            ll_delete.setVisibility(View.GONE);
        }
    }

    private void showData() {
        CartProvider cartProvider = CartProvider.getInstance();

        List<GoodsBean> datas = cartProvider.getDataFromLocal();
        if (datas != null && datas.size() > 0) {
            tvShopcartEdit.setVisibility(View.VISIBLE);
            ll_empty_shopcart.setVisibility(View.GONE);
            adapter = new ShopCartAdapter(this, datas, tvShopcartTotal, cartProvider, checkboxAll, cb_all);
            recyclerview.setLayoutManager(new LinearLayoutManager(this));
            recyclerview.setAdapter(adapter);
        } else {
            //显示空的
            tvShopcartEdit.setVisibility(View.GONE);
            ll_empty_shopcart.setVisibility(View.VISIBLE);
            ll_check_all.setVisibility(View.GONE);
            ll_delete.setVisibility(View.GONE);
        }

    }


    // 支付宝支付
    public void 支付宝() {
        BP.pay("购物车结算", "购物车结算", Double.parseDouble(tvShopcartTotal.getText().toString()),  true, new PListener() {
            @Override
            public void orderId(String s) {
                // 获取订单号
            }

            @Override
            public void succeed() {
                Toast.makeText(getApplicationContext(), "支付成功!", Toast.LENGTH_SHORT).show();
                // 支付成功之后 将信息提交到服务器
            }

            @Override
            public void fail(int i, String s) {
                Toast.makeText(getApplicationContext(), "支付失败!", Toast.LENGTH_SHORT).show();
                return;
            }

            @Override
            public void unknow() {
                Toast.makeText(getApplicationContext(), "未知原因!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 初始化popup
     */
    private void initPopupWindow() {
        View view = getLayoutInflater().inflate(R.layout.activity_pay, null);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);

        支付布局 = (RelativeLayout) view.findViewById(R.id.popup_zhifu_relative);
        支付宝 = (TextView) view.findViewById(R.id.popup_zhifubao);
        取消 = (TextView) view.findViewById(R.id.popup_zhifucancel);

        支付布局.setOnClickListener(this);
        支付宝.setOnClickListener(this);
        取消.setOnClickListener(this);

    }

    /**
     * 检查某包名应用是否已经安装
     *
     * @param packageName 包名
     * @param browserUrl  如果没有应用市场，去官网下载
     * @return
     */
    private boolean checkPackageInstalled(String packageName, String browserUrl) {
        try {
            // 检查是否有支付宝客户端
            getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            // 没有安装支付宝，跳转到应用市场
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=" + packageName));
                startActivity(intent);
            } catch (Exception ee) {// 连应用市场都没有，用浏览器去支付宝官网下载
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(browserUrl));
                    startActivity(intent);
                } catch (Exception eee) {
                    Toast.makeText(ShoppingCartActivity.this,
                            "您的手机上没有没有应用市场也没有浏览器，我也是醉了，你去想办法安装支付宝/微信吧",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
        return false;
    }
}
