package cn.songguohulian.cschool.utils;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 *
 * @author Ziv
 * @data 2017/4/30
 * @time 19:31
 */
public class NonPageTransformer implements ViewPager.PageTransformer
{
    @Override
    public void transformPage(View page, float position)
    {
        page.setScaleX(0.8f);//hack
    }

    public static final ViewPager.PageTransformer INSTANCE = new NonPageTransformer();
}
