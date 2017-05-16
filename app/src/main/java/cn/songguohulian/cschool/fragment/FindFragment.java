package cn.songguohulian.cschool.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;



import cn.songguohulian.cschool.R;
import cn.songguohulian.cschool.base.BaseFragment;
import cn.songguohulian.cschool.utils.LogUtil;

/**
 *
 * @author Ziv
 * @data 2017/4/26
 * @time 17:40
 *
 * 发现
 */
public class FindFragment extends BaseFragment{

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_find, null);
        return view;
    }

    @Override
    public void initData() {
        super.initData();

        LogUtil.e("发现的数据加载完成.....");
    }
}
