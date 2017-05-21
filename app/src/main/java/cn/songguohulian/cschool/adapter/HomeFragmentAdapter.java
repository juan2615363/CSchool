package cn.songguohulian.cschool.adapter;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.listener.OnLoadImageListener;

import java.util.ArrayList;
import java.util.List;

import cn.songguohulian.cschool.R;
import cn.songguohulian.cschool.bean.GoodsBean;
import cn.songguohulian.cschool.bean.ResultBeanData;
import cn.songguohulian.cschool.ui.AroundActivity;
import cn.songguohulian.cschool.ui.BusinessShop;
import cn.songguohulian.cschool.ui.EatActivity;
import cn.songguohulian.cschool.ui.GoodsInfoActivity;
import cn.songguohulian.cschool.ui.MyWebActivity;
import cn.songguohulian.cschool.ui.RreeRideActivity;
import cn.songguohulian.cschool.ui.RunActivity;
import cn.songguohulian.cschool.utils.AlphaPageTransformer;
import cn.songguohulian.cschool.utils.MyContants;
import cn.songguohulian.cschool.utils.NetUtils;
import cn.songguohulian.cschool.utils.ScaleInTransformer;




public class HomeFragmentAdapter extends XRecyclerView.Adapter<XRecyclerView.ViewHolder> {

    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 数据Bean对象
     */
    private ResultBeanData.ResultBean resultBean;
    /**
     * 五种类型
     */
    /**
     * 横幅广告
     */
    public static final int BANNER = 0;
    /**
     * 频道
     */
    public static final int CHANNEL = 1;

    /**
     * 活动
     */
    public static final int ACT = 2;

    /**
     * 推荐
     */
    public static final int RECOMMEND = 3;
    /**
     * 热卖
     */
    public static final int SHANGJIA = 4;

    /**
     * 当前类型
     */
    public int currentType = BANNER;
    private final LayoutInflater mLayoutInflater;


