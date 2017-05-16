package cn.songguohulian.cschool.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.songguohulian.cschool.R;
import cn.songguohulian.cschool.bean.CShcoolUser;
import cn.songguohulian.cschool.ui.LoginActivity;
import cn.songguohulian.cschool.utils.CSpTools;
import cn.songguohulian.cschool.utils.LogUtil;
import cn.songguohulian.cschool.utils.MyContants;


/**
 * @author Ziv
 * @data 2017/4/23
 * @time 23:51
 */

public class MyFragmentAdapter extends RecyclerView.Adapter {


    private Context mContext;


    /**
     * 四种类型
     */


    public static final int MY = 0;

    public static final int ORDER = 1;

    public static final int CHANNLE = 2;


    public static final int LIST = 3;

    /**
     * 当前类型
     */
    public int currentType = MY;

    private final LayoutInflater mLayoutInflater;

    public MyFragmentAdapter(Context context) {
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == MY) {
            return new MyViewHolder(mLayoutInflater.inflate(R.layout.my_login_state, null), mContext);
        } else if (viewType == ORDER) {
            return new OrderViewHolder(mLayoutInflater.inflate(R.layout.my_text_order, null), mContext);
        } else if (viewType == CHANNLE) {
            return new ChannelViewHolder(mLayoutInflater.inflate(R.layout.my_channle, null), mContext);
        } else if (viewType == LIST) {
            return new ListViewHolder(mLayoutInflater.inflate(R.layout.my_list, null), mContext);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == MY) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            myViewHolder.setData();
        } else if (getItemViewType(position) == ORDER) {
            OrderViewHolder orderViewHolder = (OrderViewHolder) holder;
            orderViewHolder.setData();
        } else if (getItemViewType(position) == CHANNLE) {
            ChannelViewHolder channelViewHolder = (ChannelViewHolder) holder;
            channelViewHolder.setData();
        } else if (getItemViewType(position) == LIST) {
            ListViewHolder listViewHolder = (ListViewHolder) holder;
            listViewHolder.setData();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    @Override
    public int getItemViewType(int position) {

        switch (position) {
            case MY:
                currentType = MY;
                break;
            case ORDER:
                currentType = ORDER;
                break;
            case CHANNLE:
                currentType = CHANNLE;
                break;
            case LIST:
                currentType = LIST;
                break;
        }

        return currentType;

    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        public Context mContext;
        public LinearLayout login_state;
        public TextView user_Name;
        public ImageView avatarImg;
        public CShcoolUser user;

        public MyViewHolder(View itemView, Context mContext) {
            super(itemView);
            login_state = (LinearLayout) itemView.findViewById(R.id.login_state);
            user_Name = (TextView) itemView.findViewById(R.id.text_input_icon);
            avatarImg = (ImageView) itemView.findViewById(R.id.pic);
            this.mContext = mContext;

            user = BmobUser.getCurrentUser(mContext, CShcoolUser.class);
            user_Name.setText(this.user.getNickName());
            if (TextUtils.isEmpty(this.user.getHeadImgUrl())) {
                avatarImg.setImageResource(R.drawable.qt16qtiw);
            } else {
                Glide.with(mContext).load(this.user.getHeadImgUrl()).into(avatarImg);
            }


        }
        public void setData() {
            // dontsometing

        }
    }

    class OrderViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;


        public OrderViewHolder(View itemView, Context mContext) {
            super(itemView);

            this.mContext = mContext;
        }

        public void setData() {
        }
    }

    private class ChannelViewHolder extends RecyclerView.ViewHolder {
        public ChannelViewHolder(View itemView, Context mContext) {
            super(itemView);
        }

        public void setData() {
        }
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        private ListView mListView;
        private Context mContext;
        private List<String> items;
        private int pic[] = {R.drawable.vip, R.drawable.usermessage, R.drawable.ad, R.drawable.yijian,
                R.drawable.settings, R.drawable.help, R.drawable.fenxiang, R.drawable.about, R.drawable.kefu,
        R.drawable.exitos};

        public ListViewHolder(View itemView, Context mContext) {
            super(itemView);
            this.mContext = mContext;

            mListView = (ListView) itemView.findViewById(R.id.my_list);
        }

        public void setData() {
            fillArray();
            mListView.setAdapter(new CustomListAdapter(mContext));
        }

        private void fillArray() {

            items = new ArrayList<String>();
            items.add("会员俱乐部");
            items.add("常用信息");
            items.add("广告投放");
            items.add("意见反馈");
            items.add("常用设置");
            items.add("帮助中心");
            items.add("分享朋友");
            items.add("关于APP");
            items.add("客服中心");
            items.add("退出系统");

        }

        class CustomListAdapter extends BaseAdapter {
            private LayoutInflater mInflater;
            private Context mContext = null;

            public CustomListAdapter(Context context) {
                mContext = context;
                mInflater = LayoutInflater.from(mContext);
            }

            public Object getItem(int arg0) {
                // TODO Auto-generated method stub
                return items.get(arg0);
            }

            public long getItemId(int position) {
                // TODO Auto-generated method stub
                return position;
            }

            public int getCount() {
                // TODO Auto-generated method stub
                return items.size();
            }

            public View getView(int position, View convertView,
                                android.view.ViewGroup parent) {
                final ImageView indexImage;
                final TextView indexText;
                if (convertView == null) {
                    // 和item_custom.xml脚本关联
                    convertView = mInflater.inflate(R.layout.my_list_item, null);
                }
                indexImage = (ImageView) convertView.findViewById(R.id.pic);
                indexText = (TextView) convertView.findViewById(R.id.text2);
                // 设置item中indexText的文本
                indexText.setText(items.get(position).toString());
                // 设置item中ImageView的图片
                indexImage.setBackgroundResource(pic[position]);
                return convertView;
            }
        }

    }
}

