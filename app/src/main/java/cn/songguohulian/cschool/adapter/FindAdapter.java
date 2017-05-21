package cn.songguohulian.cschool.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;


import java.util.ArrayList;
import java.util.List;

import cn.songguohulian.cschool.R;
import cn.songguohulian.cschool.bean.FindResultBean;
import cn.songguohulian.cschool.ui.MyWebActivity;
import cn.songguohulian.cschool.utils.LogUtil;
import cn.songguohulian.cschool.utils.MyContants;


/**
 * Created by Administrator on 2017/5/16.
 */

public class FindAdapter extends android.support.v7.widget.RecyclerView.Adapter<FindAdapter.MyListViewAdapter> {

    private Context mContext;

    private FindResultBean.ResultBean bean;

    public static final int LISTVIEW = 0;

    public int currentType = LISTVIEW;

    private final LayoutInflater mLayoutInflater;



    public FindAdapter(Context mContext, FindResultBean.ResultBean bean) {
        this.mContext = mContext;
        this.bean = bean;
        mLayoutInflater = LayoutInflater.from(mContext);
    }


    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case LISTVIEW:
                currentType = LISTVIEW;
                break;

        }
        return currentType;
    }


    @Override
    public MyListViewAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == LISTVIEW) {
            return new MyListViewAdapter(mLayoutInflater.inflate(R.layout.list_player, null), mContext, bean);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(MyListViewAdapter holder, int position) {
        if (getItemViewType(position) == LISTVIEW) {
            MyListViewAdapter myListViewAdapter = (MyListViewAdapter) holder;
            myListViewAdapter.setData();
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    class MyListViewAdapter extends XRecyclerView.ViewHolder {

        private final List<FindResultBean.ResultBean.ActInfoBean> act_info;
        public ListView mListView;
        private List<String> listUrl = new ArrayList<>();
        private List<String> listTitle = new ArrayList<>();
        private List<String> listThum = new ArrayList<>();
        public Context context;

        public MyListViewAdapter(View itemView, Context mContext, FindResultBean.ResultBean bean) {
            super(itemView);
            mListView = (ListView) itemView.findViewById(R.id.listView);
            act_info = bean.getAct_info();
            this.context = mContext;
            int size = act_info.size();

            LogUtil.e("数据为:" + size);


        }

        public void setData() {
        // 添加数据
            for (int i = 0; i < act_info.size(); i++) {
                listUrl.add(act_info.get(i).getUrl());
                listTitle.add(act_info.get(i).getName());
                listThum.add(MyContants.BASE_URl_IMAGE + act_info.get(i).getIcon_url());
            }

            ListAdapter adapter = new ListAdapter(mContext,listUrl,listTitle,listThum);
            mListView.setAdapter(adapter);

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i >= 0) {
                        Bundle bundle = new Bundle();
                        bundle.putString("URL", listUrl.get(i));
                        bundle.putString("TITLE", "发现新鲜事");
                        Intent intent_didi = new Intent(mContext, MyWebActivity.class);
                        intent_didi.putExtras(bundle);
                        mContext.startActivity(intent_didi);
                    }
                }
            });

        }
    }
}
