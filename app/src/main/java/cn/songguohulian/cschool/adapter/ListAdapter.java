package cn.songguohulian.cschool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


import com.squareup.picasso.Picasso;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import cn.songguohulian.cschool.R;


/**
 *
 * @author Ziv
 * @data 2017/5/17
 * @time 17:25
 */

public class ListAdapter extends BaseAdapter{

    private Context mContext;
    private List<String> listUrl;
    private List<String> listTitle;
    private List<String> listThum;
    public ListAdapter(Context context,List<String> listUrl,List<String> listTitle,List<String> listThum){
        this.mContext = context;
        this.listUrl = listUrl;
        this.listTitle = listTitle;
        this.listThum = listThum;
    }
    @Override
    public int getCount() {
        return listUrl.size();
    }

    @Override
    public Object getItem(int i) {
        return listUrl.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.find_listview_item,null);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView_find);
            convertView.setTag(viewHolder);
            AutoUtils.autoSize(convertView);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }


        //加载图片作为视频的显示图片
        Picasso.with(mContext)
                .load(listThum.get(position))
                .into(viewHolder.imageView);

        return convertView;
    }

    class ViewHolder{
        ImageView imageView;
    }
}