package cn.songguohulian.cschool.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.songguohulian.cschool.R;

/**
 * Created by Administrator on 2017/4/26.
 */

public class ListViewAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private Context mContext = null;
    //    private ListView mListView;
    private List<String> items;

    public ListViewAdapter(Context context) {
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
        indexText.setTextColor(Color.RED);
        // 设置item中ImageView的图片
        indexImage.setBackgroundResource(R.drawable.tuijian);
        return convertView;
    }
}