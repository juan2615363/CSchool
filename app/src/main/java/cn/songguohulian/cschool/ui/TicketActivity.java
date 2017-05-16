package cn.songguohulian.cschool.ui;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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
import cn.songguohulian.cschool.bean.TicketBean;
import cn.songguohulian.cschool.utils.LogUtil;

/**
 *
 * @author Ziv
 * @data 2017/5/12
 * @time 16:09
 *
 * 车票详情
 */
public class TicketActivity extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.chefariqi)
    public TextView chefariqi;

    @Bind(R.id.chefashijian)
    public TextView chefashijian;

    @Bind(R.id.chufadi)
    public TextView chufadi;

    @Bind(R.id.mudidi)
    public TextView mudidi;

    @Bind(R.id.price)
    public TextView price;

    @Bind(R.id.shengyu)
    public TextView shengyu;

    @Bind(R.id.maipiao)
    public Button maipiao;


    private PopupWindow popupWindow;
    private RelativeLayout 支付布局;
    private TextView 支付宝, 微信, 取消;
    private String destination1;
    private String place1;
    private String surplus1;
    private String arrival1;
    private String price1;
    private String date1;
    private String orderNumber;

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

        setContentView(R.layout.ticket_details);

        ButterKnife.bind(this);

        // 初始化popoupwindow
        initPopupWindow();

    }

    @Override
    protected void initData() {

        // 接受传递的值

        destination1 = getIntent().getStringExtra("destination1");
        place1 = getIntent().getStringExtra("place1");
        surplus1 = getIntent().getStringExtra("surplus1");
        arrival1 = getIntent().getStringExtra("arrival1");
        price1 = getIntent().getStringExtra("price1");
        date1 = getIntent().getStringExtra("date1");

        // 设置值:
        chefariqi.setText(date1);
        chefashijian.setText(arrival1);
        chufadi.setText(destination1);
        mudidi.setText(place1);
        price.setText("¥" + price1);
        shengyu.setText(surplus1 + "张");
    }


    @Override
    protected void initEvent() {
        maipiao.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.maipiao:
                // 弹出选择支付方式
                popupWindow.showAtLocation(getLayoutInflater().inflate(R.layout.merchant_details, null), Gravity.BOTTOM, 0, 0);
                break;

            case R.id.popup_zhifubao:
                if (!checkPackageInstalled("com.eg.android.AlipayGphone",
                        "https://www.alipay.com")) { // 支付宝支付要求用户已经安装支付宝客户端
                    Toast.makeText(TicketActivity.this, "请安装支付宝客户端", Toast.LENGTH_SHORT)
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
                    Toast.makeText(TicketActivity.this,
                            "您的手机上没有没有应用市场也没有浏览器，我也是醉了，你去想办法安装支付宝/微信吧",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
        return false;
    }

    // 支付宝支付
    public void 支付宝() {
        BP.pay("车票预购", "车票预订", (Double.valueOf(price1)), true, new PListener() {
            @Override
            public void orderId(String s) {
                // 获取订单号
                orderNumber = s;
            }

            @Override
            public void succeed() {
                Toast.makeText(getApplicationContext(), "支付成功!", Toast.LENGTH_SHORT).show();
                // 支付成功之后 将信息提交到服务器
                LogUtil.e("订单号码:" + orderNumber);

            }

            @Override
            public void fail(int i, String s) {
                Toast.makeText(getApplicationContext(), "支付失败!", Toast.LENGTH_SHORT).show();
                LogUtil.e("订单号码:" + orderNumber);

                return;
            }

            @Override
            public void unknow() {
                Toast.makeText(getApplicationContext(), "未知原因!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
