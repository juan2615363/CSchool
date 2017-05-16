package cn.songguohulian.cschool.adapter;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.songguohulian.cschool.R;
import cn.songguohulian.cschool.base.BaseActivity;

/**
 *
 * @author Ziv
 * @data 2017/5/9
 * @time 17:24
 *
 * 附近商家详情界面
 */

public class NearbyMerchants extends BaseActivity {

    @Bind(R.id.shoppic)
    public ImageView shopImage; // 商家图片

    @Bind(R.id.shopname)
    public TextView shopName; // 商家店铺名称

    @Bind(R.id.score)
    public TextView shopScore; // 店铺评分

    @Bind(R.id.shopprice)
    public TextView shopPrice; // 人均价格

    @Bind(R.id.shopplace)
    public TextView shopPlace; // 商家地点

    @Bind(R.id.telphone)
    public Button telPhone; //拨打订餐电话

    @Override
    protected void initView() {

        setContentView(R.layout.nearby_merchants);

        // 绑定当前Activity
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }
}
