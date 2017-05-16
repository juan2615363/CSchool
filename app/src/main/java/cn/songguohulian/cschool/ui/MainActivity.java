package cn.songguohulian.cschool.ui;


import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.songguohulian.cschool.R;
import cn.songguohulian.cschool.base.BaseActivity;
import cn.songguohulian.cschool.base.BaseFragment;
import cn.songguohulian.cschool.fragment.FindFragment;
import cn.songguohulian.cschool.fragment.HomeFragment;
import cn.songguohulian.cschool.fragment.MyFragment;



/**
 *
 * @author Ziv
 * @data 2017/4/26
 * @time 17:37
 *
 * 主界面
 */
public class MainActivity extends BaseActivity {

    @Bind(R.id.frameLayout)
    public FrameLayout mFrameLayout;

    @Bind(R.id.rg_main)
    public RadioGroup mRgMain;



    /**
     * 装多个Fragment的实例集合
     */
    private ArrayList<BaseFragment> fragments;

    private int position = 0;
    /**
     * 缓存的Fragemnt或者上次显示的Fragment
     */
    private Fragment tempFragemnt;

    @Override
    protected void initView() {

        /**
         * 沉浸式状态栏
         *
         * */
        //透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }


        setContentView(R.layout.activity_main);


        // 绑定当前所在的Activity
        ButterKnife.bind(this);

        // 初始化fragment
        initFragment();


    }


    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        mRgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_home://主页
                        position = 0;
                        break;
                    case R.id.rb_find://发现
                        position = 1;
                        break;
                    case R.id.rb_my://我的
                        position = 2;
                        break;
                    default:
                        position = 0;
                        break;
                }

                //根据位置取不同的Fragment
                BaseFragment baseFragment = getFragment(position);

                /**
                 * 第一参数：上次显示的Fragment
                 * 第二参数：当前正要显示的Fragment
                 */
                switchFragment(tempFragemnt, baseFragment);

            }
        });


        mRgMain.check(R.id.rb_home);

    }

    /**
     * 添加的时候要按照顺序
     */
    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new FindFragment());
        fragments.add(new MyFragment());
    }

    private BaseFragment getFragment(int position) {
        if (fragments != null && fragments.size() > 0) {
            BaseFragment baseFragment = fragments.get(position);
            return baseFragment;
        }
        return null;
    }

    /**
     * 切换Fragment
     * @param fromFragment
     * @param nextFragment
     */
    private void switchFragment(Fragment fromFragment, BaseFragment nextFragment) {
        if (tempFragemnt != nextFragment) {
            tempFragemnt = nextFragment;
            if (nextFragment != null) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                //判断nextFragment是否添加
                if (!nextFragment.isAdded()) {
                    //隐藏当前Fragment
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }
                    //添加Fragment
                    transaction.add(R.id.frameLayout, nextFragment).commit();
                } else {
                    //隐藏当前Fragment
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }
                    transaction.show(nextFragment).commit();
                }
            }
        }
    }

}
