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
import cn.songguohulian.cschool.bean.ResultBeanData;
import cn.songguohulian.cschool.utils.MyContants;

/**
 *
 * @author Ziv
 * @data 2017/4/16
 * @time 23:13
 */
public class EatChannelAdapter extends BaseAdapter {
    private Context mContext;
    private List<EatResultBean.ResultBean.ChannelInfoBean> channel_info;

    public EatChannelAdapter(Context mContext, List<EatResultBean.ResultBean.ChannelInfoBean> channel_info) {
        this.mContext = mContext;
        this.channel_info = channel_info;
    }


    @Override
    public int getCount() {
        return channel_info == null ? 0 : channel_info.size();
    }

    @Override
    public Object getItem(int position) {
        return channel_info.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holer;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.eat_item_channel, null);
            holer = new ViewHolder(convertView);
            convertView.setTag(holer);
        } else {
            holer = (ViewHolder) convertView.getTag();
        }

        EatResultBean.ResultBean.ChannelInfoBean channelInfoBean = channel_info.get(position);
        holer.tvChannel.setText(channelInfoBean.getChannel_name());
        Glide.with(mContext)
                .load(MyContants.BASE_URl_IMAGE +channelInfoBean.getImage())
                .into(holer.ivChannel);
        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.iv_channel)
        ImageView ivChannel;
        @Bind(R.id.tv_channel)
        TextView tvChannel;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
