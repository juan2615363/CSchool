package cn.songguohulian.cschool.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.listener.OnLoadImageListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetCallback;
import cn.bmob.v3.listener.GetListener;
import cn.songguohulian.cschool.R;
import cn.songguohulian.cschool.bean.CShcoolUser;
import cn.songguohulian.cschool.bean.CarResultBean;
import cn.songguohulian.cschool.bean.GongGao;
import cn.songguohulian.cschool.bean.TicketBean;
import cn.songguohulian.cschool.service.Tools;
import cn.songguohulian.cschool.ui.MyWebActivity;
import cn.songguohulian.cschool.ui.RreeRideActivity;
import cn.songguohulian.cschool.ui.TicketActivity;
import cn.songguohulian.cschool.utils.LogUtil;
import cn.songguohulian.cschool.utils.MyContants;
import cn.songguohulian.cschool.view.CityPickerDialog;
import cn.songguohulian.cschool.view.DiglogAnimation;

/**
 * @author Ziv
 * @data 2017/4/30
 * @time 22:15
 * <p>
 * 顺风车adapter
 */

public class CarActivityAdapter extends XRecyclerView.Adapter<XRecyclerView.ViewHolder> {

    public RreeRideActivity mContext;

    private CarResultBean.ResultBean resultBean;


    // 轮播图
    public static final int CAR_BANNER = 0;

    // 分类
    public static final int CAR_CHANNLE = 1;


    // 展示主要采用ListView进行展示
    public static final int CAR_SHOW = 2;


    /**
     * 当前类型
     */
    public int currentType = CAR_BANNER;
    private final LayoutInflater mLayoutInflater;


    public CarActivityAdapter(RreeRideActivity mContext, CarResultBean.ResultBean resultBean) {
        this.mContext = mContext;
        this.resultBean = resultBean;
        mLayoutInflater = LayoutInflater.from(mContext);
    }