    public HomeFragmentAdapter(Context mContext, ResultBeanData.ResultBean resultBean) {
        this.mContext = mContext;
        this.resultBean = resultBean;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    /**
     * 根据位置得到类型-系统调用
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case BANNER:
                currentType = BANNER;
                break;
            case CHANNEL:
                currentType = CHANNEL;
                break;
            case ACT:
                currentType = ACT;
                break;
            case RECOMMEND:
                currentType = RECOMMEND;
                break;
            case SHANGJIA:
                currentType = SHANGJIA;
                break;
        }
        return currentType;
    }

    /**
     * 返回总条数，共六种类型
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return 5;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == BANNER) {
            return new BannerViewHolder(mLayoutInflater.inflate(R.layout.banner_viewpager, null), mContext, resultBean);
        } else if (viewType == CHANNEL) {
            return new ChannelViewHolder(mLayoutInflater.inflate(R.layout.channel_item, null), mContext);
        } else if (viewType == ACT) {
            return new ActViewHolder(mLayoutInflater.inflate(R.layout.act_item, null), mContext);
        }else if (viewType == RECOMMEND) {
            return new RecommendViewHolder(mLayoutInflater.inflate(R.layout.recommend_item, null), mContext);
        }else if (viewType == SHANGJIA) {
            return new ShangJiaViewHolder(mLayoutInflater.inflate(R.layout.shangjia_item, null), mContext);
        }
        return null;
    }

    /**
     * 绑定数据
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == BANNER) {
            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
            bannerViewHolder.setData(resultBean.getBanner_info());
        } else if (getItemViewType(position) == CHANNEL) {
            ChannelViewHolder channelViewHolder = (ChannelViewHolder) holder;
            channelViewHolder.setData(resultBean.getChannel_info());
        } else if (getItemViewType(position) == ACT) {
            ActViewHolder actViewHolder = (ActViewHolder) holder;
            actViewHolder.setData(resultBean.getAct_info());
        }else if (getItemViewType(position) == RECOMMEND) {
            RecommendViewHolder recommendViewHolder = (RecommendViewHolder) holder;
            recommendViewHolder.setData(resultBean.getRecommend_info());
        } else if (getItemViewType(position) == SHANGJIA) {
            ShangJiaViewHolder hotViewHolder = (ShangJiaViewHolder) holder;
            hotViewHolder.setData(resultBean.getHot_info());
        }
    }


    class BannerViewHolder extends RecyclerView.ViewHolder {
        public Banner banner;
        public Context mContext;
        public ResultBeanData.ResultBean resultBean;

        public BannerViewHolder(View itemView, Context context, ResultBeanData.ResultBean resultBean) {
            super(itemView);
            banner = (Banner) itemView.findViewById(R.id.banner);
            this.mContext = context;
            this.resultBean = resultBean;
        }

        public void setData(final List<ResultBeanData.ResultBean.BannerInfoBean> banner_info) {

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
//                    Toast.makeText(mContext, (position - 1) + "  ", Toast.LENGTH_SHORT).show();
                    switch ((position - 1)) {
                        case 0:
                            Intent intent_around = new Intent(mContext, AroundActivity.class);
                            mContext.startActivity(intent_around);
                            break;
                        case 1:
                            Intent eat_intent = new Intent(mContext, EatActivity.class);
                            mContext.startActivity(eat_intent);
                            break;
                        case 2:
                            // 表白墙
                            if(NetUtils.isNetworkAvailable(mContext) ){
                                Bundle bundle=new Bundle();
                                Intent intent_biaobai = new Intent(mContext, MyWebActivity.class);
                                intent_biaobai.putExtra("URL", MyContants.BIAOBAIQIANGURL);
                                intent_biaobai.putExtra("TITLE", "表白墙");
                                intent_biaobai.putExtras(bundle);
                                mContext.startActivity(intent_biaobai);
                            } else {
                                Toast.makeText(mContext, "网络异常,请检查您的网络是否顺畅!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            break;
                        case 3:
                            Intent intent_paobu = new Intent(mContext, RunActivity.class);
                            mContext.startActivity(intent_paobu);
                            break;

                    }
                }

            });

        }
    }

    class ChannelViewHolder extends RecyclerView.ViewHolder {
        public GridView gvChannel;
        public Context mContext;

        public ChannelViewHolder(View itemView, Context mContext) {
            super(itemView);
            gvChannel = (GridView) itemView.findViewById(R.id.gv_channel);
            this.mContext = mContext;
        }

        public void setData(final List<ResultBeanData.ResultBean.ChannelInfoBean> channel_info) {
            gvChannel.setAdapter(new ChannelAdapter(mContext, channel_info));

            gvChannel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    /*
                    * 跳转到不同的页面
                    * */
                    switch (i) {
                        case 0:
                            // 校园商城
                            Intent eat_intent = new Intent(mContext, EatActivity.class);
                            mContext.startActivity(eat_intent);
                            break;
                        case 1:
                            // 跳转到周边游界面
                            Intent intent_around = new Intent(mContext, AroundActivity.class);
                            mContext.startActivity(intent_around);
                            break;
                        case 2:
                            // 顺风车
                            Intent intent_car = new Intent(mContext, RreeRideActivity.class);
                            mContext.startActivity(intent_car);
                            break;
                        case 3:
                            // 表白墙
                            if(NetUtils.isNetworkAvailable(mContext) ){
                                Bundle bundle=new Bundle();
                                Intent intent_biaobai = new Intent(mContext, MyWebActivity.class);
                                intent_biaobai.putExtra("URL", MyContants.BIAOBAIQIANGURL);
                                intent_biaobai.putExtra("TITLE", "表白墙");
                                intent_biaobai.putExtras(bundle);
                                mContext.startActivity(intent_biaobai);
                            } else {
                                Toast.makeText(mContext, "网络异常,请检查您的网络是否顺畅!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            break;
                        case 4:
                            // 约跑
                            Intent intent_paobu = new Intent(mContext, RunActivity.class);
                            mContext.startActivity(intent_paobu);
                            break;
                        case 7:
                            // 更多
                            break;
                    }
                }
            });

        }
    }

    class ActViewHolder extends RecyclerView.ViewHolder {
        public ViewPager actViewPager;
        public Context mContext;

        public ActViewHolder(View itemView, Context mContext) {
            super(itemView);
            actViewPager = (ViewPager) itemView.findViewById(R.id.act_viewpager);
            this.mContext = mContext;
        }

        public void setData(final List<ResultBeanData.ResultBean.ActInfoBean> data) {
            actViewPager.setPageMargin(20);
            actViewPager.setOffscreenPageLimit(3);
            actViewPager.setPageTransformer(true, new AlphaPageTransformer(new ScaleInTransformer()));

            actViewPager.setAdapter(new PagerAdapter() {
                @Override
                public int getCount() {
                    return data.size();
                }

                @Override
                public boolean isViewFromObject(View view, Object object) {
                    return view == object;
                }

                @Override
                public Object instantiateItem(ViewGroup container, final int position) {
                    ImageView view = new ImageView(mContext);

                    /**
                     * 活动点击事件
                     * */
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(mContext, "position:" + position, Toast.LENGTH_SHORT).show();
                        }
                    });
                    view.setScaleType(ImageView.ScaleType.FIT_XY);
                    //绑定数据
                    Glide.with(mContext)
                            .load(MyContants.BASE_URl_IMAGE + data.get(position).getIcon_url())
                            .into(view);
                    container.addView(view);
                    return view;
                }

                @Override
                public void destroyItem(ViewGroup container, int position, Object object) {
                    container.removeView((View) object);
                }
            });

            //点击事件
            actViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    // Toast.makeText(mContext, "position:" + position, Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });


        }
    }

    class RecommendViewHolder extends RecyclerView.ViewHolder {
        private GridView gv_recommend;
        private Context mContext;

        public RecommendViewHolder(View itemView, Context mContext) {
            super(itemView);
            gv_recommend = (GridView) itemView.findViewById(R.id.gv_recommend);
            this.mContext = mContext;
        }

        public void setData(final List<ResultBeanData.ResultBean.RecommendInfoBean> data) {
            RecommendGridViewAdapter adapter = new RecommendGridViewAdapter(mContext, data);
            gv_recommend.setAdapter(adapter);

            //点击事件
            gv_recommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    String name = data.get(position).getName();
                    String cover_price = data.get(position).getCover_price();
                    String figure = data.get(position).getFigure();
                    String product_id = data.get(position).getProduct_id();

                    GoodsBean goodsBean = new GoodsBean(name, cover_price, figure, product_id);
                    Intent intent = new Intent(mContext, GoodsInfoActivity.class);
                    intent.putExtra("goods_bean", goodsBean);
                    mContext.startActivity(intent);


                }
            });
        }
    }

    class ShangJiaViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_more_hot;
        private GridView gv_hot;
        private Context mContext;

        public ShangJiaViewHolder(View itemView, Context mContext) {
            super(itemView);
            gv_hot = (GridView) itemView.findViewById(R.id.gv_hot);
            this.mContext = mContext;
        }

        public void setData(final List<ResultBeanData.ResultBean.HotInfoBean> data) {
            ShangJiaGridViewAdapter adapter = new ShangJiaGridViewAdapter(mContext, data);
            gv_hot.setAdapter(adapter);

            //点击事件
            gv_hot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // 点击对应的商家跳转到商家界面
                    String name = data.get(position).getName(); // 商家名称
                    String figure = data.get(position).getFigure(); // 商家实景图片
                    String price = data.get(position).getCover_price(); // 价格
                    String phoneNumer = data.get(position).getPhone_number(); // 商家电话号码
                    String place = data.get(position).getPlace(); // 所在地点


                    Bundle bundle = new Bundle();
                    bundle.putString("name" ,name);
                    bundle.putString("figure", figure);
                    bundle.putString("price", price);
                    bundle.putString("phoneNumer", phoneNumer);
                    bundle.putString("place", place);

                    Intent intent = new Intent(mContext, BusinessShop.class);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            });
        }
    }

}
