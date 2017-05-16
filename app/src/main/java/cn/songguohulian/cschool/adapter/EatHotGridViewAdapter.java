package cn.songguohulian.cschool.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.songguohulian.cschool.R;
import cn.songguohulian.cschool.bean.EatResultBean;
import cn.songguohulian.cschool.utils.MyContants;

/**
 *
 * @author Ziv
 * @data 2017/5/13
 * @time 20:09
 */
public class EatHotGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<EatResultBean.ResultBean.HotInfoBean> data;

    public EatHotGridViewAdapter(Context mContext, List<EatResultBean.ResultBean.HotInfoBean> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_hot_grid_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        EatResultBean.ResultBean.HotInfoBean hotInfoBean = data.get(position);
        Glide.with(mContext)
                .load(MyContants.BASE_URl_IMAGE +hotInfoBean.getFigure())
                .into(holder.ivHot);
        holder.tvName.setText(hotInfoBean.getName());
        holder.tvPrice.setText("￥" + hotInfoBean.getCover_price());
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.iv_hot)
        ImageView ivHot;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_price)
        TextView tvPrice;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