    @Override
    public XRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == CAR_BANNER) {
            return new CarBannerViewHolder(mLayoutInflater.inflate(R.layout.carshowbanner_viewpager, null), mContext, resultBean);
        } else if (viewType == CAR_CHANNLE) {
            return new CarChannleViewHolder(mLayoutInflater.inflate(R.layout.car_channel_item, null), mContext);
        } else if (viewType == CAR_SHOW) {
            return new CarShowViewHolder(mLayoutInflater.inflate(R.layout.car_query, null), mContext);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(XRecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == CAR_BANNER) {
            CarBannerViewHolder carBannerViewHolder = (CarBannerViewHolder) holder;
            carBannerViewHolder.setData(resultBean.getBanner_info());
        } else if (getItemViewType(position) == CAR_CHANNLE) {
            CarChannleViewHolder carChannleViewHolder = (CarChannleViewHolder) holder;
            carChannleViewHolder.setData(resultBean.getChannel_info());
        } else if (getItemViewType(position) == CAR_SHOW) {
            CarShowViewHolder carShowViewHolder = (CarShowViewHolder) holder;
            carShowViewHolder.setData();
        }
    }


    @Override
    public int getItemCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case CAR_BANNER:
                currentType = CAR_BANNER;
                break;

            case CAR_CHANNLE:
                currentType = CAR_CHANNLE;
                break;

            case CAR_SHOW:
                currentType = CAR_SHOW;
                break;
        }
        return currentType;
    }


    class CarChannleViewHolder extends XRecyclerView.ViewHolder {
        private GridView gv_hot;
        private Context mContext;

        public CarChannleViewHolder(View itemView, Context mContext) {
            super(itemView);
            gv_hot = (GridView) itemView.findViewById(R.id.car_gv_channel);
            this.mContext = mContext;
        }

        public void setData(List<CarResultBean.ResultBean.ChannelInfoBean> channel_info) {
            CarChannelAdapter adapter = new CarChannelAdapter(mContext, channel_info);
            gv_hot.setAdapter(adapter);


            gv_hot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    switch (i) {
                        case 0:
                            // 显示公告
                            showDialog();
                            break;
                        case 1:
                            // 显示起售时间
                            showTime();
                            break;
                        case 2:
                            // 跳转到火车订购网址
                            HuoCheYuDing();
                            break;
                        case 3:
                            // 跳转到滴滴出行页面
                            DidiChuXing();
                            break;
                    }
                }
            });
        }

        private void DidiChuXing() {
            Bundle bundle = new Bundle();
            bundle.putString("URL", MyContants.DIDIADD);
            bundle.putString("TITLE", "滴滴出行");
            Intent intent_didi = new Intent(mContext, MyWebActivity.class);
            intent_didi.putExtras(bundle);
            mContext.startActivity(intent_didi);
        }

        private void HuoCheYuDing() {
            Bundle bundle = new Bundle();
            bundle.putString("URL", MyContants.HUOCHEPIAO);
            bundle.putString("TITLE", "火车票预订");
            Intent intent_huoche = new Intent(mContext, MyWebActivity.class);
            intent_huoche.putExtras(bundle);
            mContext.startActivity(intent_huoche);
        }

        private void showTime() {
            Bmob.initialize(mContext, MyContants.BMOB_APP_ID);

            BmobQuery<GongGao> query = new BmobQuery<GongGao>();
            query.getObject(mContext, MyContants.SELLINGTIME, new GetListener<GongGao>() {

                @Override
                public void onSuccess(GongGao gongGao) {
                    String message = gongGao.getMessage();
                    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(mContext);
                    builder.setTitle("系统公告");
                    builder.setIcon(R.drawable.new_tz);
                    builder.setMessage(message);
                    builder.setPositiveButton("确定", null);
                    builder.show();
                }

                @Override
                public void onFailure(int i, String s) {
                    Toast.makeText(mContext, "获取系统公告失败!", Toast.LENGTH_SHORT).show();
                }
            });

        }

        private void showDialog() {
            Bmob.initialize(mContext, MyContants.BMOB_APP_ID);

            BmobQuery<GongGao> query = new BmobQuery<GongGao>();
            query.getObject(mContext, MyContants.NOTEICE, new GetListener<GongGao>() {
                @Override
                public void onSuccess(GongGao gongGao) {
                    String message = gongGao.getMessage();
                    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(mContext);
                    builder.setTitle("系统公告");
                    builder.setIcon(R.drawable.new_tz);
                    builder.setMessage(message);
                    builder.setPositiveButton("确定", null);
                    builder.show();
                }

                @Override
                public void onFailure(int i, String s) {
                    Toast.makeText(mContext, "获取起售时间公告失败!", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }


    class CarShowViewHolder extends XRecyclerView.ViewHolder implements View.OnClickListener {

        public TextView destination, place;
        public ImageView qiehuan;
        public Button mButton;
        private Dialog dialog;


        public CarShowViewHolder(View itemView, RreeRideActivity mContext) {
            super(itemView);
            mButton = (Button) itemView.findViewById(R.id.start_ss);
            destination = (TextView) itemView.findViewById(R.id.destination);
            place = (TextView) itemView.findViewById(R.id.place);
            qiehuan = (ImageView) itemView.findViewById(R.id.qiehuan);
        }

        public void setData() {

            mButton.setOnClickListener(this);
            destination.setOnClickListener(this);
            place.setOnClickListener(this);
            qiehuan.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.place:
                    showChooseCity_2();
                    break;
                case R.id.start_ss:
                    // 查询
                    dialog = DiglogAnimation.createLoadingDialog(mContext, "正在查询车票....");
                    dialog.show();
                    query();
                    break;
            }
        }

        /*
        * 显示地区选择框
        * */
        private void showChooseCity_2() {
            if (!Tools.isFastDoubleClick()) {
                new CityPickerDialog(mContext,
                        new CityPickerDialog.OnCityPikerListener() {
                            @Override
                            public void onCityPicker(String province,
                                                     String city) {
                                place.setText(city);
                            }
                        }).show();
            }
        }

        private void query() {
            BmobQuery<TicketBean> query = new BmobQuery<TicketBean>();
            query.addWhereEqualTo("place", place.getText().toString());
            query.findObjects(mContext, new FindListener<TicketBean>() {
                @Override
                public void onSuccess(List<TicketBean> list) {
                    if (list.size() <= 0) {
                        dialog.dismiss();
                        Toast.makeText(mContext, "没有搜索到到达" + place.getText().toString() + "的车次!", Toast.LENGTH_SHORT).show();
                        return;

                    } else {
                        dialog.dismiss();
                        String arrival1 = list.get(0).getArrival();
                        String date1 = list.get(0).getDate();
                        String destination1 = list.get(0).getDestination();
                        String gooff1 = list.get(0).getGooff();
                        String place1 = list.get(0).getPlace();
                        String price1 = list.get(0).getPrice();
                        String surplus1 = list.get(0).getSurplus();

                        // 存在该车票 跳转车票详情界面
                        Bundle bundle = new Bundle();
                        bundle.putString("destination1", destination1);
                        bundle.putString("place1", place1);
                        bundle.putString("surplus1", surplus1);
                        bundle.putString("arrival1", arrival1);
                        bundle.putString("price1", price1);
                        bundle.putString("date1", date1);
                        Intent intent = new Intent(mContext, TicketActivity.class);
                        intent.putExtras(bundle);
                        mContext.startActivity(intent);

                    }
                }

                @Override
                public void onError(int i, String s) {

                }
            });

        }

    }


    class CarBannerViewHolder extends RecyclerView.ViewHolder {
        public Banner banner;
        public Context mContext;
        public CarResultBean.ResultBean resultBean;

        public CarBannerViewHolder(View itemView, Context context, CarResultBean.ResultBean resultBean) {
            super(itemView);
            banner = (Banner) itemView.findViewById(R.id.carshow_banner);
            this.mContext = context;
            this.resultBean = resultBean;
        }

        public void setData(List<CarResultBean.ResultBean.BannerInfoBean> banner_info) {

            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);

            //如果你想用自己项目的图片加载,那么----->自定义图片加载框架
            List<String> imageUris = new ArrayList<>();

            for (int i = 0; i < resultBean.getBanner_info().size(); i++) {
                imageUris.add(resultBean.getBanner_info().get(i).getImage());
            }

            banner.setDelayTime(4000);
            banner.setImages(imageUris, new OnLoadImageListener() {
                @Override
                public void OnLoadImage(ImageView view, Object url) {
                    /**
                     * 这里你可以根据框架灵活设置
                     */
                    Glide.with(mContext)
                            .load(MyContants.BASE_URl_IMAGE + url)
                            .into(view);
                }
            });

            banner.setOnBannerClickListener(new OnBannerClickListener() {
                @Override
                public void OnBannerClick(int position) {
//                    LogUtil.e(position + "!");
                    Toast.makeText(mContext, (position - 1) + "  ", Toast.LENGTH_SHORT).show();
                    switch ((position - 1)) {
                        case 0:


                    }
                }

            });

        }
    }
}


