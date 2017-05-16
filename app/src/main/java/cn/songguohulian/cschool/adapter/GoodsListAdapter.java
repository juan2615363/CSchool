package cn.songguohulian.cschool.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.songguohulian.cschool.R;
import cn.songguohulian.cschool.bean.TypeListBean;
import cn.songguohulian.cschool.ui.GoodsListActivity;
import cn.songguohulian.cschool.utils.MyContants;

/**
 *
 * @author Ziv
 * @data 2017/5/13
 * @time 20:09
 */
public class GoodsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<TypeListBean.ResultBean.PageDataBean> page_data;

    public GoodsListAdapter(GoodsListActivity mContext, List<TypeListBean.ResultBean.PageDataBean> page_data) {
        this.mContext = mContext;
        this.page_data = page_data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(mContext, R.layout.item_goods_list_adapter, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setData(page_data.get(position));

    }

    @Override
    public int getItemCount() {
        return page_data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_hot;
        private TextView tv_name;
        private TextView tv_price;
        private TypeListBean.ResultBean.PageDataBean data;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_hot = (ImageView) itemView.findViewById(R.id.iv_hot);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.setOnItemClickListener(data);
                    }
                }
            });
        }

        public void setData(TypeListBean.ResultBean.PageDataBean data) {
            Glide.with(mContext).load(MyContants.BASE_URl_IMAGE +data.getFigure()).into(iv_hot);
            tv_name.setText(data.getName());
            tv_price.setText("￥" + data.getCover_price());
            this.data = data;

        }
    }


    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void setOnItemClickListener(TypeListBean.ResultBean.PageDataBean data);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
