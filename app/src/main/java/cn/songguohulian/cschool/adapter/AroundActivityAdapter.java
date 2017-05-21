package cn.songguohulian.cschool.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.alibaba.fastjson.JSON;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import cn.songguohulian.cschool.R;
import cn.songguohulian.cschool.bean.AroundResultBean;
import cn.songguohulian.cschool.bean.WeatherBean;
import cn.songguohulian.cschool.ui.MerchantDetails;
import cn.songguohulian.cschool.utils.LogUtil;
import cn.songguohulian.cschool.utils.MyContants;
import okhttp3.Call;


/**
 * @author Ziv
 * @data 2017/5/9
 * @time 18:12
 * <p>
 * 周边游Adapter
 */

public class AroundActivityAdapter extends XRecyclerView.Adapter<XRecyclerView.ViewHolder> {


    /**
     * 上下文
     */
    public Context mContext;
    /**
     * 数据Bean对象
     */
    private AroundResultBean.ResultBean resultBean;


    // 顶部图片
    public static final int AROUND_IMG = 0;
    // 热门景点
    public static final int AROUND_HOT = 1;

    /**
     * 当前类型
     */
    public int currentType = AROUND_IMG;
    private final LayoutInflater mLayoutInflater;


    public AroundActivityAdapter(Context mContext, AroundResultBean.ResultBean resultBean) {
        this.mContext = mContext;
        this.resultBean = resultBean;
        mLayoutInflater = LayoutInflater.from(mContext);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == AROUND_IMG) {
            return new AroundImgViewHolder(mLayoutInflater.inflate(R.layout.around_top_img, null), mContext);
        } else if (viewType == AROUND_HOT) {
            return new AroundHotViewHolder(mLayoutInflater.inflate(R.layout.aroundhot_item, null), mContext);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == AROUND_IMG) {
            AroundImgViewHolder aroundImgViewHolder = (AroundImgViewHolder) holder;
            aroundImgViewHolder.setData();
        } else if (getItemViewType(position) == AROUND_HOT) {
            AroundHotViewHolder aroundHotViewHolder = (AroundHotViewHolder) holder;
            aroundHotViewHolder.setData(resultBean.getAround_info());
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case AROUND_IMG:
                currentType = AROUND_IMG;
                break;
            case AROUND_HOT:
                currentType = AROUND_HOT;
                break;
        }
        return currentType;

    }

    private class AroundHotViewHolder extends RecyclerView.ViewHolder {
        private GridView gv_hot;
        private Context mContext;


        public AroundHotViewHolder(View itemView, Context mContext) {
            super(itemView);
            gv_hot = (GridView) itemView.findViewById(R.id.around_gv_hot);
            this.mContext = mContext;

        }

        public void setData(final List<AroundResultBean.ResultBean.AroundInfoBean> around_info) {
            AroundHotGridViewAdapter adapter = new AroundHotGridViewAdapter(mContext, around_info);
            gv_hot.setAdapter(adapter);
            gv_hot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    // 点击跳转到景点介绍界面
                    /**
                     "cover_price": "65元",
                     "time": "09:00-17:30",
                     "figure": "/around/fuhua.jpg",
                     "name": "富华游乐园",
                     "unobstructed": "自驾：火车站-和平路-东风东街-终点",
                     "introduction" :"潍坊富华游乐园是潍坊新立克集团与美国亚洲娱乐有限公司合资兴建的，集吃、住、行、游、购、娱于一体的，具有当今世界先进水平的大型主题乐园。 乐园一期投资3亿元人民币，占地20万平方米，拥有先进的游乐设施和完善的服务功能。有大型游乐项目如摩天轮、跳伞塔、太空飞行、过山车、激流勇进、海空出击、动感电影、旋转木马、碰碰车、疯狂老鼠、旋转滚筒、宇宙漫游、欧式花园等供您休闲。儿童反斗城、进口儿童游戏机、金龟虫等项目让您尽享天仑之乐。富华游乐园环境优美，娱乐功能齐全，1992年建园至今，以发展成为山东省内人造景观接待水平最高的旅游景点，素有齐鲁第一园的美。",
                     "product_id": "10659"
                     *
                     *
                     * */
                    String address = around_info.get(i).getAddress();
                    String price = around_info.get(i).getCover_price();
                    String name = around_info.get(i).getName();

                    Bundle bundle = new Bundle();
                    bundle.putString("name", name);
                    bundle.putString("address", address);
                    bundle.putString("price", price);
                    Intent intent_mer = new Intent(mContext, MerchantDetails.class);
                    intent_mer.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent_mer.putExtras(bundle);
                    mContext.startActivity(intent_mer);

                }
            });
        }
    }

    class AroundImgViewHolder extends RecyclerView.ViewHolder {


        public LocationClient mLocationClient = null;

        private BDLocationListener myListener = new MyLocationListener();

        private TextView area;// 地区
        private TextView date; // 日期
        private TextView temperature; // 大温度
        private TextView state; // 文字状态
        private TextView lowest; // 最低温度
        private TextView highest; // 最高温度
        private TextView fengxiang; // 风向
        private TextView ganmao; // 感冒建议


        public AroundImgViewHolder(View itemView, Context mContext) {
            super(itemView);
            // 初始化控件
            area = (TextView) itemView.findViewById(R.id.area);
            date = (TextView) itemView.findViewById(R.id.date);
            temperature = (TextView) itemView.findViewById(R.id.temperature);
            state = (TextView) itemView.findViewById(R.id.state);
            lowest = (TextView) itemView.findViewById(R.id.lowest);
            highest = (TextView) itemView.findViewById(R.id.highest);
            fengxiang = (TextView) itemView.findViewById(R.id.fengxiang);
            ganmao = (TextView) itemView.findViewById(R.id.ganmao);

        }


        public void setData() {
            // 百度定位
            mLocationClient = new LocationClient(mContext); // 声明LocationClient类

            mLocationClient.registerLocationListener(myListener); //注册监听函数

            initLocation();
            mLocationClient.start();

            getDataFromNet();


        }

        // 百度定位
        public class MyLocationListener implements BDLocationListener {

            @Override
            public void onReceiveLocation(BDLocation location) {

                //获取定位结果
                StringBuffer sb = new StringBuffer(256);

                // sb.append(location.getCity());    // 获取城市

                if (location.getLocType() == BDLocation.TypeGpsLocation) {
                    // GPS定位结果
                    sb.append(location.getCity());    // 获取城市

                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {

                    sb.append(location.getCity());    // 获取城市


                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {
                    sb.append(location.getCity());    // 获取城市

                } else if (location.getLocType() == BDLocation.TypeServerError) {
                    sb.append(location.getCity());    // 获取城市

                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    sb.append(location.getCity());    // 获取城市

                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    sb.append(location.getCity());    // 获取城市

                }

                area.setText(sb);
            }

        }

        private void initLocation() {

            LocationClientOption option = new LocationClientOption();

            option.setCoorType("bd09ll");
            //可选，默认gcj02，设置返回的定位结果坐标系

            int span = 1000;
            option.setScanSpan(span);
            //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的

            option.setIsNeedAddress(true);
            //可选，设置是否需要地址信息，默认不需要

            option.setOpenGps(true);
            //可选，默认false,设置是否使用gps

            option.setLocationNotify(true);
            //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果

            option.setIsNeedLocationDescribe(true);
            //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”

            option.setIsNeedLocationPoiList(true);
            //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到

            option.setIgnoreKillProcess(false);
            //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死

            option.SetIgnoreCacheException(false);
            //可选，默认false，设置是否收集CRASH信息，默认收集

            option.setEnableSimulateGps(false);
            //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要

            mLocationClient.setLocOption(option);
        }

        private void getDataFromNet() {
            String url = MyContants.WEATHER + area.getText();
            OkHttpUtils.get().url(url).build().execute(new StringCallback() {
                /**
                 * 当请求失败的时候回调
                 * @param call
                 * @param e
                 * @param id
                 */
                @Override
                public void onError(Call call, Exception e, int id) {
                    LogUtil.e("首页请求失败==" + e.getMessage());
                }

                /**
                 * 当联网成功的时候回调
                 * @param response 请求成功的数据
                 * @param id
                 */
                @Override
                public void onResponse(String response, int id) {
                    LogUtil.e("首页请求成功==" + response);

                    //解析数据
                    WeatherBean weatherBean = JSON.parseObject(response, WeatherBean.class);
                    WeatherBean.DataBean data = weatherBean.getData();

                    if (data != null) {
                        date.setText(data.getForecast().get(0).getDate()); // 日期
                        ganmao.setText("温馨提示:" + data.getGanmao()); // 感冒
                        temperature.setText(data.getWendu() + "℃"); // 温度
                        state.setText(data.getForecast().get(0).getType()); // 文字状态
                        lowest.setText(data.getForecast().get(0).getLow()); // 最低温度
                        highest.setText(data.getForecast().get(0).getHigh()); // 最高温度
                        fengxiang.setText("风向: " + data.getForecast().get(0).getFengxiang() + " " + data.getForecast().get(0).getFengli());
                    }
                }
            });
        }

    }

}
