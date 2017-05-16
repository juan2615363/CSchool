package cn.songguohulian.cschool.fragment;


import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import cn.songguohulian.cschool.R;
import cn.songguohulian.cschool.adapter.MyFragmentAdapter;
import cn.songguohulian.cschool.base.BaseFragment;

/**
 * Created by Administrator on 2017/4/26.
 */
public class MyFragment extends BaseFragment{


    private MyFragmentAdapter adapter;
    private RecyclerView rvMy;


    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_my, null);
        rvMy = (RecyclerView) view.findViewById(R.id.rv_my);
        return view;
    }

    @Override
    public void initData() {
        super.initData();

        adapter = new MyFragmentAdapter(context);
        rvMy.setAdapter(adapter);
        GridLayoutManager manager =  new GridLayoutManager(context,1);

        //设置布局管理者
        rvMy.setLayoutManager(manager);

    }
}
