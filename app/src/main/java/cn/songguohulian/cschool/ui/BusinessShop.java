package cn.songguohulian.cschool.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.songguohulian.cschool.R;
import cn.songguohulian.cschool.base.BaseActivity;
import cn.songguohulian.cschool.utils.LogUtil;
import cn.songguohulian.cschool.utils.MyContants;

/**
 *
 * @author Ziv
 * @data 2017/5/9
 * @time 18:19
 *
 * 商家详情界面
 */

public class BusinessShop extends BaseActivity {

    @Bind(R.id.business_images)
    public ImageView businessImage;

    @Bind(R.id.business_name)
    public TextView business_name;

    @Bind(R.id.business_price)
    public TextView busniess_price;

    @Bind(R.id.business_place)
    public TextView buniess_place;

    @Bind(R.id.call_phone)
    public Button class_phone;
    private String phoneNumber;

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
        setContentView(R.layout.busines_sshop);

        // 绑定当前Activity
        ButterKnife.bind(this);

    }


    @Override
    protected void initData() {


        /**
         * String name = data.get(position).getName(); // 商家名称
         String figure = data.get(position).getFigure(); // 商家实景图片
         String price = data.get(position).getCover_price(); // 价格
         String phoneNumer = data.get(position).getPhone_number(); // 商家电话号码
         * */
        // 接受数据
        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("name");
        String figure = bundle.getString("figure");
        String price = bundle.getString("price");
        phoneNumber = bundle.getString("phoneNumer");
        String place = bundle.getString("place");

//        LogUtil.e("都没扫描三" + phoneNumber);
        // 设置控件
        Glide.with(this).load(MyContants.BASE_URl_IMAGE + figure).into(businessImage);

        business_name.setText(name);
        buniess_place.setText(place);
        busniess_price.setText("人均:¥" + price + "元");


    }

    @Override
    protected void initEvent() {
        class_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 使用电话功能 这里不展示商家电话 保护商家隐私
//                Toast.makeText(getApplicationContext(), "拨打电话", Toast.LENGTH_SHORT).show();
                 Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                 startActivity(intent);
            }
        });
    }
}
