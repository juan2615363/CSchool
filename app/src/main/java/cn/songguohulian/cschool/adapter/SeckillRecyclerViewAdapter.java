package cn.songguohulian.cschool.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.songguohulian.cschool.R;
import cn.songguohulian.cschool.bean.EatResultBean;
import cn.songguohulian.cschool.utils.MyContants;


/**
 *
 * @author Ziv
 * @data 2017/5/13
 * @time 20:09
 */
public class SeckillRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private EatResultBean.ResultBean.SeckillInfoBean data;
    private final List<EatResultBean.ResultBean.SeckillInfoBean.ListBean> list;

    public SeckillRecyclerViewAdapter(Context mContext, EatResultBean.ResultBean.SeckillInfoBean data) {
        this.mContext = mContext;
        this.data = data;
        list = data.getList();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_seckill, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.setData(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivFigure;
        private TextView tvCoverPrice;
        private TextView tvOriginPrice;
        private LinearLayout ll_root;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivFigure = (ImageView) itemView.findViewById(R.id.iv_figure);
            tvCoverPrice = (TextView) itemView.findViewById(R.id.tv_cover_price);
            tvOriginPrice = (TextView) itemView.findViewById(R.id.tv_origin_price);
            ll_root = (LinearLayout) itemView.findViewById(R.id.ll_root);
        }

        public void setData(final int position) {
            EatResultBean.ResultBean.SeckillInfoBean.ListBean listBean = list.get(position);
            tvCoverPrice.setText("￥" + listBean.getCover_price());
            tvOriginPrice.setText("￥" + listBean.getOrigin_price());
            Glide.with(mContext)
                    .load(MyContants.BASE_URl_IMAGE +listBean.getFigure())
                    .into(ivFigure);
            ll_root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  //  Toast.makeText(mContext, "position" + position, Toast.LENGTH_SHORT).show();
                    if (onSeckillRecyclerView != null) {
                        onSeckillRecyclerView.onClick(position);
                    }
                }
            });
        }
    }

    public interface OnSeckillRecyclerView {
        void onClick(int position);
    }

    public void setOnSeckillRecyclerView(OnSeckillRecyclerView onSeckillRecyclerView) {
        this.onSeckillRecyclerView = onSeckillRecyclerView;
    }

    private OnSeckillRecyclerView onSeckillRecyclerView;
}
