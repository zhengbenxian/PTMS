package com.pacia.ptms.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by p on 2018/03/23.
 */

public class MainPagerAdapter extends FragmentPagerAdapter {
    private Fragment fragments[];

    public MainPagerAdapter(FragmentManager fm, Fragment[] fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
    //不销毁
//        super.destroyItem(container, position, object);
//    }
}
