package cn.songguohulian.cschool.ui;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import butterknife.Bind;
import butterknife.ButterKnife;
import c.b.BP;
import c.b.PListener;
import cn.songguohulian.cschool.R;
import cn.songguohulian.cschool.base.BaseActivity;
import cn.songguohulian.cschool.view.TerryX5Web;

/**
 * @author Ziv
 * @data 2017/5/10
 * @time 15:54
 * <p>
 * 景点介绍
 */

public class MerchantDetails extends BaseActivity implements View.OnClickListener {


    @Bind(R.id.merchant_webView)
    public TerryX5Web webView;

    @Bind(R.id.merchant_price_show)
    public Button merchant_price_show;

    @Bind(R.id.merchant_buy)
    public Button merchant_buy;


    private PopupWindow popupWindow;
    private RelativeLayout 支付布局;
    private TextView 支付宝, 微信, 取消;
    private String name;
    private String address;
    private String price;

    @Override
    protected void initView() {

        setContentView(R.layout.merchant_details);

        // 绑定当前Activity
        ButterKnife.bind(this);

        // 初始化popoupwindow
        initPopupWindow();
    }

    @Override
    protected void initData() {

        // 加载数据
        lodingWebView();
    }

    private void lodingWebView() {

        Bundle bundle = getIntent().getExtras();
        name = bundle.getString("name");
        address = bundle.getString("address");
        price = bundle.getString("price");

        if (address != null) {
            webView.loadUrl(address);
        }


        merchant_price_show.setText("价格:¥" + price + "元");
    }

    @Override
    protected void initEvent() {

        merchant_buy.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.merchant_buy:
                // 弹出选择支付方式
                popupWindow.showAtLocation(getLayoutInflater().inflate(R.layout.merchant_details, null), Gravity.BOTTOM, 0, 0);
                break;

            case R.id.popup_zhifubao:
                if (!checkPackageInstalled("com.eg.android.AlipayGphone",
                        "https://www.alipay.com")) { // 支付宝支付要求用户已经安装支付宝客户端
                    Toast.makeText(MerchantDetails.this, "请安装支付宝客户端", Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                支付宝();
                break;
            case R.id.popup_zhifucancel:
                popupWindow.dismiss();
                break;
            case R.id.popup_zhifu_relative:
                popupWindow.dismiss();
                break;
        }
    }


    // 支付宝支付
    public void 支付宝() {
        BP.pay(name + "门票", name, (Double.valueOf(price)), true, new PListener() {
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
                    Toast.makeText(MerchantDetails.this,
                            "您的手机上没有没有应用市场也没有浏览器，我也是醉了，你去想办法安装支付宝/微信吧",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
        return false;
    }
}
