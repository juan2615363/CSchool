package cn.songguohulian.cschool.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.listener.OnLoadImageListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.songguohulian.cschool.R;
import cn.songguohulian.cschool.bean.EatResultBean;
import cn.songguohulian.cschool.bean.GoodsBean;
import cn.songguohulian.cschool.ui.GoodsInfoActivity;
import cn.songguohulian.cschool.ui.GoodsListActivity;
import cn.songguohulian.cschool.utils.LogUtil;
import cn.songguohulian.cschool.utils.MyContants;

/**
 * Created by Administrator on 2017/5/3.
 */

public class EatActivityAdapter extends XRecyclerView.Adapter<XRecyclerView.ViewHolder> {

    // 上下文
    private Context mContext;

    // 数据
    private EatResultBean.ResultBean resultBean;


    // 轮播
    public static final int BANNER = 0;

    // 分类
    public static final int CHANNEL = 1;

    // 活动倒计时
    public static final int SECKILL = 2;

    // 热门商品
    public static final int HOT = 3;

    // 默认类型
    public int currentType = BANNER;
    private final LayoutInflater mLayoutInflater;

    public EatActivityAdapter(Context mContext, EatResultBean.ResultBean resultBean) {
        this.resultBean = resultBean;
        this.mContext = mContext;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public XRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == BANNER) {
            return new EatBannerViewHolder(mLayoutInflater.inflate(R.layout.eat_banner_viewpager, null), mContext, resultBean);
        } else if (viewType == CHANNEL) {
            return new EatChannelViewHolder(mLayoutInflater.inflate(R.layout.eat_channle, null), mContext);
        } else if (viewType == SECKILL) {
            return new SeckillViewHolder(mLayoutInflater.inflate(R.layout.seckill_item, null), mContext);
        } else if (viewType == HOT) {
            return new HotViewHolder(mLayoutInflater.inflate(R.layout.eat_hot_item, null), mContext);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(XRecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == BANNER) {
            EatBannerViewHolder eatBannerViewHolder = (EatBannerViewHolder) holder;
            eatBannerViewHolder.setData(resultBean.getBanner_info());
        } else if (getItemViewType(position) == CHANNEL) {
            EatChannelViewHolder eatChannelViewHolder = (EatChannelViewHolder) holder;
            eatChannelViewHolder.setData(resultBean.getChannel_info());
        } else if (getItemViewType(position) == SECKILL) {
            SeckillViewHolder seckillViewHolder = (SeckillViewHolder) holder;
            seckillViewHolder.setData(resultBean.getSeckill_info());
        } else if (getItemViewType(position) == HOT) {
            HotViewHolder hotViewHolder = (HotViewHolder) holder;
            hotViewHolder.setData(resultBean.getHot_info());
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case BANNER:
                currentType = BANNER;
                break;
            case CHANNEL:
                currentType = CHANNEL;
                break;
            case SECKILL:
                currentType = SECKILL;
                break;
            case HOT:
                currentType = HOT;
                break;
        }
        return currentType;
    }

    class EatBannerViewHolder extends XRecyclerView.ViewHolder {
        public Banner banner;
        public Context mContext;
        public EatResultBean.ResultBean resultBean;

        public EatBannerViewHolder(View itemView, Context mContext, EatResultBean.ResultBean resultBean) {
            super(itemView);
            banner = (Banner) itemView.findViewById(R.id.eat_banner);
            this.mContext = mContext;
            this.resultBean = resultBean;
        }

        public void setData(List<EatResultBean.ResultBean.BannerInfoBean> banner_info) {
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
                            break;


                    }
                }

            });

        }
    }

    class EatChannelViewHolder extends XRecyclerView.ViewHolder {
        public GridView gvChannel;
        public Context mContext;


        public EatChannelViewHolder(View itemView, Context mContext) {
            super(itemView);
            gvChannel = (GridView) itemView.findViewById(R.id.gv_channel);
            this.mContext = mContext;
        }


        public void setData(List<EatResultBean.ResultBean.ChannelInfoBean> channel_info) {
            gvChannel.setAdapter(new EatChannelAdapter(mContext, channel_info));


            // 点击事件
            gvChannel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i <= 6) {
                        Intent intent = new Intent(mContext, GoodsListActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("position", i);
                        mContext.startActivity(intent);
                    } else {
                        Toast.makeText(mContext, "跳转到充值界面", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private boolean isFirst = true;
    private TextView tvTime;
    private int dt;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                dt = dt - 1000;
                SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");
                tvTime.setText(sd.format(new Date(dt)));

                handler.removeMessages(0);
                handler.sendEmptyMessageDelayed(0, 1000);
                if (dt == 0) {
                    handler.removeMessages(0);
                }
            }

        }
    };


    class SeckillViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMore;
        private RecyclerView recyclerView;
        public Context mContext;

        public SeckillViewHolder(View itemView, Context mContext) {
            super(itemView);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time_seckill);
            tvMore = (TextView) itemView.findViewById(R.id.tv_more_seckill);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.rv_seckill);
            this.mContext = mContext;
        }


        public void setData(final EatResultBean.ResultBean.SeckillInfoBean data) {
            //设置时间
            if (isFirst) {
//                dt = (int) (Integer.parseInt(data.getEnd_time()) - System.currentTimeMillis());
                dt = (int) (Integer.parseInt(data.getEnd_time()) - (Integer.parseInt(data.getStart_time())));
                isFirst = false;
            }

            //设置RecyclerView
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            SeckillRecyclerViewAdapter adapter = new SeckillRecyclerViewAdapter(mContext, data);
            recyclerView.setAdapter(adapter);

            //倒计时
            handler.sendEmptyMessageDelayed(0, 1000);

            //点击事件
            adapter.setOnSeckillRecyclerView(new SeckillRecyclerViewAdapter.OnSeckillRecyclerView() {
                @Override
                public void onClick(int position) {
                    List<EatResultBean.ResultBean.SeckillInfoBean.ListBean> list = data.getList();
                    String name = list.get(position).getName();
                    String cover_price = list.get(position).getCover_price();
                    String figure = list.get(position).getFigure();
                    String product_id = list.get(position).getProduct_id();

                    GoodsBean goodsBean = new GoodsBean(name, cover_price, figure, product_id);
                    Intent intent = new Intent(mContext, GoodsInfoActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("goods_bean", goodsBean);
                    mContext.startActivity(intent);

                }
            });

        }
    }

    class HotViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_more_hot;
        private GridView gv_hot;
        private Context mContext;

        public HotViewHolder(View itemView, Context mContext) {
            super(itemView);
            tv_more_hot = (TextView) itemView.findViewById(R.id.tv_more_hot);
            gv_hot = (GridView) itemView.findViewById(R.id.gv_hot);
            this.mContext = mContext;
        }

        public void setData(final List<EatResultBean.ResultBean.HotInfoBean> data) {
            EatHotGridViewAdapter adapter = new EatHotGridViewAdapter(mContext, data);
            gv_hot.setAdapter(adapter);

            //点击事件
            gv_hot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String name = data.get(position).getName();
                    String cover_price = data.get(position).getCover_price();
                    String figure = data.get(position).getFigure();
                    String product_id = data.get(position).getProduct_id();

                    GoodsBean goodsBean = new GoodsBean(name, cover_price, figure, product_id);
                    Intent intent = new Intent(mContext, GoodsInfoActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("goods_bean", goodsBean);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
