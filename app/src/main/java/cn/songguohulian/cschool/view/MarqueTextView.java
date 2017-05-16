package cn.songguohulian.cschool.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 *
 * @author Ziv
 * @data 2017/5/3
 * @time 8:13
 *
 * 跑马灯 TextView
 */

public class MarqueTextView extends TextView {

    public MarqueTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MarqueTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MarqueTextView(Context context) {
        super(context);
    }

    @Override

    public boolean isFocused() {
        return true;
    }
}