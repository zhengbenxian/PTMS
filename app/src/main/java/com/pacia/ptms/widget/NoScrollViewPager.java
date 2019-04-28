package com.pacia.ptms.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * ViewPager 是否滑动
 * Created by p on 2018/03/23.
 */

public class NoScrollViewPager extends ViewPager {
    private static final String TAG = "NoScrollViewPager";
    private boolean isScroll = true;

    //设置是否禁止滑动
    public void setScroll(boolean scroll) {
        isScroll = scroll;
    }

    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isScroll && super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return isScroll && super.onTouchEvent(ev);
    }
}
